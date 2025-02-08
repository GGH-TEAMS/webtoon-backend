package com.ggh.api.security.token;

import com.ggh.api.domain.auth.AccountStatus;

public record TokenInfo(
        Object userId,
        AccountStatus accountStatus
) {
}
