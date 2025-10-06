package com.jorge.gymapp.repository;

import com.jorge.gymapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository interface for User entity database operations.
 * Provides methods for user lookup by email, token, and username.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by their email address.
     * Used for login and registration validation.
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by their authentication token.
     * Used for validating authenticated requests.
     */
    Optional<User> findByAuthToken(String token);

    /**
     * Finds a user by their username.
     * Used for guest login functionality.
     */
    Optional<User> findByUsername(String username);
}


