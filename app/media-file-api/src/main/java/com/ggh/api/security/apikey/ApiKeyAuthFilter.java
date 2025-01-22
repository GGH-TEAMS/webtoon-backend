package com.ggh.api.security.apikey;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;


@Slf4j
@RequiredArgsConstructor
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private static final String API_KEY_HEADER = "X-API-KEY";

    private final String API_KEY;
    private RequestMatcher ignoredRequestMatcher;
    private AccessDeniedHandler accessDeniedHandler;

    public void setAccessDeniedHandler(final AccessDeniedHandler accessDeniedHandler) {
        this.accessDeniedHandler = Objects.requireNonNull(accessDeniedHandler);
    }

    public void setIgnoredRequestMatchers(final RequestMatcher ignoredRequestMatcher) {
        this.ignoredRequestMatcher = Objects.requireNonNull(ignoredRequestMatcher);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String apiKey = request.getHeader(API_KEY_HEADER);

        log.debug("API Key: {}", apiKey);

        try {
            authorize(apiKey);
        } catch (ApiKeyException e) {
            accessDeniedHandler.handle(request, response, e);
            return;
        } catch (Exception e) {
            log.trace("API 키 인증 중 알 수 없는 문제가 발생하였습니다. 확인하고 고쳐야 합니다.");
            accessDeniedHandler.handle(request, response, new ApiKeyException(e.getMessage()));
        }

        // 인증 성공 후 필터 체인 계속 처리
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) throws ServletException {
        //  requestMatcher에 해당하는 요청이면 필터를 수행하지 않음
        return ignoredRequestMatcher.matches(request);
    }

    private void authorize(String apiKey) throws ApiKeyException {
        if (!StringUtils.hasText(apiKey)) {
            throw new MissingApiKeyException("API 키가 누락되었습니다. 헤더를 확인해 주세요.");
        } else if (!API_KEY.equals(apiKey)) {
            throw new InvalidApiKeyException("API 키가 올바르지 않습니다. 확인해 주세요.");
        }
    }
}
