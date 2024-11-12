package com.example.demo.model;

/**
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 **/

import com.example.demo.controller.BreakReminderController;
import com.example.demo.controller.ToDoListController;
import com.example.demo.view.BreakReminderView;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.example.demo.view.ToDoListView;

public class MainUI extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("To-Do List");

        BorderPane pane = new BorderPane();

        // Create and set up the To-Do List
        ToDoList toDoList = new ToDoList();
        ToDoListView toDoListView = new ToDoListView();
        ToDoListController toDoListController = new ToDoListController(toDoList, toDoListView);

        // Create a VBox to hold the To-Do List and push it to the bottom
        VBox bottomRightContainer = new VBox();
        bottomRightContainer.setAlignment(Pos.CENTER_RIGHT); // Align to the bottom right
        bottomRightContainer.getChildren().add(toDoListView.getToDoListView());

        pane.setRight(bottomRightContainer); // Add the VBox to the right side of the pane

        // Initialize the Break Reminder components
        long breakInterval = 9000; // 15 minutes, adjust as needed for testing
        BreakReminderModel breakReminderModel = new BreakReminderModel(breakInterval);
        BreakReminderView breakReminderView = new BreakReminderView();
        BreakReminderController breakReminderController = new BreakReminderController(breakReminderModel, breakReminderView);

        // Start the break reminders
        breakReminderController.startReminders();

        //Scene scene = new Scene(toDoListView.getToDoListView(), 400, 300);
        Scene scene = new Scene(pane, 1000, 1000);
        // Load and apply CSS stylesheet
        scene.getStylesheets().add(getClass().getResource("/stylesToDoList.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


