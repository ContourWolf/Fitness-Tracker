package com.vlad.fitnesstracker.service;

import com.vlad.fitnesstracker.model.Person;

import javax.swing.*;

public class BMI {
    public static String calculateBMI(Person person) {
        double heightInMeters = person.getHeight() / 100;
        double bmi = person.getWeight() / (heightInMeters * heightInMeters);

        String classification;
        if (bmi < 18.5) {
            classification = "Underweight";
        } else if (bmi < 24.9) {
            classification = "Normal weight";
        } else if (bmi < 29.9) {
            classification = "Overweight";
        } else {
            classification = "Obese";
        }

        return String.format("Your BMI is: %.2f (%s)", bmi, classification);
    }

    public static double calculateBMR(Person person) {
        double bmr;
        if (person.getGender().equalsIgnoreCase("M")) {
            bmr = 10 * person.getWeight() + 6.25 * person.getHeight() - 5 * person.getAge() + 5;
        } else {
            bmr = 10 * person.getWeight() + 6.25 * person.getHeight() - 5 * person.getAge() - 161;
        }
        return bmr;
    }

    public static double calculateMaintenanceCalories(Person person) {
        double bmr = calculateBMR(person);
        double activityMultiplier = getActivityMultiplier(person.getActivityLevel());
        return bmr * activityMultiplier;
    }

    private static double getActivityMultiplier(String activityLevel) {
        switch (activityLevel) {
            case "1":
                return 1.2;
            case "2":
                return 1.375;
            case "3":
                return 1.55;
            case "4":
                return 1.725;
            case "5":
                return 1.9;
            default:
                return 1.2;
        }
    }

    public static void showBMICalculation(Person person) {
        String result = calculateBMI(person);
        JOptionPane.showMessageDialog(null, result, "BMI Result", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showBMRCalculation(Person person) {
        double bmr = calculateBMR(person);
        String result = String.format("Your BMR is: %.2f", bmr);
        JOptionPane.showMessageDialog(null, result, "BMR Result", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showMaintenanceCaloriesCalculation(Person person) {
        double maintenanceCalories = calculateMaintenanceCalories(person);
        String result = String.format("Your maintenance calories are: %.2f", maintenanceCalories);
        JOptionPane.showMessageDialog(null, result, "Maintenance Calories Result", JOptionPane.INFORMATION_MESSAGE);
    }
}
