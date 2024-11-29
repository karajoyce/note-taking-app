package com.example.demo.controller;
import com.example.demo.model.BreakReminderModel;
import com.example.demo.view.BreakReminderView;

/*
CMPT 370, T05, Team 4, Prof. Jon Lovering
        Kara Leier, kjl061, 11293306
        Nathan Balilis, ncb421, 11295020
        Trushank Lakdawala, nus429, 11350445
        Jinny Kim, yek738, 11304174
        Sara Shakeel, gvk731, 11367521
        */


/**
 * The BreakReminderController manages interactions between the BreakReminderModel and BreakReminderView.
 * It controls the initiation, suspension, and interval adjustment of break reminders, as well as
 * the display of reminder notifications to the user.
 */
public class BreakReminderController {

    private BreakReminderModel model;
    private BreakReminderView view;
    private boolean isReminderOn = false;

    /**
     * Constructs a BreakReminderController with the specified model and view.
     * Sets up the action for the "Dismiss" button in the view.
     *
     * @param model the BreakReminderModel that manages the reminder interval and task scheduling
     * @param view  the BreakReminderView that displays reminders to the user
     */
    public BreakReminderController(BreakReminderModel model, BreakReminderView view) {
        this.model = model;
        this.view = view;

        // Set up the dismiss button action to hide the reminder when clicked
        view.getDismissButton().setOnAction(e -> view.hideReminder());
    }


    /**
     * Starts displaying break reminders based on the interval set in the model.
     * Ensures that reminders only start if they are not already active.
     */
    public void startReminders() {
        if (!isReminderOn) {
            model.startBreakReminder(() -> view.showReminder());
            isReminderOn = true;
        }
    }

    /**
     * Stops displaying break reminders by cancelling any ongoing reminder tasks in the model.
     */
    public void stopReminders() {
        model.stopBreakReminder();
        isReminderOn = false;
    }

    /**
     * Sets a new interval for the break reminders. If reminders are currently active,
     * they are restarted to apply the new interval.
     *
     * @param intervalInMilliseconds the interval between reminders, in milliseconds
     */
    public void setReminderInterval(long intervalInMilliseconds) {
        model.setInterval(intervalInMilliseconds);  // Convert minutes to milliseconds
        if (isReminderOn) {
            stopReminders();
            startReminders();  // Restart with the new interval
        }
    }
}
