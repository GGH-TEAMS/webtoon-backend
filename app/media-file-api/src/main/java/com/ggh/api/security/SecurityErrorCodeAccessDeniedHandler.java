package com.ggh.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggh.api.error.MediaFileErrorCode;
import com.ggh.core.web.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class SecurityErrorCodeAccessDeniedHandler extends SecurityErrorCodeResponseHandler implements AccessDeniedHandler {

    public SecurityErrorCodeAccessDeniedHandler(final ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response, final AccessDeniedException accessDeniedException) throws IOException, ServletException {
        var status = HttpServletResponse.SC_FORBIDDEN;

        handle(request, response, MediaFileErrorCode.DEFAULT, accessDeniedException.getMessage(), status);
    }
}
