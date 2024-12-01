package com.example.demo.view;

import com.example.demo.FilerSystem.ToDoStorage;
import com.example.demo.FilerSystem.XPStorage;
import com.example.demo.HelloApplication;
import com.example.demo.controller.FoldersController;
import com.example.demo.controller.NavigationController;
import com.example.demo.controller.ToDoListController;
import com.example.demo.controller.XPController;
import com.example.demo.model.*;
import com.example.demo.model.XPModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.fxmisc.richtext.InlineCssTextArea;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainMenuScreenView extends StackPane {
    double screenHeight;
    double screenWidth;

    private ToDoListView toDoListV;
    private ToDoListController toDoCont;
    private Button pageButton; // button to choose a deck
    private MotivationalMessagesView mView;
    private TopViewBar topViewBar;

    private Button foldersButton; // New button

    private HBox NoteBox;
    private Button newNoteButton;
    private Button recentNoteButton;
    private Button recentNoteButton2;

    private Stage primaryStage; // Reference to the primary stage
    private FoldersScreenView foldersScreenView; // Reference to FoldersScreenView

    //Adding XP Bar and System
    private XPModel xpModel;
    private XPView xpView;
    private XPController xpController;
    private Button xpToggleButton;
    private DigitalTree digitalTree;
    private boolean isTrackingXP = false;

    private FoldersController foldersController;
    private final int SPACING = 100;


    public MainMenuScreenView(FoldersController foldersController) {

        this.foldersController = foldersController;
        // Deck initialization, needs to change
        screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight() - SPACING;
        screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth() - SPACING;
        pageButton = new Button("Test Page"); // this should be a deck name later

        newNoteButton = new Button("+");
        recentNoteButton = new Button("Recent");
        recentNoteButton2 = new Button("Recent");

        topViewBar = new TopViewBar();

        mView = new MotivationalMessagesView();
        ToDoList toDoList = ToDoStorage.LoadToDoList();
        this.toDoListV = new ToDoListView(toDoList);
        toDoListV.setTaskList(ToDoStorage.LoadToDoList(), xpModel);
        toDoCont = new ToDoListController(toDoList, toDoListV, xpModel);

        NoteBox = new HBox();

        //Initializing XP bar and system;
        xpModel = XPManager.getXPModel();
        xpView = new XPView();
        xpController = new XPController(xpModel,xpView, digitalTree);

        runMainScreenUpdate();
    }

    public TopViewBar getTopViewBar() {
        return this.topViewBar;
    }

    public Button getFoldersButton() {
        return topViewBar.getFoldersButton();
    }

    public Button getNewNoteButton() {
        return newNoteButton;
    }
    public Button getRecentNoteButton() {
        return recentNoteButton;
    }
    public Button getRecentNoteButton2() {
        return recentNoteButton2;
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setFoldersScreenView(FoldersScreenView foldersScreenView) {
        this.foldersScreenView = foldersScreenView;
    }

    public FoldersScreenView getFoldersScreenView() {
        return foldersScreenView;
    }

    public void runMainScreenUpdate() {
        // General class things/size
        this.getStylesheets().add("/styles.css");
        double screenHeight = Screen.getPrimary().getBounds().getMaxY() - SPACING;
        double screenWidth = Screen.getPrimary().getBounds().getMaxX() - SPACING;
        this.getChildren().clear();
        //-------------------------END

        //-------------------------
        // Main box to hold elements
        HBox fullBox = new HBox();
        fullBox.getStyleClass().add("bigbox");
        fullBox.setMaxWidth(screenWidth);
        fullBox.setMaxHeight(screenHeight);

        //-------------------------
        // Set up flashcard middle section
        VBox cardSection = new VBox();
        cardSection.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        cardSection.getStyleClass().add("cardsection");
        cardSection.setAlignment(Pos.CENTER_LEFT);
        cardSection.setMinWidth((screenWidth * 0.7) - 10);
        cardSection.setMinHeight(screenHeight);
        fullBox.getChildren().add(cardSection);



        //VBox topViewBar = new VBox();
        topViewBar.setAlignment(Pos.TOP_LEFT);
        topViewBar.getStyleClass().add("topViewBar");
        cardSection.getChildren().add(topViewBar);
        VBox topViewBarBox = new VBox();
        topViewBarBox.getChildren().add(topViewBar.getTopViewBar());
        cardSection.getChildren().add(topViewBarBox);
        topViewBarBox.getStyleClass().add("topViewBar");
        topViewBarBox.setAlignment(Pos.TOP_LEFT);
        topViewBarBox.setSpacing(50);
        topViewBarBox.setPadding(new Insets(-17.5,0,70,0));
        //-------------------------END

        //Adding the notebooks
        NoteBox = new HBox();
        NoteBox.getStyleClass().add("note-box");

        NoteBox.setAlignment(Pos.CENTER);
        NoteBox.setSpacing(35);

        newNoteButton.setMinHeight(375);
        newNoteButton.setMinWidth(275);
        newNoteButton.getStyleClass().add("bignotebox");

        recentNoteButton.setMinHeight(375);
        recentNoteButton.setMinWidth(275);
        recentNoteButton.getStyleClass().add("bignotebox");

        recentNoteButton2.setMinHeight(375);
        recentNoteButton2.setMinWidth(275);
        recentNoteButton2.getStyleClass().add("bignotebox");

        // Update recent folders
        FoldersModel foldersModel = foldersController.getFoldersModel();
        ArrayList<String> recent = foldersModel.getMostRecentFolders();

        if(recent.size() > 0) {
            String title1 = (recent.get(0));
            this.getRecentNoteButton().setText(title1);
            this.getRecentNoteButton().setOnAction(e -> foldersController.openNotebook(title1));

        }else{
            this.getRecentNoteButton().setText("Recent");

        }
        if(recent.size() > 1) {
            String title2 = (recent.get(1));
            this.getRecentNoteButton2().setText(title2);
            this.getRecentNoteButton2().setOnAction(e -> foldersController.openNotebook(title2) );
        }else{
            this.getRecentNoteButton2().setText("Recent");

        }



        NoteBox.setPadding(new Insets(50,0,0,0));
        NoteBox.getChildren().addAll(newNoteButton, recentNoteButton, recentNoteButton2);
        cardSection.getChildren().add(NoteBox);

        xpView.setPadding(new Insets(20,0,-30,0));
        cardSection.getChildren().add(xpView);

        //Card text setup
        HBox fCard = new HBox();
        fCard.setMinHeight(cardSection.getMinHeight() * (6.0 / 8.0));
        fCard.getStyleClass().add("textflow");
        fCard.setAlignment(Pos.CENTER);

        // Back button
        HBox topButtons = new HBox();
        topButtons.setMinHeight(cardSection.getMinHeight() * (1.0 / 8.0));
        topButtons.getStyleClass().add("hbox");
        cardSection.getChildren().add(topButtons);


        //-------------------------
        VBox todolist = new VBox();
        todolist.setMinWidth(screenWidth * 0.3);
        toDoListV.setMinHeight(screenHeight * 0.6);
        todolist.setAlignment(Pos.TOP_CENTER);
        todolist.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        todolist.getStyleClass().add("rightVbox");

        toDoListV.setTaskList(ToDoStorage.LoadToDoList(), this.xpModel);
        todolist.setSpacing(30);
        todolist.getChildren().addAll(mView.getMotivmsgView(), toDoListV.getToDoListView());
        toDoListV.setTaskList(ToDoStorage.LoadToDoList(), xpModel);

        fullBox.getChildren().add(todolist);
        this.getChildren().add(fullBox);
    }
}



