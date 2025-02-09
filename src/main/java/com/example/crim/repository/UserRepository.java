package com.example.crim.repository;

import com.example.crim.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository for User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    // Find users whose username contains the given keyword.
    List<User> findByUsernameContaining(String username);

    // Find a user by email (if needed).
    User findByEmail(String email);
}
