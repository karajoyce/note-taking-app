package com.example.demo.model;

/*
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 */


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Represents a task in a to-do list with a description, due date,
 * completion status, and a unique identifier.
 */
public class Task {

    private String taskDescription;
    private long taskDueDate;
    private boolean taskCompleted = false;
    private int taskID;

    /**
     * Constructs a new Task with the specified description, due date,
     * completion status, and unique identifier.
     *
     * @param taskDescription The description of the task.
     * @param taskDueDate The due date of the task, represented as a long value (e.g., milliseconds since epoch).
     * @param taskCompleted The initial completion status of the task (true if completed, false otherwise).
     * @param taskID The unique identifier for the task.
     */
    public Task(String taskDescription, long taskDueDate, boolean taskCompleted, int taskID) {
        this.taskDescription = taskDescription;
        this.taskDueDate = taskDueDate;
        this.taskCompleted = taskCompleted;
        this.taskID = taskID;
    }

    /**
     * Sets the description of the task.
     *
     * @param description The new description for the task.
     */
    public void setTaskDescription(String description) {
        this.taskDescription = description;

    }

    /**
     * Returns the description of the task.
     *
     * @return The description of the task.
     */
    public String getTaskDescription() {
        return this.taskDescription;
    }

    /**
     * Sets the due date of the task.
     *
     * @param date The new due date for the task, represented as a long value.
     */
    public void setTaskDueDate(long date) {
        this.taskDueDate = date;

    }

    /**
     * Returns the due date of the task.
     *
     * @return The due date of the task, represented as a long value.
     */
    public long getTaskDueDate() {
        return this.taskDueDate;
    }

    /**
     * Toggles the completion status of the task. If the task is currently
     * completed, it will be marked as not completed, and vice versa.
     */
    public void toggleCompleted() {
        this.taskCompleted = !this.taskCompleted;

    }

    /**
     * Returns whether the task is completed.
     *
     * @return true if the task is completed; false otherwise.
     */
    public boolean isCompleted() {
        return taskCompleted;
    }

    /**
     * Returns the unique identifier of the task.
     *
     * @return The unique identifier of the task.
     */
    public int getTaskID() {
        return this.taskID;
    }


    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Set the desired date format
        return taskDescription + " (Due: " + sdf.format(new Date(taskDueDate)) + ")";

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskDueDate == task.taskDueDate && taskID == task.taskID && Objects.equals(taskDescription, task.taskDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskDescription, taskDueDate, taskID);
    }
}
