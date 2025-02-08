package com.ggh.api.domain.auth;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AccountType {
    GOOGLE("구글"),
    ;

    private final String description;
}
