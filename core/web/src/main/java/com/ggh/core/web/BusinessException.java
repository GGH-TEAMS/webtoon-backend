package com.ggh.core.web;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BusinessException extends RuntimeException {

    public final ErrorCode errorCode;

    public BusinessException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
