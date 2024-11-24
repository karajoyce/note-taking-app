package com.example.demo.controller;

import com.example.demo.view.FlashcardScreenView;
import com.example.demo.view.MainMenuScreenView;
import com.example.demo.view.NotebookScreenView;
import com.example.demo.view.TopViewBar;
import javafx.stage.Stage;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainMenuScreenViewController {

    private MainMenuScreenView view;
    private TopViewBar topViewBar;


    public MainMenuScreenViewController(MainMenuScreenView view, TopViewBar topViewBar, Stage stage) {

        this.view = view;
        this.topViewBar = topViewBar;

        topViewBar.getBreakButton().setOnAction(event -> {

           stage.getScene().setRoot(new MainMenuScreenView());

        });

        topViewBar.getFlashButton().setOnAction(event -> {

           stage.getScene().setRoot(new FlashcardScreenView());

        });

        topViewBar.getNotesButton().setOnAction(event -> {

            stage.getScene().setRoot(new NotebookScreenView());
        });

        topViewBar.getSettingButton().setOnAction(event -> {

            stage.getScene().setRoot( new MainMenuScreenView());

        });






    }

}
