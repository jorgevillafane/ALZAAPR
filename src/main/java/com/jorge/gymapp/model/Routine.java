package com.jorge.gymapp.model;

import jakarta.persistence.*;

/**
 * Entity representing a workout routine.
 * Can be either predefined (e.g., StrongLifts 5x5) or custom user-created.
 */
@Entity
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 1000)
    private String description;

    /** True for system-provided routines, false for user-created */
    private boolean isPredefined = false;

    public Routine() {}

    public Routine(String name, String desc, boolean predefined) {
        this.name = name;
        this.description = desc;
        this.isPredefined = predefined;
    }

    // Getters and setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String n) { this.name = n; }
    public String getDescription() { return description; }
    public void setDescription(String d) { this.description = d; }
    public boolean isPredefined() { return isPredefined; }
    public void setPredefined(boolean p) { this.isPredefined = p; }
}

