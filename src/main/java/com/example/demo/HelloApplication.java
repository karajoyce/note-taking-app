package com.example.demo;
import com.example.demo.controller.*;
import com.example.demo.model.*;
import com.example.demo.view.*;
import com.example.demo.view.MainMenuScreenView;
import javafx.application.Platform;

/*

 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Author: Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
-
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private static Stage primaryStage;
    private BreakReminderController breakReminderController;
    @Override
    public void start(Stage stage){

        primaryStage = stage;

        NavigationController navigationController = new NavigationController(primaryStage);

        ToDoList toDoList = new ToDoList();
        ToDoListView toDoListView = new ToDoListView(toDoList);
        ToDoListController toDoListController = new ToDoListController(toDoList, toDoListView);

        // Break Reminder setup
        long defaultInterval = 10 * 1000L; //15 * 60 * 1000L;  Default 15 minutes in milliseconds
        BreakReminderModel breakReminderModel = new BreakReminderModel(defaultInterval);
        BreakReminderView breakReminderView = new BreakReminderView();
        this.breakReminderController = new BreakReminderController(breakReminderModel, breakReminderView);
        breakReminderController.startReminders();

        MainMenuScreenView mainMenuScreenView = new MainMenuScreenView();

        TopViewBar topViewBar = mainMenuScreenView.getTopViewBar();

        // Create and set up the Flashcard Screen
        FlashcardScreen fCard = new FlashcardScreen();
        // Deck initialization, needs to change
        // get from file system here, and upload
        fCard.addCard("How much wood could a wood chuck chuck if a wood chuck could chuck wood. Would the wood chuck chuck the wood or would he choose to chuck not the wood?", "A wood chuck could chuck all the wood if a wood chuck could chuck wood.");
        fCard.addCard("What does HTML stand for?", "Hyper Text Markup Language");

        FlashcardScreenView fCardView = new FlashcardScreenView();
        FlashcardScreenController fCardCont = new FlashcardScreenController(fCard, fCardView);
        //fCardView.setToDoList(toDoListView);

        Notebook nModel = new Notebook("CMPT281");
        nModel.addPage(new Page("Lecture 1"));
        NotebookScreenView nView = new NotebookScreenView(nModel);
        NotebookController notebookController = new NotebookController(nModel, nView);
        nView.runScreenUpdate();

        FoldersModel foldersModel = new FoldersModel();
        FoldersScreenView foldersScreenView = new FoldersScreenView();



        Scene foldersScene = new Scene(foldersScreenView);
        FoldersController foldersController = new FoldersController(foldersModel, foldersScreenView, primaryStage, nView, navigationController, foldersScene, toDoListView);






        // Create Views


        // Create Scenes
        Scene mainMenuScene = new Scene(mainMenuScreenView);

        Scene flashcardScene = new Scene(fCardView);
        flashcardScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        Scene notebookScene = new Scene(new NotebookScreenView(nModel));
        MainMenuScreenViewController mainMenuScreenViewController = new MainMenuScreenViewController(mainMenuScreenView, topViewBar, primaryStage, breakReminderController, flashcardScene, notebookScene, mainMenuScene, foldersScreenView, toDoListView);





        // Set required references in MainMenuScreenView
        navigationController.setMainMenuScene(mainMenuScene);
        navigationController.setFoldersScene(foldersScene);

        // Set Up Navigation in Views
        mainMenuScreenView.getFoldersButton().setOnAction(event -> navigationController.navigateToFoldersScreen(foldersScreenView));
        foldersScreenView.getBackButton().setOnAction(event -> navigationController.navigateToMainMenu(mainMenuScreenView));
        nView.getBackButton().setOnAction(event -> navigationController.navigateToFoldersScreen(foldersScreenView));




        mainMenuScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setScene(mainMenuScene);
        primaryStage.setTitle("Main Menu");
        // Wrap full-screen mode changes inside Platform.runLater
        Platform.runLater(() -> {
            primaryStage.setFullScreen(true);  // or false to exit full-screen
        });
        primaryStage.show();
    }

    public static Stage getStage(){
        return primaryStage;
    }

    public static void main(String[] args) {
        launch();
    }
}

