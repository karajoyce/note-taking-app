package com.example.demo.view;

import com.example.demo.controller.ToDoListController;
import com.example.demo.model.ToDoList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import java.util.List;
import java.util.Optional;

public class FoldersScreenView extends StackPane {

    private ListView<Button> foldersList;
    private MotivationalMessagesView motivationalMessagesView;
    private EventHandler<MouseEvent> folderSelectionHandler;
    private ToDoListView toDoListV;
    private ToDoListController toDoCont;
    private ToDoList toDoList;
    private Button pageBack; // button to go back to main menu
    private Button addFolderButton;

    public FoldersScreenView() {
        // Initialize components
        pageBack = new Button("Back");

        toDoListV = new ToDoListView();
        toDoList = new ToDoList();
        toDoCont = new ToDoListController(toDoList, toDoListV);
        motivationalMessagesView = new MotivationalMessagesView();

        // Run screen update
        runFoldersScreenUpdate();
    }

    public void runFoldersScreenUpdate() {
        // General setup
        this.getStylesheets().add("/styles.css");
        double screenHeight = Screen.getPrimary().getBounds().getMaxY() - 100;
        double screenWidth = Screen.getPrimary().getBounds().getMaxX() - 100;
        this.getChildren().clear();

        // Main layout
        HBox fullBox = new HBox();
        this.getChildren().add(fullBox);
        fullBox.getStyleClass().add("bigbox");
        fullBox.setMaxWidth(screenWidth);
        fullBox.setMaxHeight(screenHeight);

        // Left panel with Back button
        VBox leftPanel = new VBox();
        pageBack.setMinWidth(100);
        pageBack.setMinHeight(50);
        pageBack.getStyleClass().add("back-button");
        leftPanel.setAlignment(Pos.TOP_LEFT);
        leftPanel.setSpacing(20); // Add some spacing
        leftPanel.getChildren().add(pageBack);
        fullBox.getChildren().add(leftPanel);

        // Center area for folders list
        VBox centerBox = new VBox();
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerBox.getStyleClass().add("center-box");
        centerBox.setSpacing(20); // Add spacing between items
        foldersList = new ListView<>();
        foldersList.setMinWidth(screenWidth * 0.6);
        foldersList.setMinHeight(screenHeight * 0.7); // Adjust height for proper proportion

        // Initialize the Add Folder Button
        addFolderButton = new Button("Add Folder");
        addFolderButton.setMinWidth(120);
        addFolderButton.setMinHeight(40);
        addFolderButton.getStyleClass().add("add-folder-button");

        // Add components to the centerBox
        centerBox.getChildren().addAll(foldersList, addFolderButton);
        fullBox.getChildren().add(centerBox);

        // Right panel with motivational messages and To-Do List
        VBox rightPanel = new VBox();
        rightPanel.setMinWidth(screenWidth * 0.3);
        rightPanel.setAlignment(Pos.TOP_CENTER);
        rightPanel.getStyleClass().add("rightVbox");
        rightPanel.setSpacing(20); // Add spacing
        rightPanel.getChildren().addAll(motivationalMessagesView.getMotivmsgView(), toDoListV.getToDoListView());
        fullBox.getChildren().add(rightPanel);
    }

    public Button getAddFolderButton() {
        return addFolderButton;
    }

    public Button getBackButton() {
        return pageBack;
    }

    public ListView<Button> getFoldersList() {
        return foldersList;
    }

    public void populateFolders(List<String> folderNames) {
        foldersList.getItems().clear();
        for (String folderName : folderNames) {
            Button folderButton = new Button(folderName);
            folderButton.getStyleClass().add("folder-button");
            folderButton.setMaxWidth(Double.MAX_VALUE); // Make the button stretch to fill the width
            folderButton.setAlignment(Pos.CENTER);

            // Attach the folder selection handler if it exists
            if (folderSelectionHandler != null) {
                folderButton.setOnMouseClicked(folderSelectionHandler);
            }

            foldersList.getItems().add(folderButton);
        }
    }

    public void setFolderSelectionHandler(EventHandler<MouseEvent> handler) {
        this.folderSelectionHandler = handler;
    }

    public String showAddFolderDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Folder");
        dialog.setHeaderText("Create a New Folder");
        dialog.setContentText("Folder Name:");
        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }
}
