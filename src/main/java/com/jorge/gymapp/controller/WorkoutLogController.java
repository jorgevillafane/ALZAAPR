package com.jorge.gymapp.controller;

import com.jorge.gymapp.dto.WorkoutLogRequest;
import com.jorge.gymapp.model.User;
import com.jorge.gymapp.model.WorkoutLog;
import com.jorge.gymapp.service.UserService;
import com.jorge.gymapp.service.WorkoutLogService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for workout logging operations.
 * Handles creation and retrieval of workout logs with associated sets.
 */
@RestController
@RequestMapping("/api/logs")
@CrossOrigin(origins = "${ALLOWED_ORIGIN}")  // Add to application.properties
public class WorkoutLogController {
    private final WorkoutLogService logService;
    private final UserService userService;

    public WorkoutLogController(WorkoutLogService logService, UserService userService) {
        this.logService = logService;
        this.userService = userService;
    }

    /**
     * Creates a new workout log entry for the authenticated user.
     *
     * @param token Authentication token from Authorization header
     * @param req WorkoutLogRequest containing exercise ID, date, and sets data
     * @return Map containing success status and either the created log or error message
     */
    @PostMapping
    public Object addLog(@RequestHeader("Authorization") String token,
                         @RequestBody WorkoutLogRequest req) {
        Optional<User> uOpt = userService.findByToken(token);
        if (uOpt.isEmpty()) {
            return Map.of("success", false, "message", "Invalid token");
        }

        Optional<WorkoutLog> log = logService.addLog(uOpt.get(), req);
        return log.isPresent() ?
                Map.of("success", true, "data", log.get()) :
                Map.of("success", false, "message", "Invalid exerciseId");
    }

    /**
     * Retrieves workout logs for the authenticated user.
     * Optionally filters by date if date parameter is provided.
     *
     * @param token Authentication token from Authorization header
     * @param date Optional date filter in YYYY-MM-DD format
     * @return Map containing success status and list of workout logs
     */
    @GetMapping
    public Object getLogs(@RequestHeader("Authorization") String token,
                          @RequestParam(required = false) String date) {
        Optional<User> uOpt = userService.findByToken(token);
        if (uOpt.isEmpty()) {
            return Map.of("success", false, "message", "Invalid token");
        }

        if (date != null) {
            List<WorkoutLog> logs = logService.getLogsByDate(uOpt.get(), LocalDate.parse(date));
            return Map.of("success", true, "data", logs);
        } else {
            List<WorkoutLog> logs = logService.getLogs(uOpt.get());
            return Map.of("success", true, "data", logs);
        }
    }

    /**
     * Retrieves complete workout history for the authenticated user.
     *
     * @param token Authentication token from Authorization header
     * @return Map containing success status and list of all workout logs
     */
    @GetMapping("/history")
    public Object getHistory(@RequestHeader("Authorization") String token) {
        Optional<User> uOpt = userService.findByToken(token);
        if (uOpt.isEmpty()) {
            return Map.of("success", false, "message", "Invalid token");
        }

        List<WorkoutLog> logs = logService.getLogs(uOpt.get());
        return Map.of("success", true, "data", logs);
    }
}

