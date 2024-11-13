package com.example.demo.model;

import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;

public class BreakReminderModel {
    private long interval;
    private Timer reminderTimer;

    public BreakReminderModel(long interval) {
        this.interval = interval;
    }

    public void startBreakReminder(Runnable onReminder) {
        stopBreakReminder();  // Stop any previous timer before starting a new one
        reminderTimer = new Timer(true);
        reminderTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Wrap the reminder call in Platform.runLater to ensure it runs on the JavaFX Application Thread
                Platform.runLater(onReminder);
            }
        }, interval, interval);
    }

    public void stopBreakReminder() {
        if (reminderTimer != null) {
            reminderTimer.cancel();
            reminderTimer = null;
        }
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }
}
