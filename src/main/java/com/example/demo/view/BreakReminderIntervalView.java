package com.example.demo.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.demo.controller.BreakReminderController;

public class BreakReminderIntervalView {

    private final BreakReminderController breakReminderController;
    private final Stage intervalStage;

    public BreakReminderIntervalView(BreakReminderController breakReminderController) {
        this.breakReminderController = breakReminderController;
        this.intervalStage = new Stage();
        intervalStage.setTitle("Set Break Reminder Interval");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getStyleClass().add("break-reminder-layout"); // Apply layout styling

        Label instructionLabel = new Label("Set Break Reminder Interval (minutes):");
        instructionLabel.getStyleClass().add("reminder-message"); // Apply message styling


        TextField intervalInput = new TextField();
        intervalInput.setPromptText("Enter minutes");

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

        layout.getChildren().addAll(instructionLabel, intervalInput, setIntervalButton);
        Scene scene = new Scene(layout, 350, 150);

        // Apply the CSS file with the existing classes
        scene.getStylesheets().add(getClass().getResource("/stylesToDoList.css").toExternalForm());


        intervalStage.setScene(scene);
    }

    public void show() {
        intervalStage.show();
    }
}