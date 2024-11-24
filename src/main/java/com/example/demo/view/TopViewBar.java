package com.example.demo.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class TopViewBar extends StackPane {

    private Button notesButton;
    private Button flashButton;
    private Button breakButton;
    private Button settingButton;

    private Label titleLabel;



    public TopViewBar() {
        this.getStyleClass().add("top-bar");
        this.setAlignment(Pos.TOP_CENTER);


        this.setMinHeight(150);
        this.setMaxHeight(150);
        this.setMinWidth(400);
        this.setMaxWidth(400);

        notesButton = new Button("Notes");
        notesButton.getStyleClass().add("note-button");

        flashButton = new Button("Flash");
        flashButton.getStyleClass().add("flash-button");

        breakButton = new Button("Break");
        breakButton.getStyleClass().add("break-button");

        settingButton = new Button("Setting");
        settingButton.getStyleClass().add("setting-button");

        titleLabel = new Label("Title");

        this.getChildren().addAll(notesButton, flashButton, breakButton, settingButton, titleLabel);

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
    public Label getTitleLabel() {
        return titleLabel;
    }


}
