package com.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.FlashcardScreenView;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage){
        Scene scene = new Scene(new FlashcardScreenView());
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