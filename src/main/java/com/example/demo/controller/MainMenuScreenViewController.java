package com.example.demo.controller;

import com.example.demo.FilerSystem.NotesStorage;
import com.example.demo.FilerSystem.ToDoStorage;
import com.example.demo.model.XPManager;
import com.example.demo.model.XPModel;
import com.example.demo.model.Task;
import com.example.demo.model.TaskItem;
import com.example.demo.model.ToDoList;
import com.example.demo.view.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

public class MainMenuScreenViewController {

    private MainMenuScreenView view;
    private TopViewBar topViewBar;
    private BreakReminderController breakReminderController;
    private Stage primaryStage;
    private Scene flashcardScene;
    private Scene mainMenuScene;
    private FoldersScreenView foldersScreenView;
    private XPModel xpModel;
    private ToDoListView todoV;
    private ToDoListController todoC;


    public MainMenuScreenViewController(ToDoListView todoV, MainMenuScreenView view, TopViewBar topViewBar, Stage stage, BreakReminderController breakReminderController, Scene flashcardScene, Scene mainMenuScene, FoldersScreenView foldersScreenView, ToDoListController todoC) {

        this.view = view;
        this.topViewBar = topViewBar;
        this.breakReminderController = breakReminderController;
        this.primaryStage = stage;
        this.flashcardScene = flashcardScene;
        this.mainMenuScene = mainMenuScene;
        this.foldersScreenView = foldersScreenView;
        this.xpModel = XPManager.getXPModel();
        this.todoV = todoV;
        this.todoC = todoC;


        // Set up button actions
        setupButtonActions();
        runUpdate();
    }

    private void setupButtonActions() {
        topViewBar.getBreakButton().setOnAction(event -> openIntervalSettingWindow());
        topViewBar.getFlashButton().setOnAction(event -> {

            todoC.updateTaskListView();
            ToDoStorage.LoadToDoList();
            primaryStage.setScene(flashcardScene);

        });
        topViewBar.getSettingButton().setOnAction(event -> primaryStage.setScene(mainMenuScene));
        topViewBar.getFoldersButton().setOnAction(event -> {
            if (primaryStage == null) {
                System.err.println("PrimaryStage is not set!");
                return;
            }

            // Navigate to the FoldersScreen
            todoC.updateTaskListView();
            ToDoStorage.LoadToDoList();
            foldersScreenView.updateToDoListView();
            primaryStage.setScene(foldersScreenView.getScene());
        });
    }

    // This method opens a new window for setting the break reminder interval
    private void openIntervalSettingWindow() {
        BreakReminderIntervalView intervalView = new BreakReminderIntervalView(breakReminderController);
        intervalView.show();
    }

    public void runUpdate() {
        view.runMainScreenUpdate();
    }
}
