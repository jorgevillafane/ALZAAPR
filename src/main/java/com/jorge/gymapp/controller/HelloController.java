package com.jorge.gymapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple health check controller for testing backend connectivity.
 */
@RestController
public class HelloController {

    /**
     * Basic endpoint to verify the backend is running.
     *
     * @return Simple confirmation message
     */
    @GetMapping("/hello")
    public String hello() {
        return "Hello, your Gym App backend is running!";
    }
}

