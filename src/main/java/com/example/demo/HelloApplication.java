package com.example.demo;

import flashcard.CardView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage){
        Scene scene = new Scene(new CardView());
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Flashcard");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}