package com.jorge.gymapp.service;

import com.jorge.gymapp.dto.AuthResponse;
import com.jorge.gymapp.dto.LoginRequest;
import com.jorge.gymapp.dto.RegisterRequest;
import com.jorge.gymapp.model.User;
import com.jorge.gymapp.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for user authentication and management.
 * Handles user registration, login, and token-based authentication.
 */
@Service
public class UserService {
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * Registers a new user with encrypted password.
     * Generates a unique authentication token upon successful registration.
     *
     * @param req RegisterRequest containing username, email, and password
     * @return Optional containing AuthResponse if successful, empty if email already exists
     */
    public Optional<AuthResponse> register(RegisterRequest req) {
        // Check if email is already registered
        if (userRepo.findByEmail(req.getEmail()).isPresent()) {
            return Optional.empty();
        }

        // Hash password and create new user
        String hashed = encoder.encode(req.getPassword());
        User u = new User(req.getUsername(), req.getEmail(), hashed);

        // Generate authentication token
        String token = UUID.randomUUID().toString();
        u.setAuthToken(token);
        u.setTokenExpiresAt(Instant.now().plusSeconds(86400));
        userRepo.save(u);

        return Optional.of(new AuthResponse(token, u.getUsername(), u.getEmail()));
    }

    /**
     * Authenticates a user with email and password.
     * Generates a new authentication token upon successful login.
     *
     * @param req LoginRequest containing email and password
     * @return Optional containing AuthResponse if credentials are valid, empty otherwise
     */
    public Optional<AuthResponse> login(LoginRequest req) {
        Optional<User> uOpt = userRepo.findByEmail(req.getEmail());
        if (uOpt.isEmpty()) {
            return Optional.empty();
        }

        User u = uOpt.get();
        // Verify password matches stored hash
        if (!encoder.matches(req.getPassword(), u.getPasswordHash())) {
            return Optional.empty();
        }

        // Generate new token for this session
        String token = UUID.randomUUID().toString();
        u.setAuthToken(token);
        u.setTokenExpiresAt(Instant.now().plusSeconds(86400));
        userRepo.save(u);

        return Optional.of(new AuthResponse(token, u.getUsername(), u.getEmail()));
    }

    /**
     * Finds a user by their authentication token.
     * Validates that the token exists and has not expired.
     *
     * @param token Authentication token string
     * @return Optional containing User if token is valid and not expired, empty otherwise
     */
    public Optional<User> findByToken(String token) {
        Optional<User> user = userRepo.findByAuthToken(token);
        if (user.isPresent() && user.get().getTokenExpiresAt() != null
                && user.get().getTokenExpiresAt().isAfter(Instant.now())) {
            return user;
        }
        return Optional.empty();
    }

    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}




