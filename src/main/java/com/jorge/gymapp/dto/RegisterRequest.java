package com.jorge.gymapp.dto;

/**
 * Data Transfer Object for user registration requests.
 * Contains the required information to create a new user account.
 */
public class RegisterRequest {
    private String username;
    private String email;
    private String password;

    // Getters and setters
    public String getUsername() { return username; }
    public void setUsername(String u) { this.username = u; }
    public String getEmail() { return email; }
    public void setEmail(String e) { this.email = e; }
    public String getPassword() { return password; }
    public void setPassword(String p) { this.password = p; }
}

