package com.example.demo.controller;

import com.example.demo.FilerSystem.FlashcardStorage;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.util.List;

/*
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 */


/**
 * Controller class for managing folder-related actions in the application.
 * This class handles user interactions with the folders screen, including
 * opening, deleting, and adding folders.
 */
/**CHANGES BY NATHAN*/
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import java.util.Optional;

public class FoldersController {
    private FoldersModel foldersModel; // The model managing folders
    private FoldersScreenView foldersScreenView; // The view for displaying folder-related UI
    private Stage primaryStage; // The main application stage
    private XPModel xpModel; // Model for managing XP-related functionality
    private NavigationController navigationController; // Handles navigation between screens
    private Scene foldersScene; // Scene for displaying the folders screen
    private ToDoListView toDoListView; // Reference to the to-do list view

    Notebook lastOpenedNotebook = null;
    private EventHandler<MouseEvent> folderSelectionHandler;
    private EventHandler<MouseEvent> deleteHandler;
    private String folderName;
    private FlashcardScreenController fCont;
    private String newFolderName;
    private MainMenuScreenView menuScreenView;

    public FoldersController(FoldersModel model, FoldersScreenView view, Stage stage,
                             NavigationController navigationController, Scene foldersScene, ToDoListView toDoListView,
                             MainMenuScreenView menuScreenView, FlashcardScreenController fController) {
    /**
     * Constructs a FoldersController instance.
     *
     * @param model              The folders model containing folder data.
     * @param view               The folders screen view for UI representation.
     * @param stage              The main application stage.
     * @param navigationController Handles navigation between screens.
     * @param foldersScene       The scene for the folders screen.
     * @param toDoListView       Reference to the to-do list view.
     */
    Notebook lastOpenedNotebook = null;
        this.foldersModel = model;
        this.foldersScreenView = view;
        this.primaryStage = stage;
        this.xpModel = XPManager.getXPModel();
        this.navigationController = navigationController;
        this.foldersScene = foldersScene;
        this.toDoListView = toDoListView;
        this.fCont = fController;
        this.menuScreenView = menuScreenView;


        // Define folder selection handler
        folderSelectionHandler = event -> {
            Button selectedButton = (Button) event.getSource();
            folderName = selectedButton.getText();
            openNotebook(folderName);
        };

        stage.setOnCloseRequest(e -> {
            if(lastOpenedNotebook != null) {
                NotesStorage.SaveNotes(lastOpenedNotebook);
            }
        });

        // Folder delete handler
        // Create delete handler using the factory method
        deleteHandler = createDeleteHandler(folderSelectionHandler);

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
        menuScreenView.runMainScreenUpdate();
    }

    public String getFolderName(){
        return this.folderName;
    }

    /**
     * Returns the event handler for folder selection.
     *
     * @return The folder selection event handler.
     */
    public EventHandler<MouseEvent> getFolderSelectionHandler(){
        return folderSelectionHandler;
    }

    /**
     * Returns the event handler for folder deletion.
     *
     * @return The folder deletion event handler.
     */
    public EventHandler<MouseEvent> getDeleteHandler(){
        return deleteHandler;
    }

    /**
     * Creates an event handler for deleting folders.
     *
     * @param folderSelectionHandler The event handler for folder selection.
     * @return An event handler for folder deletion.
     */
    private EventHandler<MouseEvent> createDeleteHandler(EventHandler<MouseEvent> folderSelectionHandler) {
        return event -> {
            Button deleteButton = (Button) event.getSource();
            VBox folderContainer = (VBox) deleteButton.getParent();
            Button folderButton = (Button) folderContainer.getChildren().get(0);
            String folderName = folderButton.getText();
            deleteFolder(folderName, folderSelectionHandler);
        };
    }


    /**
     * Deletes a folder and updates the UI and model.
     *
     * @param folderName             The name of the folder to delete.
     * @param folderSelectionHandler The event handler for folder selection.
     */
    private void deleteFolder(String folderName, EventHandler<MouseEvent> folderSelectionHandler) {



        // Remove the folder from the model
        foldersModel.removeFolder(folderName);
        fCont.deleteDeck(folderName);


        // Delete the corresponding JSON file from the filesystem


        NotesStorage.DeleteNotebook(folderName);


        // Refresh the folders list
        EventHandler<MouseEvent> deleteHandler = createDeleteHandler(folderSelectionHandler);
        foldersScreenView.populateFolders(foldersModel.getFolders(), folderSelectionHandler, deleteHandler);
    }

    /**
     * Navigates back to the main menu.
     */
    private void goToMainMenu() {
        // Navigate back to the main menu
        primaryStage.setScene(new Scene(new MainMenuScreenView()));
    }

    public void addFoldersXp(double xp) {
        xpModel.addXP(xp);
    }


