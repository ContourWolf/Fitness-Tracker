package com.vlad.fitnesstracker.service;

import com.vlad.fitnesstracker.model.Person;

public class BMR {
    public static double calculateBMR(Person person) {
        double bmr;

        if (person.getGender().equalsIgnoreCase("M")) {
            bmr = 10 * person.getWeight() + 6.25 * person.getHeight() - 5 * person.getAge() + 5;
        } else {
            bmr = 10 * person.getWeight() + 6.25 * person.getHeight() - 5 * person.getAge() - 161;
        }

        return bmr;
    }
}
