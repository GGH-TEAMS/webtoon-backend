package com.ggh.api.error;

import com.ggh.core.web.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MediaFileErrorCode implements ErrorCode {
    DEFAULT("미정"),

    // A00 (기본 공통 에러)
    A00400("잘못된 요청입니다."),
    A00403("권한이 없습니다."),
    A00404("리소스를 찾을 수 없습니다."),
    A00500("서버 내부 오류가 발생하였습니다."),
    ;

    private final String description;
}
