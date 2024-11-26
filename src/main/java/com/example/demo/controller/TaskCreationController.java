package com.example.demo.controller;

import com.example.demo.FilerSystem.ToDoStorage;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.ZoneOffset;
import com.example.demo.model.ToDoList;
import com.example.demo.view.TaskCreationView;

/*
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 */

/**
 * Controls the task creation process in the To-Do List application.
 * This class interacts with the TaskCreationView to gather user input
 * and updates the ToDoList model with the new tasks.
 */
public class TaskCreationController {
    private ToDoList toDoList; // The model representing the list of tasks
    private TaskCreationView taskCreationView; // The view for creating tasks
    private ToDoListController toDoListController;
    ToDoStorage storage = new ToDoStorage();

    /**
     * Constructs a TaskCreationController with the specified ToDoList and primary stage.
     *
     * @param toDoList The ToDoList instance to which tasks will be added.
     * @param primaryStage The primary stage for the application where the task creation view will be displayed.
     */
    public TaskCreationController(ToDoList toDoList, ToDoListController toDoListController, Stage primaryStage) {
        this.toDoList = toDoList;
        this.taskCreationView = new TaskCreationView(primaryStage, this::createTask);
        primaryStage.show(); // Show the TaskCreationView
        this.toDoListController = toDoListController; // Store the reference
    }


    /**
     * Creates a new task based on user input from the TaskCreationView.
     * This method retrieves the task description and due date, validates the inputs,
     * converts the due date to milliseconds, and adds the new task to the ToDoList.
     * If the task is successfully created, the input fields in the view are cleared.
     */
    private void createTask() {
        String description = taskCreationView.getTaskDescription();
        LocalDate dueDate = taskCreationView.getDueDate();

        if (description != null && dueDate != null) {
            long dueDateMillis = dueDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC) * 1000; // Convert to milliseconds
            toDoList.addTask(description, dueDateMillis);

            ToDoStorage.SaveToDoList(toDoList.getTasks());
            ToDoStorage.LoadToDoList();


            taskCreationView.clearInputs();
            toDoListController.updateTaskListView(); // Refresh the task list view
        }
    }
}

