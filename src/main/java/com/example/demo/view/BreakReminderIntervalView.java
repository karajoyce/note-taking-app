package com.example.demo.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.demo.controller.BreakReminderController;
import javafx.stage.StageStyle;

/*
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 */


/**
 * The BreakReminderIntervalView class represents a user interface for setting the interval
 * between break reminders. It displays a window where users can input the reminder interval
 * in minutes, which is then converted to milliseconds and applied to the reminder system.
 */
public class BreakReminderIntervalView {

    private final BreakReminderController breakReminderController;
    private final Stage intervalStage;


    /**
     * Constructs a BreakReminderIntervalView with a specified BreakReminderController.
     *
     * @param breakReminderController the controller that manages the break reminder functionality
     */
    public BreakReminderIntervalView(BreakReminderController breakReminderController) {
        this.breakReminderController = breakReminderController;
        this.intervalStage = new Stage();
        intervalStage.setTitle("Set Break Reminder Interval");
        intervalStage.initStyle(StageStyle.UTILITY); // Set as a utility pop-up window
        intervalStage.setFullScreen(false); // Explicitly ensure it’s not fullscreen
        intervalStage.setResizable(false); // Prevent resizing if desired
        intervalStage.setAlwaysOnTop(true);

        // Layout for interval setting window
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getStyleClass().add("break-reminder-layout"); // Apply layout styling

        // Label with instructions for setting the interval
        Label instructionLabel = new Label("Set Break Reminder Interval (minutes):");
        instructionLabel.getStyleClass().add("reminder-message"); // Apply message styling

        // Text field for entering the interval
        TextField intervalInput = new TextField();
        intervalInput.setPromptText("Enter minutes");



        // Button to set the interval based on user input
        Button setIntervalButton = new Button("Set Interval");
        setIntervalButton.getStyleClass().add("reminder-dismiss-button"); // Apply button styling
        setIntervalButton.setOnAction(e -> {
            try {
                long minutes = Long.parseLong(intervalInput.getText());
                long intervalInMilliseconds = minutes * 60 * 1000; // Convert to milliseconds
                breakReminderController.setReminderInterval(intervalInMilliseconds);
                breakReminderController.startReminders();
                intervalStage.close(); // Close the window after setting the interval
            } catch (NumberFormatException ex) {
                intervalInput.setText("Invalid input");
            }
        });

        // Add all elements to the layout
        layout.getChildren().addAll(instructionLabel, intervalInput, setIntervalButton);
        Scene scene = new Scene(layout, 350, 150);

        // Apply the CSS file with the existing classes
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());


        intervalStage.setScene(scene);
    }

    /**
     * Displays the interval setting window to the user.
     */
    public void show() {
        intervalStage.setFullScreen(false);
        intervalStage.show();
    }
}