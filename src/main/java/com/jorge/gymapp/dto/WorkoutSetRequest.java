package com.jorge.gymapp.dto;

/**
 * Data Transfer Object representing a single set within a workout log request.
 * Contains weight, reps, and set number information.
 */
public class WorkoutSetRequest {
    private int setNumber;
    private int reps;
    private double weight;

    public int getSetNumber() { return setNumber; }
    public void setSetNumber(int setNumber) { this.setNumber = setNumber; }
    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
}

