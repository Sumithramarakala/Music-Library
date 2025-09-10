package com.userService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userService.entity.User; // <-- Import your entity, not Spring Security's User

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by username for login
    Optional<User> findByUsername(String username);
}
