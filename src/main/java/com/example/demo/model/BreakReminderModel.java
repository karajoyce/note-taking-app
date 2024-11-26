package com.example.demo.model;

import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;

/*
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 */

/**
 * The BreakReminderModel class manages the scheduling and interval setting for break reminders.
 * It uses a Timer to schedule reminders at specified intervals and provides methods to start,
 * stop, and adjust the reminder interval.
 */
public class BreakReminderModel {
    private long interval;
    private Timer reminderTimer;

    /**
     * Constructs a BreakReminderModel with the specified reminder interval.
     *
     * @param interval the interval in milliseconds between reminders
     */
    public BreakReminderModel(long interval) {
        this.interval = interval;
    }

    /**
     * Starts the break reminder timer. A new reminder timer is created, and the specified
     * reminder action is scheduled to run at fixed intervals.
     *
     * @param onReminder a Runnable to be executed each time the reminder interval elapses
     */
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

    /**
     * Stops the break reminder timer, if it is running, by cancelling the current timer.
     * This method ensures that no further reminders are triggered.
     */
    public void stopBreakReminder() {
        if (reminderTimer != null) {
            reminderTimer.cancel();
            reminderTimer = null;
        }
    }

    /**
     * Sets a new interval for the break reminder.
     * The interval specifies the time in milliseconds between consecutive reminders.
     *
     * @param interval the new interval in milliseconds between reminders
     */
    public void setInterval(long interval) {

        this.interval = interval;
    }
}
