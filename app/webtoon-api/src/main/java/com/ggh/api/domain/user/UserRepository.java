package com.ggh.api.domain.user;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long userId);
    User save(User user);
    void delete(User user);
}
