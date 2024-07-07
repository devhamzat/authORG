package org.devhamzat.authorg.repository;

import org.devhamzat.authorg.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    Optional<User> findUserByUserId(String userId);
    boolean existsByEmail(String email);
}
