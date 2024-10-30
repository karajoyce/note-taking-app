package com.example.demo.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

public class EditCardView {
    private TextField frontCard; // Input field for the card description
    private TextField backCard; // Input field for the card description
    private Button updateCardButton; // Button to update the card
    private GridPane grid; // to display everything

    public EditCardView(Stage stage, Runnable onTaskCreated){
        stage.setTitle("Add Card");

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

        GridPane.setConstraints(frontCard, 0, 0);
        GridPane.setConstraints(backCard, 1, 0);
        updateCardButton = new Button("Create Card");
        GridPane.setConstraints(updateCardButton, 0, 1);

        // Set up the layout
        grid.getChildren().addAll(frontCard, backCard, updateCardButton);
        Scene scene = new Scene(grid, 500, 350);
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

    public void clearInput(){
        frontCard.clear();
        backCard.clear();
    }

    public GridPane getGrid(){
        return grid;
    }
}
