package com.example.demo.controller;
import com.example.demo.model.BreakReminderModel;
import com.example.demo.view.BreakReminderView;



public class BreakReminderController {

    private BreakReminderModel model;
    private BreakReminderView view;
    private boolean isReminderOn = false;

    public BreakReminderController(BreakReminderModel model, BreakReminderView view) {
        this.model = model;
        this.view = view;

        view.getDismissButton().setOnAction(e -> view.hideReminder());
    }

    public void startReminders() {
        if (!isReminderOn) {
            model.startBreakReminder(() -> view.showReminder());
            isReminderOn = true;
        }
    }

    public void stopReminders() {
        model.stopBreakReminder();
        isReminderOn = false;
    }

    public void toggleReminders() {
        if (isReminderOn) {
            stopReminders();
        } else {
            startReminders();
        }
    }

    public void setReminderInterval(long intervalInMilliseconds) {
        model.setInterval(intervalInMilliseconds);  // Convert minutes to milliseconds
        if (isReminderOn) {
            stopReminders();
            startReminders();  // Restart with the new interval
        }
    }
}
