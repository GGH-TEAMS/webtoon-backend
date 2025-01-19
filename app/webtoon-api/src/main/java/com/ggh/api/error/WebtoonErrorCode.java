package com.ggh.api.error;

import com.ggh.core.web.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum WebtoonErrorCode implements ErrorCode {
    DEFAULT("미정"),

    ;

    private final String description;
}
