package com.example.demo.controller;

import com.example.demo.FilerSystem.ToDoStorage;
import com.example.demo.model.*;
import com.example.demo.view.*;
/*CHANGES MADE BY NATHAN, CHANGING CODE BELOW TO FIX WARNING*/
//REMOVING UNUSED IMPORTS
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenuScreenViewController {

    /*CHANGES MADE BY NATHAN, CHANGING CODE BELOW TO FIX WARNING*/
    //THESE BECAME FINAL NOTE: STILL SOME WARNINGS BUT DRK HOW TO FIX
    private final MainMenuScreenView view;
    private final TopViewBar topViewBar;
    private final BreakReminderController breakReminderController;
    private final Stage primaryStage;
    private final Scene flashcardScene;
    private final Scene mainMenuScene;
    private final FoldersScreenView foldersScreenView;
    private final XPModel xpModel;
    private final ToDoListView todoV;
    private final ToDoListController todoC;
    private final FoldersController fCont;
    private final FlashcardScreenView fView;
    private final NavigationController navigationController;
    private final FlashcardScreenView flashcardScreenView;
    private final FoldersModel foldersModel;


    public MainMenuScreenViewController(ToDoListView todoV, MainMenuScreenView view, TopViewBar topViewBar, Stage stage, BreakReminderController breakReminderController, Scene flashcardScene, Scene mainMenuScene, FoldersScreenView foldersScreenView, ToDoListController todoC, FoldersController fController, FlashcardScreenView flashcardScreenView, NavigationController navCont, FoldersModel foldersModel) {

        this.foldersModel = foldersModel;
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
        this.fView = flashcardScreenView;
        this.navigationController = navCont;

        this.flashcardScreenView = flashcardScreenView;


        // Set up button actions
        setupButtonActions();
        runUpdate();
    }

    private void setupButtonActions() {
        /*FROM NATHAN WEIRD ERROR THAT DRK HOW TO FIX*/
        //THIS EVENT SHIT AGAIN
        topViewBar.getBreakButton().setOnAction(event -> openIntervalSettingWindow());
        topViewBar.getFlashButton().setOnAction(event -> {

            todoC.updateTaskListView();
            ToDoStorage.LoadToDoList();
            flashcardScreenView.runDeckUpdate();
            primaryStage.setScene(flashcardScene);

        });
        //topViewBar.getSettingButton().setOnAction(event -> primaryStage.setScene(mainMenuScene));
        topViewBar.getFlashButton().setOnAction(event -> navigationController.navigateToFlashcardScreen((fView)));
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
