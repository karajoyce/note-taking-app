package com.example.demo.model;

/*
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 */

import com.example.demo.FilerSystem.ToDoStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Represents a to-do list that manages tasks.
 * This class allows adding, removing, editing, and toggling the completion status of tasks.
 */
public class ToDoList {

    private ArrayList<Task> tasks; // List of tasks in the to-do list
    private int taskCounter; // To generate unique task IDs

    /**
     * Constructs a new ToDoList instance.
     * Initializes an empty list of tasks and sets the task counter to 1.
     */
    public ToDoList() {
        this.tasks = new ArrayList<>();
        this.taskCounter = 1;
    }

    /**
     * Adds a new task to the to-do list.
     *
     * @param name The description of the task.
     * @param dueDate The due date of the task, represented as a long value (e.g., a timestamp).
     * @return The newly created Task object.
     */
    public Task addTask(String name, long dueDate) {

        Task newTask = new Task(name, dueDate, false, taskCounter++);
        tasks.add(newTask);

        sortTasksByDueDate(); // Ensure the tasks are sorted after adding

        ToDoStorage.SaveToDoList(tasks);

        return newTask;

    }

    private void sortTasksByDueDate() {
        Collections.sort(tasks, Comparator.comparingLong(Task::getTaskDueDate));
    }


    /**
     * Removes a task from the to-do list based on the task ID.
     *
     * @param taskID The unique identifier of the task to be removed.
     */
    public void removeTask(int taskID) {
        tasks.removeIf(task -> task.getTaskID() == taskID);

    }

    /**
     * Edits an existing task's description and due date.
     *
     * @param taskID The unique identifier of the task to be edited.
     * @param newDescription The new description for the task.
     * @param newDueDate The new due date for the task, represented as a long value (e.g., a timestamp).
     */
    public void editTask(int taskID, String newDescription, long newDueDate) {
        for (Task task : tasks) {
            if (task.getTaskID() == taskID) {
                task.setTaskDescription(newDescription);
                task.setTaskDueDate(newDueDate);
                ToDoStorage.SaveToDoList(tasks);
                break;
            }
        }

    }

    /**
     * Toggles the completion status of a task.
     *
     * @param taskID The unique identifier of the task whose completion status is to be toggled.
     */
    public void toggleTaskCompletion(int taskID) {
        for (Task task : tasks) {
            if (task.getTaskID() == taskID) {
                task.toggleCompleted();
                break;
            }
        }
    }

    /**
     * Retrieves the list of tasks in the to-do list.
     *
     * @return An ArrayList of Task objects.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }


}
