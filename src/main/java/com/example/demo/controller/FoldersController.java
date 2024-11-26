package com.example.demo.controller;

import com.example.demo.FilerSystem.NotesStorage;
import com.example.demo.model.FoldersModel;
import com.example.demo.model.Notebook;
import com.example.demo.model.Page;
import com.example.demo.view.FoldersScreenView;
import com.example.demo.view.NotebookScreenView;
import com.example.demo.view.MainMenuScreenView;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

public class FoldersController {
    private FoldersModel foldersModel;
    private FoldersScreenView foldersScreenView;
    private Stage primaryStage;
    private NotebookScreenView notebookScreenView;
    private NavigationController navigationController;
    private Scene foldersScene;

    public FoldersController(FoldersModel model, FoldersScreenView view, Stage stage, NotebookScreenView notebookView, NavigationController navigationController, Scene foldersScene) {
        this.foldersModel = model;
        this.foldersScreenView = view;
        this.primaryStage = stage;
        this.notebookScreenView = notebookView;
        this.navigationController = navigationController;
        this.foldersScene = foldersScene;


        // Populate folders on the screen
        foldersScreenView.populateFolders(foldersModel.getFolders());

        // Add event handlers
        foldersScreenView.getBackButton().setOnAction(e -> goToMainMenu());
        foldersScreenView.setFolderSelectionHandler(event -> {
            Button selectedButton = (Button) event.getSource();
            String folderName = selectedButton.getText();
            openNotebook(folderName);
        });
        foldersScreenView.getAddFolderButton().setOnAction(e -> addNewFolder());
    }

    private void goToMainMenu() {
        // Navigate back to the main menu
        primaryStage.setScene(new Scene(new MainMenuScreenView()));
    }

    public NotebookScreenView getNoteBookView() {
        return this.notebookScreenView;
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

    private void addNewFolder() {
        // Add a new folder
        String newFolderName = foldersScreenView.showAddFolderDialog();
        if (newFolderName != null && !newFolderName.trim().isEmpty()) {
            foldersModel.addFolder(newFolderName);

            // Optionally add a default page to the new notebook
            Notebook newNotebook = foldersModel.getNotebook(newFolderName);
            if (newNotebook.getNotes().isEmpty()) {
                newNotebook.addPage(new Page("Default Page"));
            }

            NotesStorage.SaveNotes(newNotebook); // Save the newly created notebook

            foldersScreenView.populateFolders(foldersModel.getFolders());
        }
    }
}




