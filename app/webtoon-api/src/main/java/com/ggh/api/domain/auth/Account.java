package com.ggh.api.domain.auth;

import com.ggh.core.jpa.TimeAuditableEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ACCOUNT")
@Entity
public class Account extends TimeAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "LOGIN_ID")
    private String loginId;

    @Column(name = "CREDENTIAL")
    private String credential;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Builder
    public Account(String loginId, String credential, AccountType type, long userId) {

        if(!StringUtils.hasText(loginId)) {
            throw new IllegalArgumentException("loginId must not be empty");
        }

        if(!StringUtils.hasText(credential)) {
            throw new IllegalArgumentException("password must not be empty");
        }

        this.userId = userId;
        this.loginId = loginId;
        this.credential = credential;
        this.type = type;
        this.status = AccountStatus.ONBOARDING_REQUIRED;
    }

    public void changeStatus(AccountStatus status) {
        this.status = status;
    }
}
