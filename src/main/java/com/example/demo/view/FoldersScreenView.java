package com.example.demo.view;

import com.example.demo.FilerSystem.ToDoStorage;
import com.example.demo.controller.ToDoListController;
import com.example.demo.model.XPModel;
import com.example.demo.model.ToDoList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.Scene;
import javafx.scene.control.*;
/**CHANGES BY NATHAN*/
import javafx.beans.value.ChangeListener;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.stage.Stage;


/*
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 */

/**
 * Represents the view for managing and displaying folders in the application.
 * This view allows users to interact with folders, including adding, deleting,
 * and selecting folders, while also showing motivational messages and a to-do list.
 */
public class FoldersScreenView extends StackPane {

    private GridPane foldersGrid;
    private MotivationalMessagesView motivationalMessagesView;
    private EventHandler<MouseEvent> folderSelectionHandler;
    private ToDoListView toDoListV;
    private Button pageBack; // Button to go back to main menu
    private Button addFolderButton;
    private XPModel xpModel;
    private ToDoList list;
    private Optional<String> addButtonText;
    /**CHANGES BY NATHAN*/
    private TextField searchField;
    private ChoiceBox<String> sortOptions;
    private String currentSortOrder;
    private Consumer<String> sortOptionListener;

    private ToDoListController toDoCont;
    private Button deleteButton;



    /**
     * Constructs a new `FoldersScreenView` and initializes its components.
     * This includes the folder grid, motivational messages, and to-do list.
     */
    public FoldersScreenView() {
        // Initialize components
        addFolderButton = new Button("Add Folder");

        pageBack = new Button("");
        Image imgB = new Image(getClass().getResourceAsStream("/backArrow.png"));
        ImageView imgViewB = new ImageView(imgB);
        imgViewB.setFitHeight(15);
        imgViewB.setPreserveRatio(true);
        pageBack.setGraphic(imgViewB);

        deleteButton = new Button("");
        Image imgC = new Image(getClass().getResourceAsStream("/deleteIcon.png"));
        ImageView imgViewC = new ImageView(imgC);
        imgViewC.setFitHeight(15);
        imgViewC.setPreserveRatio(true);
        deleteButton.setGraphic(imgViewC);


        motivationalMessagesView = new MotivationalMessagesView();
        this.toDoListV = new ToDoListView(ToDoStorage.LoadToDoList());
        ToDoListController toDoListController = new ToDoListController(list, toDoListV, xpModel);

        /** CHANGES BY NATHAN INITIALIZING*/
        addFolderButton = new Button("Add Folder");
        pageBack = new Button("Back");
        searchField = new TextField();
        sortOptions = new ChoiceBox<>();
        currentSortOrder = "Name";


        // Initialize foldersGrid
        foldersGrid = new GridPane();
        foldersGrid.setHgap(20); // Horizontal gap between items
        foldersGrid.setVgap(20); // Vertical gap between items
        foldersGrid.setAlignment(Pos.CENTER);

        // Run screen update
        runFoldersScreenUpdate();
    }

