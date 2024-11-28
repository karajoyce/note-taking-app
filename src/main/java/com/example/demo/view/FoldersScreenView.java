package com.example.demo.view;

import com.example.demo.FilerSystem.ToDoStorage;
import com.example.demo.controller.ToDoListController;
import com.example.demo.model.XPModel;
import com.example.demo.model.ToDoList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
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
import javafx.geometry.Insets;

import javax.swing.plaf.TableHeaderUI;

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

    private ToDoListController toDoCont;
    private Button deleteButton;


    public FoldersScreenView() {
        // Initialize components
        addFolderButton = new Button("Add Folder");

        //pageBack = new Button("Back");
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
        ToDoList toDoList = new ToDoList();
        this.toDoListV = new ToDoListView();
        ToDoListController toDoListController = new ToDoListController(toDoList, toDoListV, xpModel);



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

        // Main layout as HBox (to organize center content and right panel)
        HBox mainLayout = new HBox();
        mainLayout.setSpacing(20); // Space between center and right panels
        mainLayout.setPadding(new Insets(20));
        //mainLayout.getStyleClass().add("main-layout");
        mainLayout.getStyleClass().add(getClass().getResource("/styles.css").toExternalForm());
        this.getChildren().add(mainLayout);

        // VBox to hold the Top Bar and Folder Grid (center content)
        VBox centerBox = new VBox();
        centerBox.setSpacing(20); // Space between top bar and scrollable folder grid
        centerBox.setPadding(new Insets(20));
        centerBox.setAlignment(Pos.TOP_CENTER);
        //centerBox.getStyleClass().add("center-box");
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

        pageBack.setMinWidth(100);
        pageBack.setMinHeight(40);
        pageBack.getStyleClass().add("back-button");


        addFolderButton.setMinWidth(100);
        addFolderButton.setMinHeight(40);
        addFolderButton.getStyleClass().add("add-folder-button");

        topBar.getChildren().addAll(pageBack, addFolderButton);

        // Add folders grid to a ScrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(foldersGrid);
        //scrollPane.getStyleClass().add("scroll-pane");
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

        VBox todoContainer = new VBox(toDoListV.getToDoListView());
        toDoListV.setTaskList(ToDoStorage.LoadToDoList(), xpModel);
        todoContainer.setMinHeight(screenHeight * 0.3);
        todoContainer.getStyleClass().add("todo-container");

        // Add Motivational Messages and To-Do List to Right Panel
        rightPanel.getChildren().addAll(motivContainer, todoContainer);

        // Add centerBox and rightPanel to mainLayout
        mainLayout.getChildren().addAll(centerBox, rightPanel);
    }

    public Button getAddFolderButton() {
        return addFolderButton;
    }

    public Button getBackButton() {
        return pageBack;
    }

    public void populateFolders(List<String> folderNames, EventHandler<MouseEvent> folderSelectionHandler, EventHandler<MouseEvent> deleteHandler) {
        foldersGrid.getChildren().clear();
        //folderNames  = NotesStorage.GenerateNotebookTitles();
        int columns = 3; // Number of columns in the grid
        int row = 0, col = 0;

        for (String folderName : folderNames) {
            // Create a button for the folder
            Button folderButton = new Button(folderName);
            folderButton.getStyleClass().add("folder-box");
            folderButton.setPrefSize(150, 150); // Set preferred size of folder boxes
            folderButton.setWrapText(true); // Allow text wrapping for long folder names

            // Attach the folder selection handler

            folderButton.setOnMouseClicked(folderSelectionHandler);

            // Create a delete button for the folder
            Button deleteButton = new Button("X");
            deleteButton.getStyleClass().add("delete-button");
            deleteButton.setPrefSize(30, 30); // Set size for delete button
            deleteButton.setOnMouseClicked(deleteHandler);

            // Add folder button and delete button to a container (VBox)
            VBox folderContainer = new VBox(5); // 5px spacing
            folderContainer.setAlignment(Pos.CENTER);
            folderContainer.getChildren().addAll(folderButton, deleteButton);

            foldersGrid.add(folderContainer, col, row);
            col++;
            if (col >= columns) {
                col = 0;
                row++;
            }
        }
    }
    public void updateToDoListView() {
        toDoListV.setTaskList(ToDoList.getTasks(), xpModel);
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

        addButtonText = dialog.showAndWait();

        return addButtonText.orElse(null);
    }

    public String getAddButtonText(){
        return addButtonText.get();
    }

    public StackPane getView() {
        return this;
    }
}