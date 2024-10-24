package com.vlad.fitnesstracker.service;

import com.vlad.fitnesstracker.api.CalorieNinjasAPI;
import org.json.JSONArray;
import org.json.JSONObject;

public class CalorieCalculator {
    private double totalCalories = 0.0;
    private double totalProtein = 0.0;

    public void resetTotals() {
        totalCalories = 0.0;
        totalProtein = 0.0;
    }

    public String calculateCaloriesForFood(String foodItem, double gramsConsumed) {
        StringBuilder result = new StringBuilder();

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
                    double protein = item.getDouble("protein_g");

                    // Calculate calories and protein for the consumed amount
                    double caloriesForServings = (calories / servingSize) * gramsConsumed;
                    double proteinForServings = (protein / servingSize) * gramsConsumed;

                    totalCalories += caloriesForServings;
                    totalProtein += proteinForServings;

                    // Build the result string
                    result.append(String.format("Food Item: %s%n", name));
                    result.append(String.format("Calories for %.1f grams: %.1f kcal%n", gramsConsumed, caloriesForServings));
                    result.append(String.format("Protein for %.1f grams: %.1f g%n", gramsConsumed, proteinForServings));
                    result.append(String.format("Total calories consumed today: %.1f kcal%n", totalCalories));
                    result.append(String.format("Total protein consumed today: %.1f g%n", totalProtein));
                } else {
                    result.append("No data found for this food item.");
                }
            } catch (Exception e) {
                result.append("Error parsing the response: ").append(e.getMessage());
            }
        } else {
            result.append("Failed to fetch data for ").append(foodItem);
        }
        return result.toString(); // Return the result as a String
    }
}