    /**
     * Updates the layout and content of the folders screen.
     * This method organizes the UI into sections, including the folder grid,
     * motivational messages, and the to-do list.
     */
    public void runFoldersScreenUpdate() {


        // General setup
        this.getStylesheets().add("/styles.css");
        double screenHeight = Screen.getPrimary().getBounds().getMaxY() - 100;
        double screenWidth = Screen.getPrimary().getBounds().getMaxX() - 100;
        this.getChildren().clear();

        // Main layout as HBox (to organize center content and right panel)
        HBox mainLayout = new HBox();
        mainLayout.setSpacing(20); // Space between center and right panels
        mainLayout.setPadding(new Insets(20));
        mainLayout.getStyleClass().add(getClass().getResource("/styles.css").toExternalForm());
        this.getChildren().add(mainLayout);

        // VBox to hold the Top Bar and Folder Grid (center content)
        VBox centerBox = new VBox();
        centerBox.setSpacing(20); // Space between top bar and scrollable folder grid
        centerBox.setPadding(new Insets(20));
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerBox.getStyleClass().add(getClass().getResource("/styles.css").toExternalForm());

        // Ensure the centerBox grows with the available space
        centerBox.setMinWidth(screenWidth * 0.7);
        centerBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(centerBox, javafx.scene.layout.Priority.ALWAYS);

        // Top bar with Back and Add Folder buttons
        HBox topBar = new HBox();
        topBar.setSpacing(10); // Add space between buttons
        topBar.setAlignment(Pos.CENTER_LEFT); // Align buttons to the left
        topBar.setPadding(new Insets(10));
        topBar.getStyleClass().add("top-bar");

        /**CHANGES BY NATHAN SEARCH FIELD SETUP*/
        /*Search field setup*/
        searchField.setPromptText("Search folders");
        searchField.setPrefWidth(screenWidth * 0.4);

        sortOptions.getItems().clear();
        sortOptions.getItems().addAll("Name", "Oldest First", "Newest First");//, "Last Accessed");
        sortOptions.setValue("Oldest First");
        sortOptions.setOnAction(e -> onSortOptionChanged());

        pageBack.setMinWidth(100);
        pageBack.setMinHeight(40);
        pageBack.getStyleClass().add("back-button");


        addFolderButton.setMinWidth(100);
        addFolderButton.setMinHeight(40);
        addFolderButton.getStyleClass().add("add-folder-button");

        topBar.getChildren().addAll(pageBack, addFolderButton, searchField, sortOptions);

        // Add folders grid to a ScrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(foldersGrid);
        scrollPane.getStyleClass().add(getClass().getResource("/styles.css").toExternalForm());
        scrollPane.setPadding(new Insets(10));

        foldersGrid.setAlignment(Pos.TOP_CENTER);
        foldersGrid.setHgap(20);
        foldersGrid.setVgap(20);

        // Add Top Bar and Folder Grid to centerBox
        centerBox.getChildren().addAll(topBar, scrollPane);

        // Right panel with Motivational Messages and To-Do List
        VBox rightPanel = new VBox();
        rightPanel.setSpacing(20); // Space between elements
        rightPanel.setPadding(new Insets(20));
        rightPanel.setAlignment(Pos.TOP_CENTER);
        rightPanel.getStyleClass().add("right-panel");
        rightPanel.setMinWidth(screenWidth * 0.25);
        rightPanel.getStyleClass().add(getClass().getResource("/styles.css").toExternalForm());

        VBox motivContainer = new VBox(motivationalMessagesView.getMotivmsgView());
        motivContainer.setMinHeight(screenHeight * 0.3);
        motivContainer.getStyleClass().add(getClass().getResource("/styles.css").toExternalForm());

        toDoListV.setTaskList(ToDoStorage.LoadToDoList(), this.xpModel);
        VBox todoContainer = new VBox(toDoListV.getToDoListView());
        toDoListV.setTaskList(ToDoStorage.LoadToDoList(), xpModel);
        todoContainer.setMinHeight(screenHeight * 0.3);
        todoContainer.getStyleClass().add("todo-container");

        // Add Motivational Messages and To-Do List to Right Panel
        rightPanel.getChildren().addAll(motivContainer, todoContainer);

        // Add centerBox and rightPanel to mainLayout
        mainLayout.getChildren().addAll(centerBox, rightPanel);
    }

    /**
     * Retrieves the "Add Folder" button.
     *
     * @return The "Add Folder" button.
     */
    public Button getAddFolderButton() {
        return addFolderButton;
    }

    /**
     * Retrieves the "Back" button.
     *
     * @return The "Back" button.
     */
    public Button getBackButton() {
        return pageBack;
    }

