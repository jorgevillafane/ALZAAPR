package com.jorge.gymapp.controller;

import com.jorge.gymapp.model.Exercise;
import com.jorge.gymapp.repository.ExerciseRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for exercise management.
 * Provides endpoints to retrieve exercise information.
 */
@RestController
@RequestMapping("/api/exercises")
@CrossOrigin(origins = "${ALLOWED_ORIGIN}")  // Add to application.properties
public class ExerciseController {
    private final ExerciseRepository repo;

    public ExerciseController(ExerciseRepository repo) {
        this.repo = repo;
    }

    /**
     * Retrieves all available exercises from the database.
     *
     * @return List of all Exercise entities
     */
    @GetMapping
    public List<Exercise> all() {
        return repo.findAll();
    }
}

