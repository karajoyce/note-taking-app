package com.example.demo.controller;

import com.example.demo.model.FoldersModel;
import com.example.demo.view.FoldersScreenView;
import com.example.demo.view.NotebookScreenView;
import com.example.demo.view.MainMenuScreenView;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;

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

    private void openNotebook(String folderName) {
        // Open the notebook for the selected folder
        //notebookScreenView.setCurrentFolder(folderName);
        primaryStage.setScene(new Scene(notebookScreenView));
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
