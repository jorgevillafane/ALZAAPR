package com.jorge.gymapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Gym Tracker application.
 * This Spring Boot application provides a REST API for tracking workouts,
 * managing exercises, and organizing fitness routines.
 *
 * @author Jorge L. Villafane Guerra
 * @version 1.0
 */
@SpringBootApplication
public class GymAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(GymAppApplication.class, args);
	}
}
