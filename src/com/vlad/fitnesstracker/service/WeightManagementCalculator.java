package com.vlad.fitnesstracker.service;

import com.vlad.fitnesstracker.model.Person;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.time.temporal.ChronoUnit;

public class WeightManagementCalculator {
    public static double[] calculateWeightManagement(Person person, double desiredWeight, String goalDateInput) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d yyyy");
        LocalDate goalDate;

        try {
            goalDate = LocalDate.parse(goalDateInput, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use a valid format (e.g., 'November 19 2024').");
            return new double[]{0, 0}; // Return zero values for maintenance and daily caloric intake
        }

        long daysUntilGoal = ChronoUnit.DAYS.between(LocalDate.now(), goalDate);

        double weightToLose = person.getWeight() - desiredWeight;
        double totalDeficit = weightToLose * 7700; // 7700 kcal for 1 kg
        double dailyDeficit = totalDeficit / daysUntilGoal;

        double maintenanceCalories = BMI.calculateMaintenanceCalories(person);
        double dailyCaloricIntake = maintenanceCalories - dailyDeficit;

        return new double[]{maintenanceCalories, dailyCaloricIntake};
    }
}
