package com.example.demo.controller;
import com.example.demo.model.BreakReminderModel;
import com.example.demo.view.BreakReminderView;



public class BreakReminderController {

    private BreakReminderModel model;
    private BreakReminderView view;

    public BreakReminderController(BreakReminderModel model, BreakReminderView view) {
        this.model = model;
        this.view = view;

        view.getDismissButton().setOnAction(e -> view.hideReminder());
    }

    public void startReminders() {
        model.startBreakReminder(() -> view.showReminder());
    }

    public void stopReminders() {
        model.stopBreakReminder();
    }

    public void setReminderInterval(long interval) {
        model.setInterval(interval);
    }
}
