package com.ggh.api.security.apikey;

import java.io.Serial;

public class MissingApiKeyException extends ApiKeyException{
    @Serial
    private static final long serialVersionUID = 7485597906333047365L;

    public MissingApiKeyException(final String msg) {
        super(msg);
    }
}
