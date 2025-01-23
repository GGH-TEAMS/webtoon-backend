package com.ggh.api.security.apikey;

public class InvalidApiKeyException extends ApiKeyException {


    public InvalidApiKeyException(final String msg) {
        super(msg);
    }
}
