package com.example.demo.controller;

import com.example.demo.FilerSystem.NotesStorage;
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
    private FlashcardScreenController fCont;

    public FoldersController(FoldersModel model, FoldersScreenView view, Stage stage, NavigationController navigationController, Scene foldersScene, ToDoListView toDoListView, FlashcardScreenController fController) {
        this.foldersModel = model;
        this.foldersScreenView = view;
        this.primaryStage = stage;
        this.xpModel = XPManager.getXPModel();
        this.navigationController = navigationController;
        this.foldersScene = foldersScene;
        this.toDoListView = toDoListView;
        this.fCont = fController;

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
        fCont.deleteDeck(folderName);


        // Delete the corresponding JSON file from the filesystem
        NotesStorage.DeleteNotebook(folderName);


        // Refresh the folders list
        EventHandler<MouseEvent> deleteHandler = createDeleteHandler(folderSelectionHandler);
        foldersScreenView.populateFolders(foldersModel.getFolders(), folderSelectionHandler, deleteHandler);
    }

    private void goToMainMenu() {
        // Navigate back to the main menu
        primaryStage.setScene(new Scene(new MainMenuScreenView()));
    }

    public void addFoldersXp(double xp){
        xpModel.addXP(xp);
    }

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

    private void saveNotebookState(Notebook notebook) {
        // Save changes to the notebook into the folders model
        NotesStorage.SaveNotes(notebook);
    }

    public void addNewFolder(EventHandler<MouseEvent> folderSelectionHandler, EventHandler<MouseEvent> deleteHandler) {
        // Add a new folder
        String newFolderName = foldersScreenView.showAddFolderDialog();
        if (newFolderName != null && !newFolderName.trim().isEmpty()) {
            foldersModel.addFolder(newFolderName);
            fCont.addFlashcardDeck(newFolderName);

            Notebook newNotebook = foldersModel.getNotebook(newFolderName);
            if (newNotebook.getNotes().isEmpty()) {
                newNotebook.addPage(new Page("Lecture 1"));
            }

            NotesStorage.SaveNotes(newNotebook); // Save the notebook

            // Refresh folder list and reattach handler to all buttons
            foldersScreenView.populateFolders(foldersModel.getFolders(), folderSelectionHandler, deleteHandler);
        }
    }
}




