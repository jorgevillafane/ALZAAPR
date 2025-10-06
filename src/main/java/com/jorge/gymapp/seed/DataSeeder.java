package com.jorge.gymapp.seed;

import com.jorge.gymapp.model.Exercise;
import com.jorge.gymapp.model.Routine;
import com.jorge.gymapp.repository.ExerciseRepository;
import com.jorge.gymapp.repository.RoutineRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Database seeder that runs on application startup.
 * Populates the database with initial exercise and routine data if tables are empty.
 * This ensures the application has default data available for immediate use.
 */
@Component
public class DataSeeder implements CommandLineRunner {
    private final ExerciseRepository exRepo;
    private final RoutineRepository rRepo;

    public DataSeeder(ExerciseRepository exRepo, RoutineRepository rRepo) {
        this.exRepo = exRepo;
        this.rRepo = rRepo;
    }

    /**
     * Seeds the database with default exercises and predefined routines.
     * Only runs if the respective tables are empty to avoid duplicate data.
     *
     * @param args Command line arguments (not used)
     */
    @Override
    public void run(String... args) throws Exception {
        // Seed exercises if none exist
        if (exRepo.count() == 0) {
            // Compound movements - foundational strength exercises
            exRepo.save(new Exercise("Squat", "Legs", "Glutes", "Barbell back squat"));
            exRepo.save(new Exercise("Bench Press", "Chest", "Triceps", "Barbell bench press"));
            exRepo.save(new Exercise("Deadlift", "Back", "Legs", "Conventional deadlift"));
            exRepo.save(new Exercise("Overhead Press", "Shoulders", "Triceps", "Standing barbell press"));
            exRepo.save(new Exercise("Barbell Row", "Back", "Biceps", "Bent-over row"));

            // Additional exercises for variety
            exRepo.save(new Exercise("Pull-up", "Back", "Biceps", "Bodyweight pull-up"));
            exRepo.save(new Exercise("Leg Press", "Legs", "Glutes", "Machine leg press"));
            exRepo.save(new Exercise("Dumbbell Curl", "Biceps", "Forearms", "DB curl"));
        }

        // Seed predefined routines if none exist
        if (rRepo.count() == 0) {
            // Popular strength training programs
            rRepo.save(new Routine("Starting Strength", "3x per week linear progression", true));
            rRepo.save(new Routine("StrongLifts 5x5", "Barbell program 3x/week", true));
            rRepo.save(new Routine("5/3/1", "Periodic strength program", true));
            rRepo.save(new Routine("Push/Pull/Legs", "Split routine", true));
            rRepo.save(new Routine("Upper/Lower", "4-day split", true));
        }
    }
}

