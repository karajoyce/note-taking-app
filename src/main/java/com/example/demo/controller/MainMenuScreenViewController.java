package com.example.demo.controller;

import com.example.demo.view.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainMenuScreenViewController {

    private MainMenuScreenView view;
    private TopViewBar topViewBar;
    private BreakReminderController breakReminderController;
    private Stage primaryStage;
    private Scene flashcardScene;
    private Scene notebookScene;
    private Scene mainMenuScene;
    private FoldersScreenView foldersScreenView;


    public MainMenuScreenViewController(MainMenuScreenView view, TopViewBar topViewBar, Stage stage, BreakReminderController breakReminderController, Scene flashcardScene, Scene notebookScene, Scene mainMenuScene, FoldersScreenView foldersScreenView) {

        this.view = view;
        this.topViewBar = topViewBar;
        this.breakReminderController = breakReminderController;
        this.primaryStage = stage;
        this.flashcardScene = flashcardScene;
        this.notebookScene = notebookScene;
        this.mainMenuScene = mainMenuScene;
        this.foldersScreenView = foldersScreenView;

        // Set up button actions
        setupButtonActions();
    }

    private void setupButtonActions() {
        topViewBar.getBreakButton().setOnAction(event -> openIntervalSettingWindow());
        topViewBar.getFlashButton().setOnAction(event -> primaryStage.setScene(flashcardScene));
        topViewBar.getSettingButton().setOnAction(event -> primaryStage.setScene(mainMenuScene));
        topViewBar.getFlashButton().setOnAction(event -> {
            if (primaryStage == null) {
                System.err.println("PrimaryStage is not set!");
                return;
            }
            if (view.getFoldersScreenView() == null) {
                // Lazy initialization of FoldersScreenView
                foldersScreenView = new FoldersScreenView();
                foldersScreenView.getBackButton().setOnAction(e -> primaryStage.setScene(new Scene(view))); // Back to MainMenuScreen
            }
            // Navigate to the FoldersScreen
            primaryStage.setScene(new Scene(foldersScreenView));
        });
    }

    // This method opens a new window for setting the break reminder interval
    private void openIntervalSettingWindow() {
        BreakReminderIntervalView intervalView = new BreakReminderIntervalView(breakReminderController);
        intervalView.show();
    }


}

