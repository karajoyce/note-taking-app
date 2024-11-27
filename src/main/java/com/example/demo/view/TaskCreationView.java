package com.example.demo.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setFullScreen(false);
        primaryStage.setResizable(false);

        // Layout
        grid = new GridPane();
        grid.setPadding(new javafx.geometry.Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);
        //grid.setStyle("-fx-background-color: #ff99cc;"); // Set background color for the GridPane

        // Title label
        titleLabel = new Label("Create New Task");
        titleLabel.getStyleClass().add("task-title");
        GridPane.setConstraints(titleLabel, 0, 0, 2, 1);

        // Task input
        taskDescriptionInput = new TextField();
        taskDescriptionInput.setPromptText("Task Description");
        taskDescriptionInput.getStyleClass().add("task-description-input");
        GridPane.setConstraints(taskDescriptionInput, 0, 0);

        dueDatePicker = new DatePicker();
        GridPane.setConstraints(dueDatePicker, 1, 0);

        // Create Task Button and HBox for centering
        createTaskButton = new Button("Create Task");
        HBox buttonContainer = new HBox(createTaskButton);
        buttonContainer.setAlignment(Pos.CENTER);
        GridPane.setConstraints(buttonContainer, 0, 1, 2, 1); // Span two columns

        grid.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        // Set up the layout
        grid.getChildren().addAll(titleLabel, taskDescriptionInput, dueDatePicker, buttonContainer);
        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);

        createTaskButton.setOnAction(e -> {
            String description = taskDescriptionInput.getText().trim(); // Trim whitespace

            if (description.isEmpty()) {
                // Show an error message if the description is empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Task Description Required");
                alert.setContentText("Please enter a description for the task.");
                alert.initStyle(StageStyle.UTILITY);
                alert.getDialogPane().getStyleClass().add(getClass().getResource("/styles.css").toExternalForm());
                alert.getDialogPane().getStyleClass().add("custom-alert"); // Add a custom style class
                alert.showAndWait(); // Display the alert
            } else {
                // Proceed with task creation if valid
                onTaskCreated.run();
                primaryStage.close();
            }
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
