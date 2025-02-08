package com.ggh.api.security.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggh.api.domain.auth.AccountStatus;
import com.ggh.api.security.config.AuthTokenProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class) // _를 띄어쓰기로 변환
class AuthJwtHelperTest {

    private AuthJwtHelper authJwtHelper;
    private AuthTokenProperties authTokenProperties;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void 설정_초기화() {
        authTokenProperties = new AuthTokenProperties();
        authTokenProperties.setSecretKey("my-secret-key-my-secret-key-my-secret-key");
        authTokenProperties.setAccessTokenTtl(Duration.ofMinutes(30)); // 30분 유효기간 설정

        authJwtHelper = new AuthJwtHelper(authTokenProperties, objectMapper);
    }

    @Test
    void 토큰_발급이_정상적으로_작동한다() {
        // given
        TokenInfo tokenInfo = new TokenInfo("12345", AccountStatus.ONBOARDING_REQUIRED);

        // when
        String token = authJwtHelper.issueToken(tokenInfo);

        // then
        assertThat(token).isNotNull();
        assertThat(authJwtHelper.isValidToken(token)).isTrue();
    }

    @Test
    void 발급된_토큰에서_정보를_정상적으로_가져온다() {
        // given
        TokenInfo tokenInfo = new TokenInfo("12345", AccountStatus.ACTIVE);
        String token = authJwtHelper.issueToken(tokenInfo);

        // when
        TokenInfo extractedInfo = authJwtHelper.getTokenInfo(token);

        // then
        assertThat(extractedInfo.userId()).isEqualTo("12345");
        assertThat(extractedInfo.accountStatus()).isEqualTo(AccountStatus.ACTIVE);
    }

    @Test
    void 만료된_토큰은_유효하지_않다() {
        // given
        AuthTokenProperties shortLivedProperties = new AuthTokenProperties();
        shortLivedProperties.setSecretKey("my-secret-key-my-secret-key-my-secret-key");
        shortLivedProperties.setAccessTokenTtl(Duration.ofSeconds(1)); // 1초 유효기간 설정

        AuthJwtHelper shortLivedAuthJwtHelper = new AuthJwtHelper(shortLivedProperties, objectMapper);
        TokenInfo tokenInfo = new TokenInfo("12345", AccountStatus.ACTIVE);
        String token = shortLivedAuthJwtHelper.issueToken(tokenInfo);

        // wait for the token to expire
        try {
            Thread.sleep(2000); // 2초 대기 (토큰 만료)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // when & then
        assertThat(shortLivedAuthJwtHelper.isValidToken(token)).isFalse();
    }

    @Test
    void 잘못된_토큰은_유효하지_않다() {
        // given
        String invalidToken = "invalid.token.value";

        // when & then
        assertThat(authJwtHelper.isValidToken(invalidToken)).isFalse();
    }

    @Test
    void 토큰이_null이거나_빈값일_경우_유효하지_않다() {
        // when & then
        assertThat(authJwtHelper.isValidToken(null)).isFalse();
        assertThat(authJwtHelper.isValidToken("")).isFalse();
    }

    @Test
    void 만료된_토큰에서_claims를_가져올_수_있다() {
        // given
        AuthTokenProperties shortLivedProperties = new AuthTokenProperties();
        shortLivedProperties.setSecretKey("my-secret-key-my-secret-key-my-secret-key");
        shortLivedProperties.setAccessTokenTtl(Duration.ofSeconds(1)); // 1초 유효기간 설정

        AuthJwtHelper shortLivedAuthJwtHelper = new AuthJwtHelper(shortLivedProperties, objectMapper);
        TokenInfo tokenInfo = new TokenInfo("12345", AccountStatus.ONBOARDING_REQUIRED);
        String token = shortLivedAuthJwtHelper.issueToken(tokenInfo);

        try {
            Thread.sleep(2000); // 2초 대기 (토큰 만료)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertThat(authJwtHelper.isTokenExpired(token)).isTrue();
        TokenInfo extractedInfo = authJwtHelper.getTokenInfo(token);

        // then
        assertThat(extractedInfo.userId()).isEqualTo(tokenInfo.userId());
        assertThat(extractedInfo.accountStatus()).isEqualTo(tokenInfo.accountStatus());
    }

    @Test
    void Authorization_Header에서_Bearer_Token을_정상적으로_추출한다() {
        // given
        String authorizationHeader = "Bearer my-access-token";

        // when
        String token = AuthJwtHelper.resolveBearerToken(authorizationHeader);

        // then
        assertThat(token).isEqualTo("my-access-token");
    }

    @Test
    void Authorization_Header가_null이거나_Bearer로_시작하지_않으면_null을_반환한다() {
        // given & when & then
        assertThat(AuthJwtHelper.resolveBearerToken(null)).isNull();
        assertThat(AuthJwtHelper.resolveBearerToken("Invalid header")).isNull();
    }
}
