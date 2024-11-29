package com.example.demo.controller;

import com.example.demo.FilerSystem.FlashcardStorage;
import com.example.demo.FilerSystem.FolderStorage;
import com.example.demo.FilerSystem.NotesStorage;
//import com.example.demo.FilerSystem.FolderStorage;
import com.example.demo.FilerSystem.NotesStorage;
import com.example.demo.FilerSystem.ToDoStorage;
import com.example.demo.model.*;
import com.example.demo.view.FoldersScreenView;
import com.example.demo.view.NotebookScreenView;
import com.example.demo.view.MainMenuScreenView;
import com.example.demo.view.ToDoListView;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**CHANGES BY NATHAN*/
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Optional;

public class FoldersController {
    private FoldersModel foldersModel;
    private FoldersScreenView foldersScreenView;
    private Stage primaryStage;
    private XPModel xpModel;
    private NavigationController navigationController;
    private Scene foldersScene;
    private ToDoListView toDoListView;

    Notebook lastOpenedNotebook = null;
    private EventHandler<MouseEvent> folderSelectionHandler;
    private EventHandler<MouseEvent> deleteHandler;
    private String folderName;

    public FoldersController(FoldersModel model, FoldersScreenView view, Stage stage, NavigationController navigationController, Scene foldersScene, ToDoListView toDoListView) {
    Notebook lastOpenedNotebook = null;
        this.foldersModel = model;
        this.foldersScreenView = view;
        this.primaryStage = stage;
        this.xpModel = XPManager.getXPModel();
        this.navigationController = navigationController;
        this.foldersScene = foldersScene;
        this.toDoListView = toDoListView;

        // Define folder selection handler
        EventHandler<MouseEvent> folderSelectionHandler = this::handleFolderSelection;
        EventHandler<MouseEvent> deleteHandler = createDeleteHandler(folderSelectionHandler);

        stage.setOnCloseRequest(e -> {
            if (lastOpenedNotebook != null) {
                NotesStorage.SaveNotes(lastOpenedNotebook);
            }
        });

        attachSearchAndSortListeners(folderSelectionHandler, deleteHandler);

        // Populate folders and pass the handler
        foldersScreenView.populateFolders(foldersModel.getFolders(), folderSelectionHandler, deleteHandler);

        // Add event handlers for buttons
        foldersScreenView.getBackButton().setOnAction(e -> goToMainMenu());
        foldersScreenView.getAddFolderButton().setOnAction(e -> addNewFolder(folderSelectionHandler, deleteHandler));
    }

    public String getFolderName(){
        return this.folderName;
    }

    public EventHandler<MouseEvent> getFolderSelectionHandler(){
        return folderSelectionHandler;
    }
    public EventHandler<MouseEvent> getDeleteHandler(){
        return deleteHandler;
    }

    private EventHandler<MouseEvent> createDeleteHandler(EventHandler<MouseEvent> folderSelectionHandler) {
        return event -> {
            Button deleteButton = (Button) event.getSource();
            VBox folderContainer = (VBox) deleteButton.getParent();
            Button folderButton = (Button) folderContainer.getChildren().get(0);
            String folderName = folderButton.getText();
            deleteFolder(folderName, folderSelectionHandler);
        };
    }

    private void deleteFolder(String folderName, EventHandler<MouseEvent> folderSelectionHandler) {



        // Remove the folder from the model
        foldersModel.removeFolder(folderName);


        // Delete the corresponding JSON file from the filesystem


        NotesStorage.DeleteNotebook(folderName);


        System.out.println("Deleted folder: " + folderName);

        // Refresh the folders list
        EventHandler<MouseEvent> deleteHandler = createDeleteHandler(folderSelectionHandler);
        foldersScreenView.populateFolders(foldersModel.getFolders(), folderSelectionHandler, deleteHandler);
    }

    private void goToMainMenu() {
        // Navigate back to the main menu
        primaryStage.setScene(new Scene(new MainMenuScreenView()));
    }

    public void addFoldersXp(double xp) {
        xpModel.addXP(xp);
    }

