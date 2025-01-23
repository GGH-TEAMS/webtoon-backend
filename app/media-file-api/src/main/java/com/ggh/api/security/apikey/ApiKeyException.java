package com.ggh.api.security.apikey;

import org.springframework.security.access.AccessDeniedException;

public class ApiKeyException extends AccessDeniedException {

    public ApiKeyException(String message) {
        super(message);
    }
}
