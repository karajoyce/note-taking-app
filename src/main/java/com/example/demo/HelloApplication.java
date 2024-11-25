package com.example.demo;
import com.example.demo.controller.NotebookController;
import com.example.demo.model.Notebook;
import com.example.demo.view.*;
import com.example.demo.view.MainMenuScreenView;
import com.example.demo.controller.FoldersController;
import com.example.demo.controller.NavigationController;
import com.example.demo.controller.NotebookController;
import com.example.demo.model.FoldersModel;
import com.example.demo.model.Notebook;
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
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import com.example.demo.model.FlashcardScreen;

public class HelloApplication extends Application {

    private static Stage primaryStage;
    @Override
    public void start(Stage stage){

        primaryStage = stage;

        NavigationController navigationController = new NavigationController(primaryStage);

        // Create and set up the Flashcard Screen
        FlashcardScreen fCard = new FlashcardScreen();
        // Deck initialization, needs to change
        // get from file system here, and upload
        fCard.addCard("How much wood could a wood chuck chuck if a wood chuck could chuck wood. Would the wood chuck chuck the wood or would he choose to chuck not the wood?", "A wood chuck could chuck all the wood if a wood chuck could chuck wood.");
        fCard.addCard("What does HTML stand for?", "Hyper Text Markup Language");

        FlashcardScreenView fCardView = new FlashcardScreenView();
        FlashcardScreenController fCardCont = new FlashcardScreenController(fCard, fCardView);

        NotebookScreenView nView = new NotebookScreenView();
        Notebook nModel = new Notebook("CMPT281");
        NotebookController notebookController = new NotebookController(nModel, nView);

        FoldersModel foldersModel = new FoldersModel();
        FoldersScreenView foldersScreenView = new FoldersScreenView();
        FoldersController foldersController = new FoldersController(foldersModel, foldersScreenView, primaryStage, nView);

        MainMenuScreenView mView = new MainMenuScreenView();
        TopViewBar topViewBar = new TopViewBar();

        MotivationalMessagesView motView = new MotivationalMessagesView();

        Scene scene = new Scene(mView);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Flashcard");
        // Create Views
        MainMenuScreenView mainMenuScreenView = new MainMenuScreenView();


        // Create Scenes
        Scene mainMenuScene = new Scene(mainMenuScreenView);
        Scene foldersScene = new Scene(foldersScreenView);


        // Set required references in MainMenuScreenView
        navigationController.setMainMenuScene(mainMenuScene);
        navigationController.setFoldersScene(foldersScene);

        // Set Up Navigation in Views
        mainMenuScreenView.getFoldersButton().setOnAction(event -> navigationController.navigateToFoldersScreen());
        foldersScreenView.getBackButton().setOnAction(event -> navigationController.navigateToMainMenu());



        mainMenuScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setScene(mainMenuScene);
        primaryStage.setTitle("Flashcard");
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
