package com.ggh.api.security.apikey;

public class MissingApiKeyException extends ApiKeyException {

    public MissingApiKeyException(final String msg) {
        super(msg);
    }
}
