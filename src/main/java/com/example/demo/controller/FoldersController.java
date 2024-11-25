package com.example.demo.controller;

import com.example.demo.model.FoldersModel;
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

    public FoldersController(FoldersModel model, FoldersScreenView view, Stage stage, NotebookScreenView notebookView) {
        this.foldersModel = model;
        this.foldersScreenView = view;
        this.primaryStage = stage;
        this.notebookScreenView = notebookView;

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
        if (notebookScreenView.getScene() != null) {
            notebookScreenView.getScene().setRoot(new StackPane()); // Detach it by setting a dummy root
        }
        notebookScreenView.setCurrentFolder(folderName);
        notebookScreenView.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        //primaryStage.setScene(new Scene(notebookScreenView)); // Reattach it to the new scene

        Scene notebookScene = new Scene(notebookScreenView);
        primaryStage.setScene(notebookScene); // Reattach it to the new scene
    }

    private void addNewFolder() {
        // Add a new folder
        String newFolderName = foldersScreenView.showAddFolderDialog();
        if (newFolderName != null && !newFolderName.trim().isEmpty()) {
            foldersModel.addFolder(newFolderName);
            foldersScreenView.populateFolders(foldersModel.getFolders());
        }
    }
}
