package com.vlad.fitnesstracker.service;

import com.vlad.fitnesstracker.api.CalorieNinjasAPI;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Scanner;

public class CalorieCalculator {
    public static void getCalories() {
        Scanner scanner = new Scanner(System.in);
        double totalCalories = 0.0;
        double totalProtein = 0.0;

        System.out.println("What have you eaten today? Enter the products below (type 'done' to finish):");

        while (true) {
            String foodItem = scanner.nextLine();
            if (foodItem.equalsIgnoreCase("done")) {
                break; // Exit the loop if user types 'done'
            }

            String jsonResponse = CalorieNinjasAPI.getNutritionData(foodItem.replace(" ", "%20"));

            if (jsonResponse != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    JSONArray items = jsonObject.getJSONArray("items");

                    if (items.length() > 0) {
                        JSONObject item = items.getJSONObject(0);
                        String name = item.getString("name");
                        double calories = item.getDouble("calories");
                        double servingSize = item.getDouble("serving_size_g");
                        double fat = item.getDouble("fat_total_g");
                        double protein = item.getDouble("protein_g");
                        double carbohydrates = item.getDouble("carbohydrates_total_g");

                        System.out.print("Enter how much you had of " + name + " (in grams): ");
                        double gramsConsumed = scanner.nextDouble();
                        scanner.nextLine(); // Consume the newline character

                        // Calculate calories and protein based on the amount consumed
                        double caloriesForServings = (calories / servingSize) * gramsConsumed;
                        double proteinForServings = (protein / servingSize) * gramsConsumed;

                        totalCalories += caloriesForServings;
                        totalProtein += proteinForServings;

                        System.out.printf("Food Item: %s%n", name);
                        System.out.printf("Calories for %.1f grams: %.1f kcal%n", gramsConsumed, caloriesForServings);
                        System.out.printf("Protein for %.1f grams: %.1f g%n", gramsConsumed, proteinForServings);
                        System.out.println("---------------");
                        System.out.printf("Total calories consumed today: %.1f kcal%n", totalCalories);
                        System.out.printf("Total protein consumed today: %.1f g%n", totalProtein);
                    } else {
                        System.out.println("No data found for this food item.");
                    }
                } catch (Exception e) {
                    System.out.println("Error parsing the response: " + e.getMessage());
                }
            } else {
                System.out.println("Failed to fetch data for " + foodItem);
            }
        }
    }
}
