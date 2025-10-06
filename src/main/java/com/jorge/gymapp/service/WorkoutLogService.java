package com.jorge.gymapp.service;

import com.jorge.gymapp.dto.WorkoutLogRequest;
import com.jorge.gymapp.dto.WorkoutSetRequest;
import com.jorge.gymapp.model.Exercise;
import com.jorge.gymapp.model.User;
import com.jorge.gymapp.model.WorkoutLog;
import com.jorge.gymapp.model.WorkoutSet;
import com.jorge.gymapp.repository.ExerciseRepository;
import com.jorge.gymapp.repository.WorkoutLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for workout logging operations.
 * Handles creation and retrieval of workout logs with associated sets.
 */
@Service
public class WorkoutLogService {
    private final WorkoutLogRepository logRepo;
    private final ExerciseRepository exRepo;

    public WorkoutLogService(WorkoutLogRepository logRepo, ExerciseRepository exRepo) {
        this.logRepo = logRepo;
        this.exRepo = exRepo;
    }

    /**
     * Creates a new workout log for a user with multiple sets.
     * Each set includes weight, reps, and set number.
     *
     * @param user User who performed the workout
     * @param req WorkoutLogRequest containing exercise ID, date, and set data
     * @return Optional containing the saved WorkoutLog if successful, empty if exercise not found
     */
    public Optional<WorkoutLog> addLog(User user, WorkoutLogRequest req) {
        // Verify exercise exists
        Optional<Exercise> exOpt = exRepo.findById(req.getExerciseId());
        if (exOpt.isEmpty()) {
            return Optional.empty();
        }

        // Create workout log entry
        WorkoutLog log = new WorkoutLog(user, exOpt.get(), LocalDate.parse(req.getDate()));

        // Add all sets to the log with sequential numbering
        int index = 1;
        for (WorkoutSetRequest s : req.getSets()) {
            WorkoutSet set = new WorkoutSet(log, index, s.getReps(), s.getWeight());
            log.getSets().add(set);
            index++;
        }

        logRepo.save(log);
        return Optional.of(log);
    }

    /**
     * Retrieves all workout logs for a specific user.
     *
     * @param user User whose logs to retrieve
     * @return List of all WorkoutLog entries for the user
     */
    public List<WorkoutLog> getLogs(User user) {
        return logRepo.findByUser(user);
    }

    /**
     * Retrieves workout logs for a specific user on a specific date.
     *
     * @param user User whose logs to retrieve
     * @param date Date to filter logs by
     * @return List of WorkoutLog entries for the user on the specified date
     */
    public List<WorkoutLog> getLogsByDate(User user, LocalDate date) {
        return logRepo.findByUserAndDate(user, date);
    }
}

