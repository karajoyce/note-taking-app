package com.example.demo.model;

import java.util.Timer;
import java.util.TimerTask;

public class BreakReminderModel {
    private long interval;
    private Timer reminderTimer;

    public BreakReminderModel(long interval) {
        this.interval = interval;
    }

    public void startBreakReminder(Runnable onReminder) {
        reminderTimer = new Timer(true);
        reminderTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                onReminder.run();
            }
        }, interval, interval);
    }

    public void stopBreakReminder() {
        if (reminderTimer != null) {
            reminderTimer.cancel();
        }
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }
}
