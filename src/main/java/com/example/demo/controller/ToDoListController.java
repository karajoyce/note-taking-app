package com.example.demo.controller;

/*
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 */

import com.example.demo.model.ToDoList;
import com.example.demo.model.XPManager;
import com.example.demo.view.ToDoListView;
import com.example.demo.model.XPModel;
import javafx.stage.Stage;

/**
 * Controls the main To-Do List application, managing the interaction
 * between the ToDoList model and the ToDoListView.
 */
public class ToDoListController  {

    private ToDoList toDoList; // The model representing the list of tasks
    private ToDoListView toDoListView; // The view for displaying the to-do list
    private XPModel xpModel;

    /**
     * Constructs a ToDoListController with the specified ToDoList and primary stage.
     *
     * @param toDoList The ToDoList instance that holds the tasks.
     */
    public ToDoListController(ToDoList toDoList, ToDoListView toDoListView, XPModel xpModel) {
        this.toDoList = toDoList;
        this.toDoListView = toDoListView;
        this.xpModel = XPManager.getXPModel();
        updateTaskListView(); // Populate the view with current tasks

        // Set the action for the "Add Task" button
        toDoListView.setAddTaskButtonAction(e -> {
            new TaskCreationController(toDoList, this, new Stage()); // Open task creation window
            updateTaskListView(); // Refresh the list after adding
        });
    }

    /**
     * Updates the task list view with the current list of tasks from the model.
     */
    public void updateTaskListView() {
        toDoListView.setTaskList(toDoList.getTasks(), xpModel);
    }
}

