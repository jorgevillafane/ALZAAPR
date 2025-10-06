package com.jorge.gymapp.model;

import jakarta.persistence.*;

/**
 * Entity representing a physical exercise.
 * Includes information about target muscles and exercise description.
 */
@Entity
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String primaryMuscle;
    private String secondaryMuscle;
    private String description;

    public Exercise() {}

    public Exercise(String name, String primaryMuscle, String secondaryMuscle, String description) {
        this.name = name;
        this.primaryMuscle = primaryMuscle;
        this.secondaryMuscle = secondaryMuscle;
        this.description = description;
    }

    // Getters and setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String n) { this.name = n; }
    public String getPrimaryMuscle() { return primaryMuscle; }
    public void setPrimaryMuscle(String m) { this.primaryMuscle = m; }
    public String getSecondaryMuscle() { return secondaryMuscle; }
    public void setSecondaryMuscle(String m) { this.secondaryMuscle = m; }
    public String getDescription() { return description; }
    public void setDescription(String d) { this.description = d; }
}

