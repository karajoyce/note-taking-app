package com.example.demo.controller;

import com.example.demo.view.FoldersScreenView;
import com.example.demo.view.MainMenuScreenView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NavigationController {

    private Stage primaryStage;
    private Scene mainMenuScene;
    private Scene foldersScene;

    public NavigationController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // Set the main menu scene
    public void setMainMenuScene(Scene mainMenuScene) {
        this.mainMenuScene = mainMenuScene;
    }

    // Set the folders scene
    public void setFoldersScene(Scene foldersScene) {
        this.foldersScene = foldersScene;
    }

    // Navigate to the main menu
    public void navigateToMainMenu(MainMenuScreenView mainMenuScreenView) {
        if (mainMenuScene != null) {
            mainMenuScreenView.runMainScreenUpdate();
            primaryStage.setScene(mainMenuScene);
        } else {
            System.err.println("MainMenuScene is not set!");
        }
    }

    // Navigate to the folders screen
    public void navigateToFoldersScreen(FoldersScreenView foldersScreenView) {
        if (foldersScene != null) {
            foldersScreenView.runFoldersScreenUpdate();
            primaryStage.setScene(foldersScene);
        } else {
            System.err.println("FoldersScene is not set!");
        }
    }
}