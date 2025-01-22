package com.ggh.api.security.apikey;


import java.io.Serial;

public class InvalidApiKeyException extends ApiKeyException {

    @Serial
    private static final long serialVersionUID = -3957402017631196621L;

    public InvalidApiKeyException(final String msg) {
        super(msg);
    }
}
