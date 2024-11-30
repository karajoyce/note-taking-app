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

    private final ArrayList<Task> tasks; // List of tasks in the to-do list
    private int taskCounter; // To generate unique task IDs

    /**
     * Constructs a new ToDoList instance.
     * Initializes an empty list of tasks and sets the task counter to 1.
     */
    public ToDoList(ArrayList<Task> tasks) {
        //this.tasks = new ArrayList<>();
        this.tasks = tasks;
        //this.taskCounter = 1;
        this.taskCounter = 1;

        XPModel xpModel = XPManager.getXPModel();
    }

    /*FROM NATHAN WEIRD ERROR THAT DRK HOW TO FIX*/
    //WHY IS IT TRYING TO MAKE IT VOID AAAAA
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

    /**
     * Sorts a task by due date
     */
    private void sortTasksByDueDate() {
        Collections.sort(tasks, Comparator.comparingLong(Task::getTaskDueDate));
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

