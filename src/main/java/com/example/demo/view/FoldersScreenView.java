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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import java.util.List;
import java.util.Optional;
import javafx.geometry.Insets;

public class FoldersScreenView extends StackPane {

    private GridPane foldersGrid;
    private MotivationalMessagesView motivationalMessagesView;
    private EventHandler<MouseEvent> folderSelectionHandler;
    private ToDoListView toDoListV;
    private Button pageBack; // Button to go back to main menu
    private Button addFolderButton;

    public FoldersScreenView() {
        // Initialize components
        pageBack = new Button("Back");
        toDoListV = new ToDoListView();
        motivationalMessagesView = new MotivationalMessagesView();

        // Initialize foldersGrid
        foldersGrid = new GridPane();
        foldersGrid.setHgap(20); // Horizontal gap between items
        foldersGrid.setVgap(20); // Vertical gap between items
        foldersGrid.setAlignment(Pos.CENTER);

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
        leftPanel.setMinWidth(screenWidth * 0.15);
        leftPanel.setAlignment(Pos.TOP_CENTER);
        leftPanel.getStyleClass().add("left-panel");
        pageBack.setMinWidth(120);
        pageBack.setMinHeight(40);
        pageBack.getStyleClass().add("back-button");
        leftPanel.getChildren().add(pageBack);
        fullBox.getChildren().add(leftPanel);

        // Center area for folders grid
        VBox centerBox = new VBox();
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerBox.getStyleClass().add("center-box");
        centerBox.setSpacing(20);
        centerBox.setPadding(new Insets(20));
        centerBox.setMinWidth(screenWidth * 0.5);

        // Add folders grid to a container
        VBox gridContainer = new VBox();
        gridContainer.getStyleClass().add("grid-container");
        gridContainer.setAlignment(Pos.CENTER);
        gridContainer.setPadding(new Insets(20));
        gridContainer.setSpacing(10);

        // Add the grid to the container
        gridContainer.getChildren().add(foldersGrid);

        // Add the Add Folder Button
        addFolderButton = new Button("Add Folder");
        addFolderButton.setMinWidth(150);
        addFolderButton.setMinHeight(50);
        addFolderButton.getStyleClass().add("add-folder-button");
        gridContainer.getChildren().add(addFolderButton);

        centerBox.getChildren().add(gridContainer);
        fullBox.getChildren().add(centerBox);

        // Right panel with motivational messages and To-Do List
        VBox rightPanel = new VBox();
        rightPanel.setMinWidth(screenWidth * 0.25);
        rightPanel.setAlignment(Pos.TOP_CENTER);
        rightPanel.getStyleClass().add("right-panel");
        rightPanel.setSpacing(20);

        VBox motivContainer = new VBox(motivationalMessagesView.getMotivmsgView());
        motivContainer.setMinHeight(screenHeight * 0.3);
        motivContainer.getStyleClass().add("motivation-container");

        VBox todoContainer = new VBox(toDoListV.getToDoListView());
        todoContainer.setMinHeight(screenHeight * 0.3);
        todoContainer.getStyleClass().add("todo-container");

        rightPanel.getChildren().addAll(motivContainer, todoContainer);
        fullBox.getChildren().add(rightPanel);
    }

    public Button getAddFolderButton() {
        return addFolderButton;
    }

    public Button getBackButton() {
        return pageBack;
    }

    public void populateFolders(List<String> folderNames) {
        foldersGrid.getChildren().clear();
        int columns = 3; // Number of columns in the grid
        int row = 0, col = 0;

        for (String folderName : folderNames) {
            Button folderButton = new Button(folderName);
            folderButton.getStyleClass().add("folder-box");
            folderButton.setPrefSize(150, 150); // Set preferred size of folder boxes
            folderButton.setWrapText(true); // Allow text wrapping for long folder names

            // Attach the folder selection handler if it exists
            if (folderSelectionHandler != null) {
                folderButton.setOnMouseClicked(folderSelectionHandler);
            }

            foldersGrid.add(folderButton, col, row);
            col++;
            if (col >= columns) {
                col = 0;
                row++;
            }
        }
    }

    public void setFolderSelectionHandler(EventHandler<MouseEvent> handler) {
        this.folderSelectionHandler = handler;
    }

    public String showAddFolderDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Folder");
        dialog.setHeaderText(null); // Remove header
        dialog.setGraphic(null); // Remove graphic
        dialog.setContentText("Folder Name:");

        // Apply styles to the dialog using CSS
        dialog.getDialogPane().getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        dialog.getDialogPane().getStyleClass().add("cardview");

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    public StackPane getView() {
        return this;
    }
}