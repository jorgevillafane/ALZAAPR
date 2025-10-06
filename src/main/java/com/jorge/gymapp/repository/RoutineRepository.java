package com.jorge.gymapp.repository;

import com.jorge.gymapp.model.Routine;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Routine entity database operations.
 * Provides standard CRUD operations for workout routines.
 */
public interface RoutineRepository extends JpaRepository<Routine, Long> {}
