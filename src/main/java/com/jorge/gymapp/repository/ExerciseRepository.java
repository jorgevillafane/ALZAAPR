package com.jorge.gymapp.repository;

import com.jorge.gymapp.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Exercise entity database operations.
 * Provides standard CRUD operations for exercises.
 */
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {}
