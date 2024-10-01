package com.vlad.fitnesstracker.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CalorieNinjasAPI {
    private static final String API_URL = "https://api.calorieninjas.com/v1/nutrition?query=";
    private static final String API_KEY = "dpCNupKi+Z7xLMfo8TX+Dg==tejMVcppseCKUto4";

    public static String getNutritionData(String foodItem) {
        try {
            URL url = new URL(API_URL + foodItem);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-Api-Key", API_KEY);
            conn.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
