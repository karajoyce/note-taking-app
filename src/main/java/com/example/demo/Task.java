package com.example.demo;

public class Task {

    private String taskDescription;
    private long taskDueDate;
    private boolean taskCompleted = false;
    private int taskID;

    public Task(String taskDescription, long taskDueDate, boolean taskCompleted, int taskID) {
        this.taskDescription = taskDescription;
        this.taskDueDate = taskDueDate;
        this.taskCompleted = taskCompleted;
        this.taskID = taskID;
    }

    public void setTaskDescription(String description) {
        this.taskDescription = description;

    }

    public String getTaskDescription() {
        return this.taskDescription;
    }

    public void setTaskDueDate(long date) {
        this.taskDueDate = date;

    }

    public long getTaskDueDate() {
        return this.taskDueDate;
    }

    public void toggleCompleted() {
        this.taskCompleted = true;

    }

    public int getTaskID() {
        return this.taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }
}
