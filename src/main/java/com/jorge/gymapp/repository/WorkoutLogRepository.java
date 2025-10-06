package com.jorge.gymapp.repository;

import com.jorge.gymapp.model.WorkoutLog;
import com.jorge.gymapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for WorkoutLog entity database operations.
 * Provides methods for querying workout logs by user and date.
 */
public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Long> {
    /**
     * Retrieves all workout logs for a specific user.
     */
    List<WorkoutLog> findByUser(User user);

    /**
     * Retrieves workout logs for a specific user on a specific date.
     */
    List<WorkoutLog> findByUserAndDate(User user, LocalDate date);
}
