package com.vlad.fitnesstracker.service;

import com.vlad.fitnesstracker.model.Person;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.time.temporal.ChronoUnit;

public class WeightManagementCalculator {
    public static void calculateWeightManagement(Person person) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your desired weight (kg): ");
        double desiredWeight = scanner.nextDouble();

        System.out.print("Enter the date until you want to reach your goal (e.g., 'November 19 2024'): ");
        scanner.nextLine(); // Consume newline
        String dateInput = scanner.nextLine(); // Read the entire line

        // Convert input to title case to handle case insensitivity
        String[] words = dateInput.split(" ");
        for (int i = 0; i < words.length; i++) {
            words[i] = capitalizeFirstLetter(words[i]);
        }
        String formattedDateInput = String.join(" ", words);

        // Use DateTimeFormatter to parse the custom date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d yyyy");
        LocalDate goalDate;

        try {
            goalDate = LocalDate.parse(formattedDateInput, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use a valid formal.");
            return; // Exit the method if the date is invalid
        }

        long daysUntilGoal = ChronoUnit.DAYS.between(LocalDate.now(), goalDate);

        double weightToLose = person.getWeight() - desiredWeight;

         double totalDeficit = weightToLose * 7700; // 7700 kcal for 1 kg

        double dailyDeficit = totalDeficit / daysUntilGoal;

       double maintenanceCalories = BMI.calculateMaintenanceCalories(person);

        double dailyCaloricIntake = maintenanceCalories - dailyDeficit;

        double bmr = BMI.calculateBMR(person);
        if (dailyCaloricIntake < bmr) {
            System.out.println("Warning: The calculated daily caloric intake is below your BMR. This may not be sustainable or healthy.");
        }

        System.out.printf("Based on your maintenance calories of %.2f kcal/day, you should consume %.2f kcal/day until %s.%n",
                maintenanceCalories, dailyCaloricIntake, goalDate.toString());
    }

     private static String capitalizeFirstLetter(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }
}
