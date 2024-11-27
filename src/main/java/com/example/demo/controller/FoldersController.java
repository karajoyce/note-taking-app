package com.example.demo.controller;

import com.example.demo.FilerSystem.NotesStorage;
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

public class FoldersController {
    private FoldersModel foldersModel;
    private FoldersScreenView foldersScreenView;
    private Stage primaryStage;
    private NotebookScreenView notebookScreenView;
    private XPModel xpModel;
    private NavigationController navigationController;
    private Scene foldersScene;
    private ToDoListView toDoListView;

    public FoldersController(FoldersModel model, FoldersScreenView view, Stage stage, NotebookScreenView notebookView, NavigationController navigationController, Scene foldersScene, ToDoListView toDoListView) {
        this.foldersModel = model;
        this.foldersScreenView = view;
        this.primaryStage = stage;
        this.notebookScreenView = notebookView;
        this.xpModel = XPManager.getXPModel();
        this.navigationController = navigationController;
        this.foldersScene = foldersScene;
        this.toDoListView = toDoListView;

        // Define folder selection handler
        EventHandler<MouseEvent> folderSelectionHandler = event -> {
            Button selectedButton = (Button) event.getSource();
            String folderName = selectedButton.getText();
            openNotebook(folderName);
        };

        // Folder delete handler
        // Create delete handler using the factory method
        EventHandler<MouseEvent> deleteHandler = createDeleteHandler(folderSelectionHandler);

        // Populate folders and pass the handler
        foldersScreenView.populateFolders(foldersModel.getFolders(), folderSelectionHandler, deleteHandler);

        // Add event handlers for buttons
        foldersScreenView.getBackButton().setOnAction(e -> goToMainMenu());
        foldersScreenView.getAddFolderButton().setOnAction(e -> addNewFolder(folderSelectionHandler, deleteHandler));
    }

    private EventHandler<MouseEvent> createDeleteHandler(EventHandler<MouseEvent> folderSelectionHandler) {
        return event -> {
            System.out.println("Delete button clicked."); // Debug log
            Button deleteButton = (Button) event.getSource();
            VBox folderContainer = (VBox) deleteButton.getParent();
            Button folderButton = (Button) folderContainer.getChildren().get(0);
            String folderName = folderButton.getText();
            System.out.println("Attempting to delete folder: " + folderName); // Debug log
            deleteFolder(folderName, folderSelectionHandler);
        };
    }

    private void deleteFolder(String folderName, EventHandler<MouseEvent> folderSelectionHandler) {
        System.out.println("Deleting folder: " + folderName);


        // Remove the folder from the model
        foldersModel.removeFolder(folderName);

        //NotesStorage.SaveNotes(foldersModel.getNotebook(folderName));

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

    public NotebookScreenView getNoteBookView() {
        return this.notebookScreenView;
    }

    public void addFoldersXp(double xp){
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
        // Get the notebook associated with the selected folder
        // Get the notebook associated with the selected folder
        Notebook notebook = foldersModel.getNotebook(folderName);

        if (notebook != null) {
            // Reload the notebook from storage
            Notebook finalNotebook = NotesStorage.LoadNotes(folderName);

            if (finalNotebook != null) {
                NotebookScreenView notebookView = new NotebookScreenView(finalNotebook);
                NotebookController notebookController = new NotebookController(finalNotebook, notebookView);

                // Save changes to the notebook when navigating back
                notebookView.getBackButton().setOnAction(e -> {
                    saveNotebookState(finalNotebook); // Use the effectively final variable
                    primaryStage.setScene(foldersScene); // Reuse the existing scene
                });



                notebookView.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
                Scene notebookScene = new Scene(notebookView);
                primaryStage.setScene(notebookScene);
            } else {
                System.err.println("Failed to load notebook for folder: " + folderName);
            }
        } else {
            System.err.println("No notebook found for folder: " + folderName);
        }
    }

    private void saveNotebookState(Notebook notebook) {
        // Save changes to the notebook into the folders model
        NotesStorage.SaveNotes(notebook);
        System.out.println("Notebook state saved for: " + notebook.getTitle());
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
}




