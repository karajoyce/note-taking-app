package com.example.demo;

import controller.FlashcardScreenController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.FlashcardScreen;
import view.FlashcardScreenView;
import view.NotebookScreenView;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage){

        // Create and set up the Flashcard Screen
        FlashcardScreen fCard = new FlashcardScreen();
        FlashcardScreenView fCardView = new FlashcardScreenView();
        FlashcardScreenController fCardCont = new FlashcardScreenController(fCard, fCardView);


        Scene scene = new Scene(fCardView);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Flashcard");
        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}