    private void openNotebook(String folderName) {
        // Open the notebook for the selected folder
        /*

        if (notebookScreenView.getScene() != null) {
            notebookScreenView.getScene().setRoot(new StackPane()); // Detach it by setting a dummy root
        }
        notebookScreenView.setCurrentFolder(folderName);
        notebookScreenView.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        //primaryStage.setScene(new Scene(notebookScreenView)); // Reattach it to the new scene

        Scene notebookScene = new Scene(notebookScreenView);
        primaryStage.setScene(notebookScene); // Reattach it to the new scene

         */

        // Get the notebook associated with the selected folder
        lastOpenedNotebook = NotesStorage.LoadNotes(folderName);

        if (lastOpenedNotebook != null) {
            NotebookScreenView notebookView = new NotebookScreenView(lastOpenedNotebook);
            NotebookController notebookController = new NotebookController(lastOpenedNotebook, notebookView);
            notebookView.runScreenUpdate();

            // Save changes to the notebook when navigating back
            notebookView.getBackButton().setOnAction(e -> {
                saveNotebookState(lastOpenedNotebook); // Use the effectively final variable
                primaryStage.setScene(foldersScene); // Reuse the existing scene
            });


            notebookView.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
            Scene notebookScene = new Scene(notebookView);
            primaryStage.setScene(notebookScene);
        } else {
            System.err.println("Failed to load notebook for folder: " + folderName);
        }

    }

    private void saveNotebookState(Notebook notebook) {
        // Save changes to the notebook into the folders model
        NotesStorage.SaveNotes(notebook);
    }

    private void addNewFolder(EventHandler<MouseEvent> folderSelectionHandler, EventHandler<MouseEvent> deleteHandler) {
        // Add a new folder
        String newFolderName = foldersScreenView.showAddFolderDialog();
        if (newFolderName != null && !newFolderName.trim().isEmpty()) {
            foldersModel.addFolder(newFolderName);

            Notebook newNotebook = foldersModel.getNotebook(newFolderName);
            if (newNotebook.getNotes().isEmpty()) {
                newNotebook.addPage(new Page("Lecture 1"));
            }

            NotesStorage.SaveNotes(newNotebook); // Save the notebook

            // Refresh folder list and reattach handler to all buttons
            foldersScreenView.populateFolders(foldersModel.getFolders(), folderSelectionHandler, deleteHandler);
        }
    }

    private void handleFolderSelection(MouseEvent event) {
        String selectedFolder = ((Button) event.getSource()).getText();
        openNotebook(selectedFolder);
    }

    private void attachSearchAndSortListeners(EventHandler<MouseEvent> folderSelectionHandler, EventHandler<MouseEvent> deleteHandler) {
        foldersScreenView.getSearchField().textProperty().addListener((observable, oldValue, newValue) -> {
            updateFoldersGrid(newValue, foldersScreenView.getCurrentSortOrder(), folderSelectionHandler, deleteHandler);
        });

        foldersScreenView.getSortOptions().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            foldersScreenView.setCurrentSortOrder(newValue); // Update the sort order in the view
            updateFoldersGrid(foldersScreenView.getSearchField().getText(), foldersScreenView.getCurrentSortOrder(), folderSelectionHandler, deleteHandler);
        });
    }


    private void updateFoldersGrid(String searchQuery, String sortOrder,
                                   EventHandler<MouseEvent> folderSelectionHandler,
                                   EventHandler<MouseEvent> deleteHandler) {
        // Provide a default value if sortOrder is null
        String effectiveSortOrder = (sortOrder != null) ? sortOrder : "Name";

        // Filter folders based on the search query
        List<String> filteredFolders = foldersModel.getFolders().stream()
                .filter(name -> name.toLowerCase().contains(searchQuery.toLowerCase())) // Apply search filter
                .collect(Collectors.toList());

        // Sort folders based on the selected sort order
        if (effectiveSortOrder.equals("Oldest First")) {
            filteredFolders.sort(Comparator.comparing(folder -> foldersModel.getFolderMetadata(folder).getCreationDate()));
        } else if (effectiveSortOrder.equals("Newest First")) {
            filteredFolders.sort((folder1, folder2) ->
                    foldersModel.getFolderMetadata(folder2).getCreationDate()
                            .compareTo(foldersModel.getFolderMetadata(folder1).getCreationDate()));
        } else if (effectiveSortOrder.equals("Name")) { // Default to Name sorting
            filteredFolders.sort(String::compareToIgnoreCase);
        }

        // Populate the folders view with the sorted and filtered list
        foldersScreenView.populateFolders(filteredFolders, folderSelectionHandler, deleteHandler);
    }
}




