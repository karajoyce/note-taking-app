package com.example.demo.view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;


/*
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 */


/**
 * The BreakReminderView class represents a pop-up window that notifies the user to take a break.
 * It displays a reminder message and a dismiss button, which allows the user to close the reminder.
 */
public class BreakReminderView extends Parent {
    /*CHANGES MADE BY NATHAN, CHANGING CODE BELOW TO FIX WARNING*/
    private final Stage reminderStage;
    private final Label reminderMessage;
    private final Button dismissButton;

    /**
     * Constructs a BreakReminderView with a styled pop-up window containing a reminder message
     * and a dismiss button. The view is initialized with specific styling and layout settings.
     */
    public BreakReminderView() {
        reminderStage = new Stage();
        reminderStage.setTitle("Break Reminder");
        reminderStage.initStyle(StageStyle.UTILITY); // Set as a utility pop-up window
        reminderStage.setFullScreen(false); // Explicitly ensure it’s not fullscreen
        reminderStage.setResizable(false); // Optional: Prevent resizing if desired
        reminderStage.setAlwaysOnTop(true);

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
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
        reminderStage.setScene(scene);
    }

    /**
     * Displays the break reminder pop-up window.
     * Ensures the window is not fullscreen and is set to a windowed mode before displaying.
     */
    public void showReminder() {
        reminderStage.setFullScreen(false); // Ensure it’s set to windowed mode again before showing
        reminderStage.show();
    }

    /**
     * Hides the break reminder pop-up window.
     */
    public void hideReminder() {
        reminderStage.hide();
    }

    /**
     * Provides access to the dismiss button, allowing external classes to add event handling.
     *
     * @return the dismiss Button used to close the reminder window.
     */
    public Button getDismissButton() {
        return dismissButton;
    }

    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }
}