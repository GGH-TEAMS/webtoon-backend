package com.ggh.api.domain.auth;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AccountStatus {
    ONBOARDING_REQUIRED("회원 가입 후 온보딩이 필요한 계정"),
    ACTIVE("활성화된 계정"),
    INACTIVE("비활성화된 계정")
    ;

    private final String description;
}