    /**
     * Opens a notebook associated with a specific folder.
     *
     * @param folderName The name of the folder to open.
     */
    public void openNotebook(String folderName) {
        // Get the notebook associated with the selected folder
        lastOpenedNotebook = NotesStorage.LoadNotes(folderName);

        if (lastOpenedNotebook != null) {
            NotebookScreenView notebookView = new NotebookScreenView(lastOpenedNotebook);
            NotebookController notebookController = new NotebookController(lastOpenedNotebook, notebookView);
            notebookView.runScreenUpdate();

            // Save changes to the notebook when navigating back
            notebookView.getBackButton().setOnAction(e -> {
                foldersScreenView.runFoldersScreenUpdate();
                saveNotebookState(lastOpenedNotebook); // Use the effectively final variable
                primaryStage.setScene(foldersScene); // Reuse the existing scene
            });



            notebookView.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
            Scene notebookScene = new Scene(notebookView);
            primaryStage.setScene(notebookScene);
            notebookView.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

            /* Add keybinds */

            notebookScene.getAccelerators().put(
                    new KeyCodeCombination(KeyCode.B, KeyCombination.SHORTCUT_DOWN),
                    new Runnable() {
                        @Override
                        public void run() {
                            notebookController.getNoteView().getNoteController().toggleBold();
                        }
                    });

            notebookScene.getAccelerators().put(
                    new KeyCodeCombination(KeyCode.I, KeyCombination.SHORTCUT_DOWN),
                    new Runnable() {
                        @Override
                        public void run() {
                                notebookController.getNoteView().getNoteController().toggleItalic();
                            }
                    });

            notebookScene.getAccelerators().put(
                    new KeyCodeCombination(KeyCode.U, KeyCombination.SHORTCUT_DOWN),
                    new Runnable() {
                        @Override
                        public void run() {
                            notebookController.getNoteView().getNoteController().toggleUnderline();
                        }
                    });

            notebookScene.getAccelerators().put(
                    new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN),
                    new Runnable() {
                        @Override
                        public void run() {
                            notebookController.getNoteView().getNoteController().toggleStrikethrough();
                        }
                    });


            primaryStage.setScene(notebookScene);
        } else {
                System.err.println("Failed to load notebook for folder: " + folderName);
        }

    }

    /**
     * Saves the state of the specified notebook.
     *
     * @param notebook The notebook to save.
     */
    private void saveNotebookState(Notebook notebook) {
        // Save changes to the notebook into the folders model
        NotesStorage.SaveNotes(notebook);
    }

    /**
     * Adds a new folder to the model and updates the UI.
     *
     * @param folderSelectionHandler The event handler for folder selection.
     * @param deleteHandler          The event handler for folder deletion.
     */
    public void addNewFolder(EventHandler<MouseEvent> folderSelectionHandler, EventHandler<MouseEvent> deleteHandler) {
        // Add a new folder
        String newFolderName = foldersScreenView.showAddFolderDialog(NotesStorage.GenerateNotebookTitles());
        if (newFolderName != null && !newFolderName.trim().isEmpty()) {
            foldersModel.addFolder(newFolderName);
            foldersModel.getFolderMetadata(newFolderName);
            fCont.addFlashcardDeck(newFolderName);

            Notebook newNotebook = foldersModel.getNotebook(newFolderName);

            // Make a new deck assigned to the new notebook
            Deck deck = new Deck(newFolderName);
            FlashcardStorage.SaveDeck(deck);

            if (newNotebook.getNotes().isEmpty()) {
                newNotebook.addPage(new Page("Lecture 1"));
            }

            NotesStorage.SaveNotes(newNotebook); // Save the notebook

            // Refresh folder list and reattach handler to all buttons
            foldersScreenView.populateFolders(foldersModel.getFolders(), folderSelectionHandler, deleteHandler);
        }
    }
    public String getNewFolderName(){
        return newFolderName;
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
        /*if (effectiveSortOrder.equals("Oldest First")) {
            filteredFolders.sort(Comparator.comparing(folder -> foldersModel.getFolderMetadata(folder).getCreationDate()));
        } else if (effectiveSortOrder.equals("Newest First")) {
            filteredFolders.sort((folder1, folder2) ->
                    foldersModel.getFolderMetadata(folder2).getCreationDate()
                            .compareTo(foldersModel.getFolderMetadata(folder1).getCreationDate()));
        } else if (effectiveSortOrder.equals("Name")) { // Default to Name sorting
            filteredFolders.sort(String::compareToIgnoreCase);
        } else if (effectiveSortOrder.equals("Last Accessed")) {
            filteredFolders.sort((folder1, folder2) ->
                    foldersModel.getFolderMetadata(folder2).getLastAccessed()
                            .compareTo(foldersModel.getFolderMetadata(folder1).getLastAccessed()));
        }*/
        // Apply sorting logic
        switch (effectiveSortOrder) {
            case "Last Accessed":
                filteredFolders.sort((folder1, folder2) ->
                        foldersModel.getFolderMetadata(folder2).getLastAccessed()
                                .compareTo(foldersModel.getFolderMetadata(folder1).getLastAccessed()));
                //System.out.println("REACHED SORT LAST ACCESSED");
                /*filteredFolders.sort(Comparator.comparing(folder ->
                        foldersModel.getFolderMetadata(folder).getLastAccessed()));*/
                break;
            case "Oldest First":
                filteredFolders.sort(Comparator.comparing(folder ->
                        foldersModel.getFolderMetadata(folder).getCreationDate()));
                break;
            case "Newest First":
                filteredFolders.sort((folder1, folder2) ->
                        foldersModel.getFolderMetadata(folder2).getCreationDate()
                                .compareTo(foldersModel.getFolderMetadata(folder1).getCreationDate()));
                break;
            default: // Sort by Name
                Collections.sort(filteredFolders);
                break;
        }

        // Populate the folders view with the sorted and filtered list
        foldersScreenView.populateFolders(filteredFolders, folderSelectionHandler, deleteHandler);
    }
}




