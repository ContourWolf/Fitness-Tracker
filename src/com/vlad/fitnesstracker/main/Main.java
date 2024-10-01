package com.vlad.fitnesstracker;

import com.vlad.fitnesstracker.model.Person;
import com.vlad.fitnesstracker.service.BMI;
import com.vlad.fitnesstracker.service.CalorieCalculator;
import com.vlad.fitnesstracker.service.WeightManagementCalculator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Collect user data
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your age: ");
        int age = scanner.nextInt();
        System.out.print("Enter your weight (kg): ");
        double weight = scanner.nextDouble();
        System.out.print("Enter your height (cm): ");
        double height = scanner.nextDouble();
        System.out.print("Enter your gender (M/F): ");
        char gender = scanner.next().charAt(0);
        System.out.print("Enter your activity level (1: Sedentary, 2: Lightly active, 3: Moderately active, 4: Very active, 5: Extra active): ");
        String activityLevel = scanner.next();
        System.out.println("Hello, " + name + "!");

        // Create Person object
        Person person = new Person(age, weight, height, String.valueOf(gender), activityLevel);

        // Menu options
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Calculate BMI");
            System.out.println("2. Calculate BMR");
            System.out.println("3. Use Calorie Calculator");
            System.out.println("4. Use Weight Management Calculator");
            System.out.println("0. End Program");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    String bmiResult = BMI.calculateBMI(person);
                    System.out.println(bmiResult);
                    break;
                case 2:
                    double bmr = BMI.calculateBMR(person);
                    System.out.printf("Your BMR is: %.2f kcal/day%n", bmr);
                    break;
                case 3:
                    CalorieCalculator.getCalories();
                    break;
                case 4:
                    WeightManagementCalculator.calculateWeightManagement(person);
                    break;
                case 0:
                    System.out.println("Ending program. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
