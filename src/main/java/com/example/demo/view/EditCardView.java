package com.example.demo.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;

public class EditCardView {
    private TextField frontCard; // Input field for the card description
    private TextField backCard; // Input field for the card description
    private Button updateCardButton; // Button to update the card
    private GridPane grid; // to display everything

    public EditCardView(Stage stage, Runnable onTaskCreated){
        stage.setTitle("Add Card");
        stage.initStyle(StageStyle.UTILITY); // Set as a utility pop-up window
        stage.setFullScreen(false); // Explicitly ensure it’s not fullscreen
        stage.setResizable(false); // Optional: Prevent resizing if desired

        // Setting up the layout of the screen
        grid = new GridPane();
        grid.setPadding(new javafx.geometry.Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        // setting up the text areas
        frontCard = new TextField();
        frontCard.setPromptText("Enter front of the card");
        backCard = new TextField();
        backCard.setPromptText("Enter the back of the card");

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
        frontCard.setPrefHeight(100);
        backCard.setPrefHeight(100);

        GridPane.setConstraints(frontCard, 1, 0);
        GridPane.setColumnSpan(frontCard, 3);
        GridPane.setConstraints(backCard, 1, 1);
        GridPane.setColumnSpan(backCard, 3);
        updateCardButton = new Button("Update Card");
        updateCardButton.getStyleClass().add("editbutton");
        updateCardButton.setMinWidth(150);
        GridPane.setConstraints(updateCardButton, 2, 3);

        // Set up the layout
        grid.getChildren().addAll(frontCard, backCard, updateCardButton);
        Scene scene = new Scene(grid, 500, 350);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        grid.getStyleClass().add("grid");
        stage.setScene(scene);

        updateCardButton.setOnAction(e -> {
            // Call the provided function when the task is created
            onTaskCreated.run();
            stage.close(); // Close the task creation window
        });
        }

    public String getFrontDescription(){
        return frontCard.getText();
    }

    public String getBackDescription(){
        return backCard.getText();
    }

//    public void clearInput(){
//        frontCard.clear();
//        backCard.clear();
//    }

//    public GridPane getGrid(){
//        return grid;
//    }
}
