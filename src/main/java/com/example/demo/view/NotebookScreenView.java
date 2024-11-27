package com.example.demo.view;

import com.example.demo.FilerSystem.FlashcardStorage;
import com.example.demo.FilerSystem.NotesStorage;
import com.example.demo.HelloApplication;
import com.example.demo.controller.ToDoListController;
import com.example.demo.controller.XPController;
import com.example.demo.model.*;
import com.example.demo.notes.NoteController;
import com.example.demo.notes.NoteModel;
import com.example.demo.notes.NoteView;
import com.google.gson.annotations.SerializedName;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import org.fxmisc.richtext.InlineCssTextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;

/**

 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Author: Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521

 **/

public class NotebookScreenView extends StackPane {

    // Deck initialization, needs to change
    double screenHeight;
    double screenWidth;

    private XPModel xpModel;
    private XPView xpView;
    private XPController xpController;
    private Button xpToggleButton;
    private boolean isTrackingXP = false;

    //Digital Tree
    private DigitalTree digitalTree;

    private ToDoListView toDoListV;
    private ToDoListController toDoCont;
    private ToDoList toDoList;
    private Button addPage; // to add a new page to the notebook
    private Button pageBack;
    private Button removePage;
    private Button renamePage;
    private NoteModel noteModel;
    private NoteController noteController;
    private NoteView noteView;
    private Notebook currentNotebook;
    private Page currentPage;
    private javafx.event.EventHandler<javafx.event.ActionEvent> pageHandler;

    public NotebookScreenView(Notebook currNotebook) {

        //-------------------------
        // Screen Initialization
        screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight()-100;
        screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth()-100;

        renamePage = new Button("");
        Image imgR = new Image(getClass().getResourceAsStream("/rename.png"));
        ImageView imgViewR = new ImageView(imgR);
        imgViewR.setFitHeight(20);
        imgViewR.setPreserveRatio(true);
        renamePage.setGraphic(imgViewR);

        addPage = new Button("");
        Image imgA = new Image(getClass().getResourceAsStream("/plus.png"));
        ImageView imgViewA = new ImageView(imgA);
        imgViewA.setFitHeight(15);
        imgViewA.setPreserveRatio(true);
        addPage.setGraphic(imgViewA);

        pageBack = new Button("");
        Image imgB = new Image(getClass().getResourceAsStream("/backArrow.png"));
        ImageView imgViewB = new ImageView(imgB);
        imgViewB.setFitHeight(15);
        imgViewB.setPreserveRatio(true);
        pageBack.setGraphic(imgViewB);

        removePage = new Button();
        Image img = new Image(getClass().getResourceAsStream("/trashcan.png"));
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(30);
        imgView.setPreserveRatio(true);
        removePage.setGraphic(imgView);

        //Initializing XP bar and system;
        xpModel = new XPModel(100);
        xpView = new XPView();
        //Initializing new Tree
        digitalTree = new DigitalTree();
        xpController = new XPController(xpModel, xpView, digitalTree);

        toDoListV = new ToDoListView();
        toDoList = new ToDoList();
        toDoCont = new ToDoListController(toDoList, toDoListV);

        noteModel = new NoteModel();

        currentNotebook = currNotebook;
        setCurrentPage(currNotebook.getNotes().getFirst());

        /* Initialize (MVC) */
        noteController= new NoteController(noteModel);
        noteView= new NoteView(noteController);

        runScreenUpdate();
    }

