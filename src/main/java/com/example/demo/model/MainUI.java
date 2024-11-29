package com.example.demo.model;

/**
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 **/

import com.example.demo.FilerSystem.ToDoStorage;
import com.example.demo.controller.BreakReminderController;
import com.example.demo.controller.ToDoListController;
import com.example.demo.view.BreakReminderIntervalView;
import com.example.demo.view.BreakReminderView;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.example.demo.view.ToDoListView;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import com.example.demo.model.XPModel;

public class MainUI extends Application {

    private BreakReminderController breakReminderController;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("To-Do List");

        BorderPane pane = new BorderPane();

        // Create and set up the To-Do List
        XPModel xpModel = null;
        ToDoList toDoList = ToDoStorage.LoadToDoList();
        ToDoListView toDoListView = new ToDoListView(toDoList);
        ToDoListController toDoListController = new ToDoListController(toDoList, toDoListView, xpModel);

        // Create a VBox to hold the To-Do List and push it to the bottom
        VBox bottomRightContainer = new VBox();
        bottomRightContainer.setAlignment(Pos.CENTER_RIGHT); // Align to the bottom right
        bottomRightContainer.getChildren().add(toDoListView.getToDoListView());

        pane.setRight(bottomRightContainer); // Add the VBox to the right side of the pane

        // Break Reminder setup
        long defaultInterval = 10 * 1000L; //15 * 60 * 1000L;  Default 15 minutes in milliseconds
        BreakReminderModel breakReminderModel = new BreakReminderModel(defaultInterval);
        BreakReminderView breakReminderView = new BreakReminderView();
        this.breakReminderController = new BreakReminderController(breakReminderModel, breakReminderView);
        breakReminderController.startReminders();

        // Load the icon image
        Image reminderIcon = new Image(getClass().getResourceAsStream("/BreakReminderIcon.png"));

        // Set up the ImageView with the icon
        ImageView reminderIconView = new ImageView(reminderIcon);
        reminderIconView.setFitWidth(80);  // Set width to fit your layout
        reminderIconView.setFitHeight(80); // Set height to fit your layout

        // Button to open the interval setting window
        //Button setReminderIntervalButton = new Button("Set Break Reminder");
        Button setReminderIntervalButton = new Button();
        setReminderIntervalButton.setGraphic(reminderIconView);
        setReminderIntervalButton.setOnAction(e -> openIntervalSettingWindow());

        VBox reminderControl = new VBox(10, setReminderIntervalButton);
        reminderControl.setAlignment(Pos.CENTER);
        pane.setLeft(reminderControl);

        //Scene scene = new Scene(toDoListView.getToDoListView(), 400, 300);
        Scene scene = new Scene(pane, 1000, 1000);
        // Load and apply CSS stylesheet
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // This method opens a new window for setting the break reminder interval
    private void openIntervalSettingWindow() {
        BreakReminderIntervalView intervalView = new BreakReminderIntervalView(breakReminderController);
        intervalView.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}


