package com.jorge.gymapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

/**
 * Entity representing a single set within a workout log.
 * Tracks the weight lifted, number of repetitions, and set number.
 */
@Entity
public class WorkoutSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Parent workout log this set belongs to.
     * JsonBackReference prevents infinite recursion during JSON serialization.
     */
    @ManyToOne(optional = false)
    @JsonBackReference
    private WorkoutLog workoutLog;

    /** Sequential number of this set within the workout (1, 2, 3, etc.) */
    private int setNumber;

    /** Number of repetitions performed */
    private int reps;

    /** Weight used in pounds */
    private double weight;

    public WorkoutSet() {}

    public WorkoutSet(WorkoutLog workoutLog, int setNumber, int reps, double weight) {
        this.workoutLog = workoutLog;
        this.setNumber = setNumber;
        this.reps = reps;
        this.weight = weight;
    }

    // Getters and setters
    public Long getId() { return id; }
    public WorkoutLog getWorkoutLog() { return workoutLog; }
    public void setWorkoutLog(WorkoutLog workoutLog) { this.workoutLog = workoutLog; }
    public int getSetNumber() { return setNumber; }
    public void setSetNumber(int setNumber) { this.setNumber = setNumber; }
    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
}


