package com.example.demo.controller;

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
    public void navigateToMainMenu() {
        if (mainMenuScene != null) {
            primaryStage.setScene(mainMenuScene);
        } else {
            System.err.println("MainMenuScene is not set!");
        }
    }

    // Navigate to the folders screen
    public void navigateToFoldersScreen() {
        if (foldersScene != null) {
            primaryStage.setScene(foldersScene);
        } else {
            System.err.println("FoldersScene is not set!");
        }
    }
}