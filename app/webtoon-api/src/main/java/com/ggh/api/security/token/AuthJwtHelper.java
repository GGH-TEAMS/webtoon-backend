package com.ggh.api.security.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggh.api.security.config.AuthTokenProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class AuthJwtHelper {
    private static final String ISS = "webtoon-backend";
    private static final String TOKEN_INFO = "token_info";

    private final AuthTokenProperties authTokenProperties;
    private final SecretKey signingKey;
    private final ObjectMapper objectMapper;

    public AuthJwtHelper(AuthTokenProperties authTokenProperties, ObjectMapper objectMapper) {
        this.authTokenProperties = authTokenProperties;
        this.signingKey = Keys.hmacShaKeyFor(authTokenProperties.getSecretKey().getBytes());
        this.objectMapper = objectMapper;
    }

    public String issueToken(TokenInfo tokenInfo) {
        try {
            Instant now = Instant.now();
            Date issuedAt = Date.from(now);

            long accessTokenTtl = authTokenProperties.getAccessTokenTtl().toSeconds();
            Instant expirationInstant = now.plus(accessTokenTtl, ChronoUnit.SECONDS);
            Date expirationAt = Date.from(expirationInstant);
            var jsonTokenInfo = objectMapper.writeValueAsString(tokenInfo);

            return Jwts.builder()
                    .issuer(ISS)
                    .claim(TOKEN_INFO, jsonTokenInfo)
                    .issuedAt(issuedAt)
                    .expiration(expirationAt)
                    .signWith(signingKey)
                    .compact();
        } catch (Exception e) {
            throw new RuntimeException("Failed to issue token", e);
        }
    }

    public boolean isValidToken(String accessToken) {
        if (accessToken == null || accessToken.isBlank()) {
            return false;
        }

        try {
            return !isTokenExpired(accessToken);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        Date expiration = getClaims(token).getExpiration();

        return expiration.before(Date.from(Instant.now()));
    }

    public TokenInfo getTokenInfo(String token) {
        Claims claims = getClaims(token);
        String jsonTokenInfo = claims.get(TOKEN_INFO, String.class);

        try {
            return objectMapper.readValue(jsonTokenInfo, TokenInfo.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get token info", e);
        }
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public static String resolveBearerToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}