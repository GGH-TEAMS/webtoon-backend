package com.ggh.core.web;

public record ErrorResponse(
        String message,
        ErrorCode errorCode
) {
}
