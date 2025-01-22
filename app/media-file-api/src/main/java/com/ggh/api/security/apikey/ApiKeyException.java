package com.ggh.api.security.apikey;

import org.springframework.security.access.AccessDeniedException;

import java.io.Serial;

public class ApiKeyException extends AccessDeniedException {
    @Serial
    private static final long serialVersionUID = 1576725704514989679L;

    public ApiKeyException(final String msg) {
        super(msg);
    }
}
