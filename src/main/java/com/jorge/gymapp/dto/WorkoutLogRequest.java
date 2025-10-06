package com.jorge.gymapp.dto;

import java.util.List;

/**
 * Data Transfer Object for workout log creation requests.
 * Contains the exercise performed, date, and all sets completed.
 */
public class WorkoutLogRequest {
    private Long exerciseId;

    /** Date in YYYY-MM-DD format */
    private String date;

    /** List of all sets performed for this exercise */
    private List<WorkoutSetRequest> sets;

    public Long getExerciseId() { return exerciseId; }
    public void setExerciseId(Long exerciseId) { this.exerciseId = exerciseId; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public List<WorkoutSetRequest> getSets() { return sets; }
    public void setSets(List<WorkoutSetRequest> sets) { this.sets = sets; }
}


