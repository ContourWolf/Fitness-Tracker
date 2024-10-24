package com.vlad.fitnesstracker.gui;

import com.vlad.fitnesstracker.service.CalorieCalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalorieCalculatorGUI extends JFrame {
    private JTextField foodField, gramsField;
    private JTextArea resultArea;
    private CalorieCalculator calorieCalculator;

    public CalorieCalculatorGUI() {
        calorieCalculator = new CalorieCalculator();
        calorieCalculator.resetTotals(); // Reset totals for a new session

        setTitle("Calorie Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        // Food input
        JLabel foodLabel = new JLabel("What have you eaten today?");
        foodField = new JTextField();
        panel.add(foodLabel);
        panel.add(foodField);

        // Grams input
        JLabel gramsLabel = new JLabel("How much in grams?");
        gramsField = new JTextField();
        panel.add(gramsLabel);
        panel.add(gramsField);

        // Button to calculate
        JButton calculateButton = new JButton("Calculate");
        panel.add(calculateButton);

        // Result area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setPreferredSize(new Dimension(350, 100));

        // Add components to the frame
        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Add event listener for the Calculate button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateCalories();
            }
        });
    }

    private void calculateCalories() {
        String foodItem = foodField.getText();
        String gramsText = gramsField.getText();

        try {
            double grams = Double.parseDouble(gramsText);

            // Call the service to calculate calories and protein
            String result = calorieCalculator.calculateCaloriesForFood(foodItem, grams);

            // Display the result in the result area
            resultArea.append(result + "\n----------------------\n");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for grams.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalorieCalculatorGUI gui = new CalorieCalculatorGUI();
            gui.setVisible(true);
        });
    }
}
