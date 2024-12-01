package com.example.demo.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.util.Objects;

public class NewPageView {
    private final TextField title; // Input field for the card description

    public NewPageView(Stage stage, Runnable onTaskCreated) {
        stage.setTitle("Add Page");

        // Setting up the layout of the screen
        // to display everything
        GridPane grid = new GridPane();
        grid.setPadding(new javafx.geometry.Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        // setting up the text areas
        title = new TextField("Enter the title of the new page.");

        // set up rows and columns
        grid.getColumnConstraints().add(new ColumnConstraints(50));
        grid.getColumnConstraints().add(new ColumnConstraints(50));
        grid.getColumnConstraints().add(new ColumnConstraints(150));
        grid.getColumnConstraints().add(new ColumnConstraints(50));
        grid.getColumnConstraints().add(new ColumnConstraints(50));
        grid.getRowConstraints().add(new RowConstraints(100));
        grid.getRowConstraints().add(new RowConstraints(100));

        // place things in said rows and columns
        grid.setAlignment(Pos.CENTER);
        title.setPrefHeight(100);

        GridPane.setConstraints(title, 1, 0);
        GridPane.setColumnSpan(title, 3);
        // Button to update the card
        Button createPageButton = new Button("Add Page");
        createPageButton.getStyleClass().add("editbutton");
        createPageButton.setMinWidth(150);
        GridPane.setConstraints(createPageButton, 1, 3);

        // Set up the layout
        grid.getChildren().addAll(title, createPageButton);
        Scene scene = new Scene(grid, 500, 350);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
        grid.getStyleClass().add("grid");
        stage.setAlwaysOnTop(true);
        stage.setScene(scene);

        createPageButton.setOnAction(e -> {
            // Call the provided function when the task is created
            onTaskCreated.run();
            stage.close(); // Close the task creation window
        });
    }

    public String getTitle() {
        return title.getText();
    }
}


