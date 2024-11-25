package com.example.demo.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class TopViewBar extends HBox {

    private HBox topBar = new HBox();
    private Button notesButton;
    private Button flashButton;
    private Button breakButton;
    private Button settingButton;
    private Button foldersButton; // Add folders button

    private Label titleLabel;


    public TopViewBar() {
        topBar = new HBox();
        topBar.getStyleClass().add("top-bar");
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.TOP_LEFT);
        topBar.setSpacing(20);


        notesButton = new Button("Notes");
        notesButton.getStyleClass().add("note-button");

        flashButton = new Button("Flash");
        flashButton.getStyleClass().add("flash-button");

        breakButton = new Button("Break");
        breakButton.getStyleClass().add("break-button");

        settingButton = new Button("Setting");
        settingButton.getStyleClass().add("setting-button");

        titleLabel = new Label("TruNotes");
        titleLabel.getStyleClass().add("main-title-label");

        foldersButton = new Button("Folders"); // Initialize folders button
        foldersButton.getStyleClass().add("folders-button");

        topBar.getChildren().addAll(titleLabel, notesButton, flashButton, breakButton, settingButton, foldersButton);

    }
    public Button getNotesButton() {
        return notesButton;
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



