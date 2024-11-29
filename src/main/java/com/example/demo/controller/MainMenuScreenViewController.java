package com.example.demo.controller;

import com.example.demo.FilerSystem.NotesStorage;
import com.example.demo.FilerSystem.ToDoStorage;
import com.example.demo.model.*;
import com.example.demo.view.*;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.List;

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
    private FoldersController fCont;
    private FlashcardScreenView flashcardScreenView;
    private NavigationController navigationController;


    public MainMenuScreenViewController(ToDoListView todoV, MainMenuScreenView view, TopViewBar topViewBar, Stage stage, BreakReminderController breakReminderController, Scene flashcardScene, Scene mainMenuScene, FoldersScreenView foldersScreenView, ToDoListController todoC, FoldersController fController, FlashcardScreenView flashcardScreenView, NavigationController navigationController) {

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
        this.fCont = fController;
        this.flashcardScreenView = flashcardScreenView;
        this.navigationController = navigationController;


        // Set up button actions
        setupButtonActions();
        runUpdate();
    }

    private void setupButtonActions() {
        topViewBar.getBreakButton().setOnAction(event -> openIntervalSettingWindow());
        /*
        topViewBar.getFlashButton().setOnAction(event -> {

            todoC.updateTaskListView();
            ToDoStorage.LoadToDoList();
            flashcardScreenView.updateToDoListView();
            primaryStage.setScene(flashcardScreenView.getScene());

        });

         */

        topViewBar.getFlashButton().setOnAction(event -> navigationController.navigateToFlashCardScreen(flashcardScreenView));

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
        view.getNewNoteButton().setOnAction(event -> {
            fCont.addNewFolder(fCont.getFolderSelectionHandler(), fCont.getDeleteHandler());
            if (!foldersScreenView.getAddButtonText().isEmpty() || fCont.getNewFolderName() != null) {
                if (fCont.getNewFolderName() != null) {
                    System.out.println("Folder name is : " + fCont.getNewFolderName());
                    fCont.openNotebook(foldersScreenView.getAddButtonText());
                }
            }
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
