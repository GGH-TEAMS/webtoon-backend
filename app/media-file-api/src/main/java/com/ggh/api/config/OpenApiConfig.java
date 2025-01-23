package com.ggh.api.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {

        var apiKeyScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY) // API Key 방식
                .in(SecurityScheme.In.HEADER)    // 헤더를 통해 전달
                .name("X-API-KEY")               // 헤더 이름
                .description("API 키 인증을 사용합니다.");

        var securityRequirement = new SecurityRequirement()
                .addList("API Key Authentication");

        var info = new Info()
                .title("Media File Backend API")
                .version("1.0.0")
                .description("""
                        Media File Backend API Documentation"""
                );

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("API Key Authentication", apiKeyScheme)) // 보안 스키마 추가
                .security(List.of(securityRequirement))
                .servers(List.of(
                        new Server().url("http://localhost:8081")
                                .description("로컬 서버")

                ))
                .info(info);
    }
}