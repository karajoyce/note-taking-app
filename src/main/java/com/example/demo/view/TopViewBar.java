package com.example.demo.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TopViewBar extends HBox {

    private HBox topBar = new HBox();
    private Button notesButton;
    private Button flashButton;
    private Button breakButton;
    private Button settingButton;

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

        topBar.getChildren().addAll(titleLabel, notesButton, flashButton, breakButton, settingButton);

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
    public HBox getTopViewBar() {
        return topBar; // Return the HBox
    }


}
