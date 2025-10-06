package com.jorge.gymapp.controller;

import com.jorge.gymapp.model.Routine;
import com.jorge.gymapp.repository.RoutineRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for workout routine management.
 * Provides endpoints to access predefined and custom workout routines.
 */
@RestController
@RequestMapping("/api/routines")
@CrossOrigin(origins = "${ALLOWED_ORIGIN}")  // Add to application.properties
public class RoutineController {
    private final RoutineRepository repo;

    public RoutineController(RoutineRepository repo) {
        this.repo = repo;
    }

    /**
     * Retrieves all available workout routines.
     *
     * @return List of all Routine entities (both predefined and custom)
     */
    @GetMapping
    public List<Routine> all() {
        return repo.findAll();
    }
}

