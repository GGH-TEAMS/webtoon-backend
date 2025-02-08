package com.ggh.api.domain.user;

import com.ggh.core.jpa.TimeAuditableEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "USER")
@Entity
public class User extends TimeAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NICKNAME")
    private String nickname;

    @Column(name = "EMAIL")
    private String email;

    @Builder
    public User(String email, String nickname) {
        if(!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("email must not be empty");
        }

        if (!StringUtils.hasText(nickname)) {
            throw new IllegalArgumentException("nickname must not be null");
        }

        this.email = email;
        this.nickname = nickname;
    }
}
