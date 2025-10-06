package com.jorge.gymapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a workout session log.
 * Contains information about which exercise was performed and when,
 * along with all sets completed during that session.
 */
@Entity
public class WorkoutLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** User who performed this workout */
    @ManyToOne(optional = false)
    private User user;

    /** Exercise that was performed */
    @ManyToOne(optional = false)
    private Exercise exercise;

    /**
     * Collection of all sets performed in this workout session.
     * JsonManagedReference prevents infinite recursion during JSON serialization.
     */
    @OneToMany(mappedBy = "workoutLog", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<WorkoutSet> sets = new ArrayList<>();

    /** Date when the workout was performed */
    private LocalDate date;

    public WorkoutLog() {}

    public WorkoutLog(User user, Exercise exercise, LocalDate date) {
        this.user = user;
        this.exercise = exercise;
        this.date = date;
    }

    // Getters and setters
    public Long getId() { return id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Exercise getExercise() { return exercise; }
    public void setExercise(Exercise exercise) { this.exercise = exercise; }
    public List<WorkoutSet> getSets() { return sets; }
    public void setSets(List<WorkoutSet> sets) { this.sets = sets; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}


