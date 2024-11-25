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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import org.fxmisc.richtext.InlineCssTextArea;

import java.awt.*;
import java.util.List;

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

    private String currentFolder;

    private XPModel xpModel;
    private XPView xpView;
    private XPController xpController;
    private Button xpToggleButton;
    private boolean isTrackingXP = false;

    //Digital Tree
    DigitalTree digitalTree;

    private ToDoListView toDoListV;
    private ToDoListController toDoCont;
    private ToDoList toDoList;
    private Button pageButton; // button to choose a deck
    private Button addPage; // to add a new page to the notebook
    NoteModel noteModel;
    NoteController noteController;
    NoteView noteView;
    private javafx.event.EventHandler<javafx.event.ActionEvent> pageHandler;

    public NotebookScreenView() {

        //-------------------------
        // Screen Initialization
        screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight()-100;
        screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth()-100;
        pageButton = new Button("Test Page"); // this should be a deck name later
        addPage = new Button("+");

        //Initializing XP bar and system;
        xpModel = new XPModel(100);
        xpView = new XPView();
        //Initializing new Tree
        digitalTree = new DigitalTree();
        xpController = new XPController(xpModel, xpView, digitalTree);

        toDoListV = new ToDoListView();
        toDoList = new ToDoList();
        toDoCont = new ToDoListController(toDoList, toDoListV);

        /* Initialize (MVC) */
        noteModel = new NoteModel();
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
        addPage.setAlignment(Pos.CENTER);
        addPage.setMinWidth(50);
        addPage.setMinHeight(50);
        HBox topLine = new HBox(addPage);
        topLine.setAlignment(Pos.TOP_RIGHT);
        sidePanel.getChildren().add(topLine);
        sidePanel.getChildren().add(deckSelection);
        fullBox.getChildren().add(sidePanel);

        // Buttons for pages
        populatePages(deckSelection);
        //-------------------------END

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
    }

    /**
     * Function that grabs the list of decks from the JSON to make the buttons on the deck selection pane
     * @param pageBox: the box to draw the buttons in
     */
    public void populatePages(ListView<Button> pageBox){
        // Get names from the JSON
        List<String> titles = NotesStorage.GeneratePageTitles();
        for (String title: titles){
            Button tButton = new Button(title);
            tButton.setAlignment(Pos.CENTER);
            tButton.setMinWidth(pageBox.getMinWidth()-70);
            tButton.setMinHeight(160);
            pageBox.getItems().add(tButton);
            tButton.setOnAction(pageHandler);
        }
    }

    public void setAddPageButton(javafx.event.EventHandler<javafx.event.ActionEvent> handler){
        pageHandler = handler;
    }
    /**
     * An event handler for the confident button
     */
    public void setAddPage(javafx.event.EventHandler<javafx.event.ActionEvent> handler){
        addPage.setOnAction(handler);
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
}



