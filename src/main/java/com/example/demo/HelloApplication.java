package com.example.demo;
import com.example.demo.FilerSystem.ToDoStorage;
import com.example.demo.FilerSystem.NotesStorage;
import com.example.demo.controller.NotebookController;
import com.example.demo.FilerSystem.XPStorage;
import com.example.demo.controller.*;
import com.example.demo.model.*;
import com.example.demo.model.BreakReminderModel;
import com.example.demo.model.FoldersModel;
import com.example.demo.model.Notebook;
import com.example.demo.model.Page;
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

import com.example.demo.controller.FlashcardScreenController;
import javafx.application.Application;
import javafx.scene.Scene;
import com.example.demo.model.XPModel;
import com.example.demo.model.XPManager;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private static Stage primaryStage;
    private BreakReminderController breakReminderController;
    private XPModel xpModel;
    @Override
    public void start(Stage stage){

        primaryStage = stage;

        NavigationController navigationController = new NavigationController(primaryStage);

        ToDoList toDoList = new ToDoList();
        ToDoListView toDoListView = new ToDoListView();
        ToDoListController toDoListController = new ToDoListController(toDoList, toDoListView);
        toDoListController.updateTaskListView();

        // Break Reminder setup
        long defaultInterval = 10 * 1000L; //15 * 60 * 1000L;  Default 15 minutes in milliseconds
        BreakReminderModel breakReminderModel = new BreakReminderModel(defaultInterval);
        BreakReminderView breakReminderView = new BreakReminderView();
        this.breakReminderController = new BreakReminderController(breakReminderModel, breakReminderView);
        breakReminderController.startReminders();

        // Create and set up the Flashcard Screen
        FlashcardScreen fCard = new FlashcardScreen();
        // Deck initialization, needs to change
        // get from file system here, and upload
        fCard.addCard("How much wood could a wood chuck chuck if a wood chuck could chuck wood. Would the wood chuck chuck the wood or would he choose to chuck not the wood?", "A wood chuck could chuck all the wood if a wood chuck could chuck wood.");
        fCard.addCard("What does HTML stand for?", "Hyper Text Markup Language");

        FlashcardScreenView fCardView = new FlashcardScreenView();

        FlashcardScreenController fCardCont = new FlashcardScreenController(fCard, fCardView);

        fCardView.runDeckUpdate();

        Notebook nModel = new Notebook("CMPT281");
        nModel.addPage(new Page("Lecture 1"));
        NotebookScreenView nView = new NotebookScreenView(nModel);

//        Notebook nModel = new Notebook("CMPT281");
//        nModel.addPage(new Page("Lecture 1"));
        NotebookController notebookController = new NotebookController(nModel, nView);
        nView.runScreenUpdate();

        FoldersModel foldersModel = new FoldersModel();
        FoldersScreenView foldersScreenView = new FoldersScreenView();
        FoldersController foldersController = new FoldersController(foldersModel, foldersScreenView, primaryStage, nView);


        Scene foldersScene = new Scene(foldersScreenView);
        FoldersController foldersController = new FoldersController(foldersModel, foldersScreenView, primaryStage, nView, navigationController, foldersScene, toDoListView);

        foldersController.getNoteBookView();

        // Create Views
        MainMenuScreenView mainMenuScreenView = new MainMenuScreenView();
        TopViewBar topViewBar = mainMenuScreenView.getTopViewBar();


        // Create Scenes
        Scene mainMenuScene = new Scene(mainMenuScreenView);

        Scene flashcardScene = new Scene(fCardView);
        flashcardScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        Scene notebookScene = new Scene(new NotebookScreenView(nModel));
        MainMenuScreenViewController mainMenuScreenViewController = new MainMenuScreenViewController(toDoListView ,mainMenuScreenView, topViewBar, primaryStage, breakReminderController, flashcardScene, notebookScene, mainMenuScene, foldersScreenView, toDoListController);





        // Set required references in MainMenuScreenView
        navigationController.setMainMenuScene(mainMenuScene);
        navigationController.setFoldersScene(foldersScene);

        // Set Up Navigation in Views
        mainMenuScreenView.getFoldersButton().setOnAction(event -> navigationController.navigateToFoldersScreen());
        foldersScreenView.getBackButton().setOnAction(event -> navigationController.navigateToMainMenu());
        nView.getBackButton().setOnAction(even -> navigationController.navigateToFoldersScreen());
        mainMenuScreenView.getNewNoteButton().setOnAction(event -> primaryStage.setScene(notebookScene));
        mainMenuScreenView.getRecentNoteButton().setOnAction(event -> primaryStage.setScene(notebookScene));
        mainMenuScreenView.getRecentNoteButton2().setOnAction(event -> primaryStage.setScene(notebookScene));




        mainMenuScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setScene(mainMenuScene);
        primaryStage.setTitle("Main Menu");
        // Wrap full-screen mode changes inside Platform.runLater
        Platform.runLater(() -> {
            primaryStage.setFullScreen(true);  // or false to exit full-screen
        });

        primaryStage.setOnCloseRequest(e -> {
            NotesStorage.SaveNotes(nModel);
        });

        primaryStage.show();
    }

    public static Stage getStage(){
        return primaryStage;
    }
    @Override
    public void stop() {
        // Save XP on app close
        XPStorage.SaveXPBar(XPManager.getXPModel());
    }

    public static void main(String[] args) {
        launch();
    }
}