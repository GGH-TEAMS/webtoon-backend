package com.ggh.api.security;

import com.ggh.api.error.MediaFileErrorCode;
import com.ggh.core.web.ErrorCode;
import org.springframework.security.access.AccessDeniedException;

import java.util.Map;

public class AuthorizationErrorCodeTranslator {

    private static final Map<Class<? extends AccessDeniedException>, ErrorCode> exceptionToErrorCode = Map.of(
    );

    public static ErrorCode determineErrorCode(final AccessDeniedException authorizationException) {
        return exceptionToErrorCode.getOrDefault(authorizationException.getClass(), MediaFileErrorCode.A00403);
    }

}
