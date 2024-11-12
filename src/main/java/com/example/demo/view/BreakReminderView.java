package com.example.demo.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BreakReminderView {
    private Stage reminderStage;
    private Label reminderMessage;
    private Button dismissButton;

    public BreakReminderView() {
        reminderStage = new Stage();
        reminderMessage = new Label("Time to take a break!");
        dismissButton = new Button("Dismiss");

        VBox layout = new VBox(10, reminderMessage, dismissButton);
        Scene scene = new Scene(layout, 200, 100);
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
