package com.ggh.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggh.api.security.apikey.ApiKeyAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {


    private final List<String> allowedOriginPatterns = List.of("*");
    private final ObjectMapper objectMapper;

    @Bean
    public OrRequestMatcher ignoredMatchers() {
        return new OrRequestMatcher(
                new AntPathRequestMatcher("/error/**"),
                new AntPathRequestMatcher("/v3/api-docs"),
                new AntPathRequestMatcher("/swagger.html/**"),
                new AntPathRequestMatcher("/swagger-ui/**"),
                new AntPathRequestMatcher("/test/**")
        );
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new SecurityErrorCodeAccessDeniedHandler(objectMapper);
    }

    @Bean
    public ApiKeyAuthFilter apiKeyAuthFilter(
            @Value("${app.api-key}") String apiKey,
            OrRequestMatcher ignoredMatchers,
            AccessDeniedHandler accessDeniedHandler
    ) {
        var apiKeyAuthFilter = new ApiKeyAuthFilter(apiKey);
        apiKeyAuthFilter.setIgnoredRequestMatchers(ignoredMatchers);
        apiKeyAuthFilter.setAccessDeniedHandler(accessDeniedHandler);

        return apiKeyAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            ApiKeyAuthFilter apiKeyAuthFilter,
            AccessDeniedHandler accessDeniedHandler,
            CorsConfigurationSource corsConfigurationSource
    ) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cc -> cc.configurationSource(corsConfigurationSource))
                .authorizeHttpRequests(registry -> registry
                        .anyRequest().permitAll()
                )
                .addFilterBefore(apiKeyAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ehc -> ehc
                        .accessDeniedHandler(accessDeniedHandler)
                );
        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(allowedOriginPatterns);
        configuration.setAllowedMethods(List.of("GET", "POST", "OPTIONS", "PUT", "DELETE", "HEAD"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(false);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
