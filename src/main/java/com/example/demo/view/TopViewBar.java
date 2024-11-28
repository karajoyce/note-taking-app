package com.example.demo.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.awt.*;

public class TopViewBar extends HBox {

    private HBox topBar = new HBox();
    private Button flashButton;
    private Button breakButton;
    private Button settingButton;
    private Button foldersButton; // Add folders button

    private Label titleLabel;


    public TopViewBar() {

        Image logo = new Image(getClass().getResource("/file.png").toExternalForm());
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

        settingButton = new Button("Setting");
        settingButton.getStyleClass().add("setting-button");

        titleLabel = new Label("TruNotes");
        titleLabel.getStyleClass().add("main-title-label");

        foldersButton = new Button("Folders"); // Initialize folders button
        foldersButton.getStyleClass().add("folders-button");


        topBar.getChildren().addAll(logoView, titleLabel, flashButton, breakButton, settingButton, foldersButton);

    }

    public Button getFlashButton() {
        return flashButton;
    }
    public Button getBreakButton() {
        return breakButton;
    }
    public Button getSettingButton() {
        return settingButton;
    }

    public Button getFoldersButton() {
        return foldersButton;
    }

    public HBox getTopViewBar() {
        return topBar; // Return the HBox
    }


}



