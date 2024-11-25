package com.example.demo.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import java.util.List;

public class FoldersScreenView extends StackPane {

    private Button backButton;
    private ListView<Button> foldersList;
    private ToDoListView toDoListView;
    private MotivationalMessagesView motivationalMessagesView;

    public FoldersScreenView() {
        // General setup
        this.getStyleClass().add("wholescreen");
        double screenHeight = Screen.getPrimary().getBounds().getMaxY() - 100;
        double screenWidth = Screen.getPrimary().getBounds().getMaxX() - 100;

        // Main layout
        HBox fullBox = new HBox();
        this.getChildren().add(fullBox);
        fullBox.getStyleClass().add("bigbox");
        fullBox.setMaxWidth(screenWidth);
        fullBox.setMaxHeight(screenHeight);

        // Left panel with Back button
        VBox leftPanel = new VBox();
        backButton = new Button("Back");
        backButton.setMinWidth(100);
        backButton.setMinHeight(50);
        backButton.getStyleClass().add("back-button");
        leftPanel.getChildren().add(backButton);
        fullBox.getChildren().add(leftPanel);

        // Center area for folders list
        VBox centerBox = new VBox();
        centerBox.setAlignment(Pos.CENTER);
        centerBox.getStyleClass().add("center-box");
        foldersList = new ListView<>();
        foldersList.setMinWidth(screenWidth * 0.6);
        foldersList.setMinHeight(screenHeight);
        centerBox.getChildren().add(foldersList);
        fullBox.getChildren().add(centerBox);

        // Right panel with motivational messages and ToDoList
        VBox rightPanel = new VBox();
        motivationalMessagesView = new MotivationalMessagesView();
        toDoListView = new ToDoListView();
        rightPanel.getChildren().addAll(motivationalMessagesView, toDoListView);
        fullBox.getChildren().add(rightPanel);
    }

    public Button getBackButton() {
        return backButton;
    }

    public ListView<Button> getFoldersList() {
        return foldersList;
    }

    public void populateFolders(List<String> folderNames) {
        foldersList.getItems().clear();
        for (String folderName : folderNames) {
            Button folderButton = new Button(folderName);
            folderButton.getStyleClass().add("folder-button");
            foldersList.getItems().add(folderButton);
        }
    }
}
