package com.jorge.gymapp.dto;

/**
 * Data Transfer Object for user login requests.
 * Contains credentials needed for authentication.
 */
public class LoginRequest {
    private String email;
    private String password;

    public String getEmail() { return email; }
    public void setEmail(String e) { this.email = e; }
    public String getPassword() { return password; }
    public void setPassword(String p) { this.password = p; }
}

