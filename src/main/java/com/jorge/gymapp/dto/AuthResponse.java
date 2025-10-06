package com.jorge.gymapp.dto;

/**
 * Data Transfer Object for authentication responses.
 * Contains the authentication token and user information after successful login/registration.
 */
public class AuthResponse {
    private String token;
    private String username;
    private String email;

    public AuthResponse(String token, String username, String email) {
        this.token = token;
        this.username = username;
        this.email = email;
    }

    public String getToken() { return token; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
}

