package com.jorge.gymapp.repository;

import com.jorge.gymapp.model.WorkoutSet;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for WorkoutSet entity database operations.
 * Provides standard CRUD operations for workout sets.
 */
public interface WorkoutSetRepository extends JpaRepository<WorkoutSet, Long> {}

