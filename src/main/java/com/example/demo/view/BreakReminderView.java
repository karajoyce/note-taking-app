package com.example.demo.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class BreakReminderView {
    private Stage reminderStage;
    private Label reminderMessage;
    private Button dismissButton;

    public BreakReminderView() {
        reminderStage = new Stage();
        reminderStage.setTitle("Break Reminder");

        // Create and style the reminder message
        reminderMessage = new Label("You've been working for too long.\nTime to take a break!");

        // Create and style the dismiss button
        dismissButton = new Button("Dismiss");

        // Add CSS classes for specific styling
        reminderMessage.getStyleClass().add("reminder-message");
        dismissButton.getStyleClass().add("reminder-dismiss-button");


        // Set up the VBox layout
        VBox layout = new VBox(15, reminderMessage, dismissButton);
        layout.setAlignment(Pos.CENTER);
        layout.getStyleClass().add("break-reminder-layout");

        // Set up the scene and apply it to the stage
        Scene scene = new Scene(layout, 300, 150);

        // Apply the stylesheet
        scene.getStylesheets().add(getClass().getResource("/stylesToDoList.css").toExternalForm());
        reminderStage.setScene(scene);
    }

    public void showReminder() {
        reminderStage.show();
    }

    public void hideReminder() {
        reminderStage.hide();
    }

    public Button getDismissButton() {
        return dismissButton;
    }
}