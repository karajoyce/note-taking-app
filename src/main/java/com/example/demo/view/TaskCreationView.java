package com.example.demo.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;

/*
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 */

/**
 * A view for creating a new task in the To-Do List application.
 * This class provides a user interface for entering task details such as
 * description and due date.
 */
public class TaskCreationView {

    private TextField taskDescriptionInput; // Input field for the task description
    private DatePicker dueDatePicker; // Date picker for selecting the due date
    private Button createTaskButton; // Button to create the task
    public GridPane grid;
    private Label titleLabel; // Title label for the "Create New Task" window

    /**
     * Constructs a TaskCreationView with a given primary stage and a callback
     * function that is executed when a new task is created.
     *
     * @param primaryStage The main window of the application.
     * @param onTaskCreated A Runnable that will be called when the task is created.
     */
    public TaskCreationView(Stage primaryStage, Runnable onTaskCreated) {
        primaryStage.setTitle("Create New Task");
        primaryStage.initStyle(StageStyle.UTILITY); // Set as a utility pop-up window
        primaryStage.setFullScreen(false); // Explicitly ensure it’s not fullscreen
        primaryStage.setResizable(false); // Optional: Prevent resizing if desired

        // Layout
        grid = new GridPane();
        grid.setPadding(new javafx.geometry.Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Title label
        titleLabel = new Label("Create New Task");
        titleLabel.getStyleClass().add("task-title"); // Apply custom CSS class
        GridPane.setConstraints(titleLabel, 0, 0, 2, 1); // Span across two columns

        // Task input
        taskDescriptionInput = new TextField();
        taskDescriptionInput.setPromptText("Task Description");
        taskDescriptionInput.getStyleClass().add("task-description-input"); // Apply custom CSS class
        GridPane.setConstraints(taskDescriptionInput, 0, 0);

        dueDatePicker = new DatePicker();
        GridPane.setConstraints(dueDatePicker, 1, 0);

        createTaskButton = new Button("Create Task");
        GridPane.setConstraints(createTaskButton, 0, 1);

        grid.getStylesheets().add(getClass().getResource("/stylesToDoList.css").toExternalForm());

        // Set up the layout
        grid.getChildren().addAll(taskDescriptionInput, dueDatePicker, createTaskButton);
        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);

        createTaskButton.setOnAction(e -> {
            // Call the provided function when the task is created
            onTaskCreated.run();
            primaryStage.close(); // Close the task creation window
        });
    }

    /**
     * Retrieves the task description entered by the user.
     *
     * @return The task description as a String.
     */
    public String getTaskDescription() {
        return taskDescriptionInput.getText();
    }

    /**
     * Retrieves the due date selected by the user.
     *
     * @return The due date as a LocalDate, or null if no date is selected.
     */
    public LocalDate getDueDate() {
        return dueDatePicker.getValue();
    }

    /**
     * Clears the input fields in the task creation view.
     * This includes clearing the task description and resetting the due date.
     */
    public void clearInputs() {
        taskDescriptionInput.clear();
        dueDatePicker.setValue(null);
    }

    public GridPane getTaskCreationView() {
        return grid;
    }

}
