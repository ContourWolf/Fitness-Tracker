package com.vlad.fitnesstracker.gui;

import com.vlad.fitnesstracker.model.Person;
import com.vlad.fitnesstracker.service.BMI;
import com.vlad.fitnesstracker.service.WeightManagementCalculator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FitnessTrackerGUI extends JFrame {
    private JTextField nameField, ageField, heightField, weightField;
    private JComboBox<String> genderComboBox, activityLevelComboBox;

    public FitnessTrackerGUI() {
        setTitle("Fitness Tracker");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);


        JLabel nameLabel = new JLabel("What is your name?");
        nameLabel.setBounds(20, 20, 150, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(200, 20, 165, 25);
        add(nameField);


        JLabel ageLabel = new JLabel("How old are you?");
        ageLabel.setBounds(20, 60, 150, 25);
        add(ageLabel);

        ageField = new JTextField();
        ageField.setBounds(200, 60, 165, 25);
        add(ageField);


        JLabel heightLabel = new JLabel("How tall are you? (cm)");
        heightLabel.setBounds(20, 100, 150, 25);
        add(heightLabel);

        heightField = new JTextField();
        heightField.setBounds(200, 100, 165, 25);
        add(heightField);


        JLabel weightLabel = new JLabel("How much do you weigh? (kg)");
        weightLabel.setBounds(20, 140, 200, 25);
        add(weightLabel);

        weightField = new JTextField();
        weightField.setBounds(200, 140, 165, 25);
        add(weightField);


        JLabel genderLabel = new JLabel("Select your gender:");
        genderLabel.setBounds(20, 180, 150, 25);
        add(genderLabel);

        genderComboBox = new JComboBox<>(new String[]{"Male", "Female"});
        genderComboBox.setBounds(200, 180, 165, 25);
        add(genderComboBox);


        JLabel activityLevelLabel = new JLabel("Select your activity level:");
        activityLevelLabel.setBounds(20, 220, 150, 25);
        add(activityLevelLabel);

        activityLevelComboBox = new JComboBox<>(new String[]{"Sedentary", "Lightly active", "Moderately active", "Very active"});
        activityLevelComboBox.setBounds(200, 220, 165, 25);
        add(activityLevelComboBox);


        JButton nextButton = new JButton("Next");
        nextButton.setBounds(150, 280, 100, 30);
        add(nextButton);


        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSecondMenu();
            }
        });
    }

    private void showSecondMenu() {
        JFrame secondMenuFrame = new JFrame("Fitness Tracker - Second Menu");
        secondMenuFrame.setSize(400, 300);
        secondMenuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        secondMenuFrame.setLayout(null);


        JButton bmiButton = new JButton("BMI Calculator");
        bmiButton.setBounds(50, 50, 300, 30);
        secondMenuFrame.add(bmiButton);


        JButton bmrButton = new JButton("BMR Calculator");
        bmrButton.setBounds(50, 100, 300, 30);
        secondMenuFrame.add(bmrButton);


        JButton calorieCounterButton = new JButton("Calorie Counter");
        calorieCounterButton.setBounds(50, 150, 300, 30);
        secondMenuFrame.add(calorieCounterButton);


        JButton weightCalculatorButton = new JButton("Weight Calculator");
        weightCalculatorButton.setBounds(50, 200, 300, 30);
        secondMenuFrame.add(weightCalculatorButton);


        bmiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateBMI();
            }
        });


        bmrButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateBMR();
            }
        });


        calorieCounterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CalorieCalculatorGUI().setVisible(true);
            }
        });


        weightCalculatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateWeightManagement();
            }
        });

        secondMenuFrame.setVisible(true);
    }

    private void calculateBMI() {
        double height = Double.parseDouble(heightField.getText()) / 100; // Convert cm to meters
        double weight = Double.parseDouble(weightField.getText());
        double bmi = weight / (height * height); // BMI formula

        String weightStatus;
        if (bmi < 18.5) {
            weightStatus = "Underweight";
        } else if (bmi < 24.9) {
            weightStatus = "Normal weight";
        } else if (bmi < 29.9) {
            weightStatus = "Overweight";
        } else {
            weightStatus = "Obesity";
        }

        JOptionPane.showMessageDialog(this, String.format("Your BMI is: %.2f\nStatus: %s", bmi, weightStatus));
    }

    private void calculateBMR() {
        double height = Double.parseDouble(heightField.getText());
        double weight = Double.parseDouble(weightField.getText());
        int age = Integer.parseInt(ageField.getText());
        String gender = (String) genderComboBox.getSelectedItem();

        double bmr;
        if (gender.equals("Male")) {

            bmr = 10 * weight + 6.25 * height - 5 * age + 5;
        } else {

            bmr = 10 * weight + 6.25 * height - 5 * age - 161;
        }

        JOptionPane.showMessageDialog(this, String.format("Your BMR is: %.2f kcal/day", bmr));
    }

    private void calculateWeightManagement() {

        Person person = new Person(
                nameField.getText(), // name
                Integer.parseInt(ageField.getText()),   // age
                Integer.parseInt(heightField.getText()), // height
                Double.parseDouble(weightField.getText()), // weight
                (String) activityLevelComboBox.getSelectedItem() // activity level
        );

        String desiredWeightInput = JOptionPane.showInputDialog(this, "Enter your desired weight (kg):");
        double desiredWeight;

        try {
            desiredWeight = Double.parseDouble(desiredWeightInput);
            String goalDateInput = JOptionPane.showInputDialog(this, "Enter the date until you want to reach your goal (e.g., 'november 19 2024'):");


            goalDateInput = goalDateInput.substring(0, 1).toUpperCase() + goalDateInput.substring(1).toLowerCase();


            double[] results = WeightManagementCalculator.calculateWeightManagement(person, desiredWeight, goalDateInput);

            // Unpack the results
            double maintenanceCalories = results[0];
            double dailyCaloricIntake = results[1];
            LocalDate goalDate = LocalDate.parse(goalDateInput, DateTimeFormatter.ofPattern("MMMM d yyyy"));


            double bmr = BMI.calculateBMR(person);


            StringBuilder message = new StringBuilder();
            if (dailyCaloricIntake < bmr) {
                message.append("Warning: The calculated daily caloric intake is below your BMR. This may not be sustainable or healthy.\n");
            }

            message.append(String.format("Based on your maintenance calories of %.2f kcal/day, you should consume %.2f kcal/day until %s.",
                    maintenanceCalories, dailyCaloricIntake, goalDate.toString()));


            JOptionPane.showMessageDialog(this, message.toString());

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for weight.");
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use a valid format (e.g., 'November 19 2024').");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FitnessTrackerGUI gui = new FitnessTrackerGUI();
            gui.setVisible(true);
        });
    }
}