    /**
     * Populates the folder grid with the provided folder names.
     * Attaches the appropriate event handlers for selection and deletion.
     *
     * @param folderNames            List of folder names to display.
     * @param folderSelectionHandler Event handler for folder selection.
     * @param deleteHandler          Event handler for folder deletion.
     */
    public void populateFolders(List<String> folderNames, EventHandler<MouseEvent> folderSelectionHandler, EventHandler<MouseEvent> deleteHandler) {
        foldersGrid.getChildren().clear();
        foldersGrid.getChildren().clear(); // Clear all current children in the grid
        int columns = 3; // Number of columns in the grid
        int row = 0, col = 0;

        for (String folderName : folderNames) {
            // Create a button to represent the folder
            Button folderButton = new Button(folderName);
            folderButton.getStyleClass().add("folder-box"); // Apply folder-box styling from CSS
            folderButton.setPrefSize(150, 150); // Set preferred size
            folderButton.setWrapText(true); // Wrap text if folder names are long
            folderButton.setOnMouseClicked(folderSelectionHandler); // Attach the selection handler

            // Create a delete button
            Button deleteButton = new Button("X");
            deleteButton.getStyleClass().add("delete-button"); // Apply delete-button styling from CSS
            deleteButton.setPrefSize(30, 30); // Set preferred size for delete button
            deleteButton.setOnMouseClicked(deleteHandler); // Attach the delete handler

            // Wrap the folder and delete button in a VBox
            VBox folderContainer = new VBox(5);
            folderContainer.setAlignment(Pos.CENTER); // Align content to the center
            folderContainer.getChildren().addAll(folderButton, deleteButton);

            // Add the VBox to the grid
            foldersGrid.add(folderContainer, col, row);
            col++;
            if (col >= columns) {
                col = 0; // Reset column index
                row++;  // Move to the next row
            }
        }
    }


    /**
     * Updates the to-do list view with the latest tasks.
     */
    public void updateToDoListView() {
        toDoListV.setTaskList(ToDoStorage.LoadToDoList(), xpModel);
    }


    /**
     * Displays a dialog for adding a new folder and retrieves the entered folder name.
     *
     * @return The folder name entered by the user, or `null` if canceled.
     */
    public String showAddFolderDialog(List<String> existingFolders) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Folder");
        dialog.setHeaderText(null); // Remove header
        dialog.setGraphic(null); // Remove graphic
        dialog.setContentText("Folder Name:");

        // Use a custom stage to make it always on top
        Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
        dialogStage.setAlwaysOnTop(true); // Keep dialog on top


        // Apply styles to the dialog using CSS
        dialog.getDialogPane().getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        dialog.getDialogPane().getStyleClass().add("cardview");

        // Show the dialog and get the result
        addButtonText = dialog.showAndWait();

        if (addButtonText.isPresent()) {
            // Trim the input to remove leading/trailing whitespace
            String folderName = addButtonText.get().trim();

            // Validate the folder name
            if (folderName.isEmpty()) {
                showErrorDialog("Invalid Folder Name", "The folder name is required.");
                return null; // Invalid name
            }

            if (existingFolders.contains(folderName)) {
                showErrorDialog("Duplicate Folder Name", "A folder with this name already exists.");
                return null; // Duplicate name
            }

            //return folderName; // Valid name
            return addButtonText.get();
        }

        return null; // Dialog was canceled
    }

    private void showErrorDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        //alert.setGraphic(null);
        alert.setContentText(content);

        // Use a custom stage to make it always on top
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.setAlwaysOnTop(true); // Keep dialog on top

        // Apply styles to the alert using CSS
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("cardview");

        alert.showAndWait();
    }


    /**
     * Retrieves the text entered in the "Add Folder" dialog.
     *
     * @return The text entered in the dialog, or `null` if no text was entered.
     */
    public String getAddButtonText(){
        return addButtonText.get();

    }

    // Getters

    public TextField getSearchField() {
        return searchField;
    }

    public ChoiceBox<String> getSortOptions() {
        return sortOptions;
    }

    public String getCurrentSortOrder() {
        return currentSortOrder;
    }

    public void setCurrentSortOrder(String sortOrder) {
        this.currentSortOrder = sortOrder;
    }
    // Trigger the listener when the user changes the sorting option
    private void onSortOptionChanged() {
        if (sortOptionListener != null) {
            String selectedOption = sortOptions.getValue(); // Get selected sort option
            sortOptionListener.accept(selectedOption);     // Notify the listener
        }
    }
    // Setter to allow the controller to attach the listener
    public void setSortOptionListener(Consumer<String> listener) {
        this.sortOptionListener = listener;
    }

    /**
     * Retrieves the root view of the folders screen.
     *
     * @return The `StackPane` representing the folders screen view.
     */
    public StackPane getView() {
        return this;
    }
}