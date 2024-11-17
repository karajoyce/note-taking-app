package com.example.demo;
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
import com.example.demo.view.FlashcardScreenView;
import com.example.demo.view.NotebookScreenView;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage){

        // Create and set up the Flashcard Screen
        FlashcardScreen fCard = new FlashcardScreen();
        // Deck initialization, needs to change
        // get from file system here, and upload
        fCard.addCard("How much wood could a wood chuck chuck if a wood chuck could chuck wood. Would the wood chuck chuck the wood or would he choose to chuck not the wood?", "A wood chuck could chuck all the wood if a wood chuck could chuck wood.");
        fCard.addCard("What does HTML stand for?", "Hyper Text Markup Language");

        FlashcardScreenView fCardView = new FlashcardScreenView();
        FlashcardScreenController fCardCont = new FlashcardScreenController(fCard, fCardView);
        NotebookScreenView nView = new NotebookScreenView();

        Scene scene = new Scene(fCardView);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Flashcard");
        // Wrap full-screen mode changes inside Platform.runLater
        Platform.runLater(() -> {
            stage.setFullScreen(true);  // or false to exit full-screen
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}