    public void runScreenUpdate(){
        // General class things/size
        this.getStyleClass().add("wholescreen");
        double screenHeight = Screen.getPrimary().getBounds().getMaxY()-100;
        double screenWidth = Screen.getPrimary().getBounds().getMaxX()-100;
        this.getChildren().clear();
        //-------------------------END

        //-------------------------
        // Main box to hold elements
        HBox fullBox = new HBox();
        this.getChildren().add(fullBox);
        fullBox.getStyleClass().add("bigbox");
        fullBox.setMaxWidth(screenWidth);
        fullBox.setMaxHeight(screenHeight);
        //-------------------------END

        //-------------------------
        // Deck selection pane
        VBox sidePanel = new VBox();
        ListView<Button> deckSelection = new ListView<>();
        deckSelection.getStyleClass().add("deck");
        deckSelection.setMinWidth(screenWidth*0.15);
        deckSelection.setMinHeight(screenHeight);
        fullBox.getChildren().add(deckSelection);

        // Button for adding a new page
        pageBack.setMinHeight(50);
        pageBack.getStyleClass().add("back-button");

        removePage.setMinHeight(50);
        removePage.getStyleClass().add("back-button");

        renamePage.setMinHeight(50);
        renamePage.getStyleClass().add("back-button");

        addPage.setAlignment(Pos.CENTER);
        addPage.setMinWidth(50);
        addPage.setMinHeight(50);
        HBox topLine = new HBox(pageBack, removePage, renamePage, addPage);
        topLine.setSpacing(10);
        topLine.setAlignment(Pos.CENTER);
        sidePanel.getChildren().add(topLine);
        sidePanel.getChildren().add(deckSelection);
        fullBox.getChildren().add(sidePanel);

        //-------------------------
        // Set up flashcard middle section
        VBox cardSection = new VBox();
        cardSection.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        cardSection.getStyleClass().add("cardsection");
        cardSection.setAlignment(Pos.CENTER_LEFT);
        cardSection.setMinWidth((screenWidth*0.6)-10);
        cardSection.setMinHeight(screenHeight);
        fullBox.getChildren().add(cardSection);

        InlineCssTextArea textArea = noteModel.getTextArea();

        javafx.scene.control.MenuBar menuBar = noteView.createMenuBar(HelloApplication.getStage());
        menuBar.getStyleClass().add("menuBar");
        ToolBar toolBar = noteView.createToolBar();

        HBox menuItems = new HBox();
        menuItems.getStyleClass().add("textEditorBar");
        menuItems.setMaxHeight(50);
        menuItems.setMinHeight(50);
        menuItems.setAlignment(Pos.CENTER);
        menuItems.setMaxWidth(cardSection.getMinWidth());

        HBox fileButton = new HBox();
        fileButton.setStyle("-fx-padding: 0");
        menuBar.setMinHeight(50);
        menuBar.setMaxHeight(50);
        fileButton.setMinWidth(55);
        fileButton.setMaxWidth(55);
        fileButton.getChildren().add(menuBar);

        HBox stylesButton = new HBox();
        stylesButton.setMinHeight(50);
        stylesButton.setMaxHeight(50);
        stylesButton.getChildren().add(toolBar);
        toolBar.setMinWidth(cardSection.getMinWidth()-55);
        toolBar.setMinHeight(50);
        toolBar.setMaxHeight(50);
        menuItems.getChildren().add(fileButton);
        menuItems.getChildren().add(toolBar);
        cardSection.getChildren().add(menuItems);
        ScrollPane sPane = new ScrollPane(textArea);
        sPane.setHmax(screenHeight);
        cardSection.getChildren().add(sPane);


        //Card text setup
        HBox fCard = new HBox();
        fCard.setMinHeight(cardSection.getMinHeight()*(6.0/8.0));
        fCard.getStyleClass().add("textflow");
        fCard.setAlignment(Pos.CENTER);


        //-------------------------
        VBox todolist = new VBox();
        todolist.setAlignment(Pos.TOP_CENTER);
        todolist.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        todolist.getStyleClass().add("rightVbox");


        //BUTTON FOR NOW MAYBE CHANGE LATER;
        xpToggleButton = new Button("START XP TRACKING ");
        xpToggleButton.setOnAction(e -> toggleXPtracking());
        xpToggleButton.getStyleClass().add("xpbar");
        xpToggleButton.setMinHeight(50);

        todolist.getChildren().addAll(toDoListV.getToDoListView(), digitalTree.getTreeImageview(), xpView, xpToggleButton);
        fullBox.getChildren().add(todolist);

        // Buttons for pages
        populatePages(deckSelection);
        //-------------------------END
    }

    /**
     * Function that grabs the list of decks from the JSON to make the buttons on the deck selection pane
     * @param pageBox: the box to draw the buttons in
     */
    public void populatePages(ListView<Button> pageBox){
        // Get names from the JSON
        for (Page page: currentNotebook.getNotes()){
            Button tButton = new Button(page.getTitle());
            tButton.setAlignment(Pos.CENTER);
            tButton.setMinWidth(pageBox.getMinWidth()-70);
            tButton.setMaxWidth(pageBox.getMinWidth()-70);
            tButton.setMinHeight(160);
            tButton.wrapTextProperty().setValue(true);
            tButton.setTextAlignment(TextAlignment.CENTER);
            pageBox.getItems().add(tButton);
            tButton.setOnAction(pageHandler);
        }
    }

    public void setChangeButton(javafx.event.EventHandler<javafx.event.ActionEvent> handler){
        pageHandler = handler;
    }
    /**
     * An event handler for the confident button
     */
    public void setAddPage(javafx.event.EventHandler<javafx.event.ActionEvent> handler){
        addPage.setOnAction(handler);
    }
    /**
     * An event handler for the confident button
     */
    public void setDeletePage(javafx.event.EventHandler<javafx.event.ActionEvent> handler){
        removePage.setOnAction(handler);
    }

    //Adding XP tracking when entering this screen
    public void startXPtracking(){
        xpController.startXPTimer();
    }

    public void stopXPtracking(){
        xpController.stopXPTimer();
    }
    //BUTTON FUNCTIONS FOR XP TRACKING FOR NOW
    private void toggleXPtracking() {
        if (isTrackingXP) {
            stopXPtracking();
            xpToggleButton.setText("START XP TRACKING");
        } else {
            startXPtracking();
            xpToggleButton.setText("STOP XP TRACKING");
        }
        isTrackingXP = !isTrackingXP;
    }

    public void setCurrentPage(Page page){
        currentPage = page;
        currentPage.getContents().setPrefHeight(800);
        currentPage.getContents().setPrefWidth(800);
        noteModel.setTextArea(page.getContents());
        noteModel.resetTextAreaStyles(noteController);
        noteModel.getTextArea().textProperty().addListener(((observableValue, s, t1) -> noteController.applyCurrentStyleToNewText()));
    }

    public Page getCurrentPage(){
        return this.currentPage;
    }
}
