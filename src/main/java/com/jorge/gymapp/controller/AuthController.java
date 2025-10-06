package com.jorge.gymapp.controller;

import com.jorge.gymapp.dto.AuthResponse;
import com.jorge.gymapp.dto.LoginRequest;
import com.jorge.gymapp.dto.RegisterRequest;
import com.jorge.gymapp.model.User;
import com.jorge.gymapp.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/**
 * REST controller for user authentication operations.
 * Handles user registration, login, and guest access functionality.
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "${ALLOWED_ORIGIN}")  // Add to application.properties
public class AuthController {
    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    /**
     * Registers a new user account.
     *
     * @param req RegisterRequest containing username, email, and password
     * @return Map containing success status and either user data or error message
     */
    @PostMapping("/register")
    public Object register(@RequestBody RegisterRequest req) {
        Optional<AuthResponse> resp = service.register(req);
        return resp.isPresent() ? Map.of("success", true, "data", resp.get()) :
                Map.of("success", false, "message", "Email already in use");
    }

    /**
     * Authenticates an existing user.
     *
     * @param req LoginRequest containing email and password
     * @return Map containing success status and either authentication token or error message
     */
    @PostMapping("/login")
    public Object login(@RequestBody LoginRequest req) {
        Optional<AuthResponse> resp = service.login(req);
        return resp.isPresent() ? Map.of("success", true, "data", resp.get()) :
                Map.of("success", false, "message", "Invalid credentials");
    }

    /**
     * Retrieves current user information based on authentication token.
     *
     * @param token Authentication token from Authorization header
     * @return Map containing user data or error message
     */
    @GetMapping("/me")
    public Object me(@RequestHeader(name = "Authorization", required = false) String token) {
        if (token == null || token.isBlank()) {
            return Map.of("success", false, "message", "No token");
        }
        Optional<User> uopt = service.findByToken(token);
        return uopt.isPresent() ?
                Map.of("success", true, "data", Map.of(
                        "username", uopt.get().getUsername(),
                        "email", uopt.get().getEmail()
                )) :
                Map.of("success", false, "message", "Invalid token");
    }

    /**
     * Provides guest access without requiring registration.
     * Creates a guest user on first access or logs in existing guest user.
     *
     * @return Map containing success status and authentication data
     */
    @PostMapping("/guest-login")
    public Object guestLogin() {
        Optional<User> uOpt = service.findByUsername("guest");

        // Create guest user if it doesn't exist
        if (uOpt.isEmpty()) {
            RegisterRequest guestReq = new RegisterRequest();
            guestReq.setUsername("guest");
            guestReq.setEmail("guest@example.com");
            guestReq.setPassword("guest");

            Optional<AuthResponse> regResp = service.register(guestReq);
            if (regResp.isEmpty()) {
                return Map.of("success", false, "message", "Failed to create guest user");
            }
            return Map.of("success", true, "data", regResp.get());
        }

        // Login existing guest user
        LoginRequest loginReq = new LoginRequest();
        loginReq.setEmail("guest@example.com");
        loginReq.setPassword("guest");

        Optional<AuthResponse> resp = service.login(loginReq);
        return resp.isPresent() ?
                Map.of("success", true, "data", resp.get()) :
                Map.of("success", false, "message", "Guest login failed");
    }
}

