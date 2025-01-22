package com.ggh.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggh.core.web.ErrorCode;
import com.ggh.core.web.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class SecurityErrorCodeResponseHandler {

    private final ObjectMapper objectMapper;

    // 공통 응답 처리 메소드
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       ErrorCode errorCode, String errorMessage, int status) throws IOException {

        var errorResponse = new ErrorResponse(errorMessage, errorCode);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

}
