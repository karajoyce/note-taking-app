package com.example.demo.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.Objects;

public class TopViewBar extends HBox {
    /*CHANGES MADE BY NATHAN, CHANGING CODE BELOW TO FIX WARNING*/
    //CHANGING TO FINAL
    private final HBox topBar;
    private final Button flashButton;
    private final Button breakButton;
    private final Button foldersButton; // Add folders button


    public TopViewBar() {

        Image logo = new Image(Objects.requireNonNull(getClass().getResource("/file.png")).toExternalForm());
        ImageView logoView = new ImageView(logo);
        logoView.getStyleClass().add("image-view");

        logoView.setFitHeight(120);
        logoView.setFitWidth(120);
        logoView.setPreserveRatio(true);
        logoView.setSmooth(true);


        topBar = new HBox();
        topBar.getStyleClass().add("top-bar");
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.TOP_LEFT);
        topBar.setSpacing(10);


        flashButton = new Button("Flashcard");
        flashButton.getStyleClass().add("flash-button");

        breakButton = new Button("Break");
        breakButton.getStyleClass().add("break-button");


        Label titleLabel = new Label("TruNotes");
        titleLabel.getStyleClass().add("main-title-label");

        foldersButton = new Button("Folders"); // Initialize folders button
        foldersButton.getStyleClass().add("folders-button");


        topBar.getChildren().addAll(logoView, titleLabel, flashButton, breakButton, foldersButton);

    }

    public Button getFlashButton() {
        return flashButton;
    }
    public Button getBreakButton() {
        return breakButton;
    }

    public Button getFoldersButton() {
        return foldersButton;
    }

    public HBox getTopViewBar() {
        return topBar; // Return the HBox
    }


}



