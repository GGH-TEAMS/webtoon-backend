package com.ggh.api.domain.auth;

import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findByLoginId(String loginId);
    Boolean existsByLoginId(String loginId);
    Account save(Account account);
    void delete(Account account);
}
