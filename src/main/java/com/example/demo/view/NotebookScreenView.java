package com.example.demo.view;

import com.example.demo.HelloApplication;
import com.example.demo.controller.ToDoListController;
import com.example.demo.controller.XPController;
import com.example.demo.model.DigitalTree;
import com.example.demo.model.ToDoList;
import com.example.demo.model.XPModel;
import com.example.demo.notes.NoteController;
import com.example.demo.notes.NoteModel;
import com.example.demo.notes.NoteView;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import org.fxmisc.richtext.InlineCssTextArea;

import java.awt.*;
import java.util.Set;

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
    DigitalTree digitalTree;

    private ToDoListView toDoListV;
    private ToDoListController toDoCont;
    private ToDoList toDoList;
    private Button pageButton; // button to choose a deck
    NoteModel noteModel;
    NoteController noteController;
    NoteView noteView;

    public NotebookScreenView() {

        //-------------------------
        // Deck initialization, needs to change
        screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight()-100;
        screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth()-100;
        pageButton = new Button("Test Page"); // this should be a deck name later

        //Initializing XP bar and system;
        xpModel = new XPModel(100);
        xpView = new XPView();
        //Initializing new Tree
        digitalTree = new DigitalTree();
        xpController = new XPController(xpModel, xpView, digitalTree);

        toDoListV = new ToDoListView();
        toDoList = new ToDoList();
        toDoCont = new ToDoListController(toDoList, toDoListV, xpModel);

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
        VBox deckSelection = new VBox();
        deckSelection.getStyleClass().add("deck");
        deckSelection.setAlignment(Pos.TOP_CENTER);
        deckSelection.setMinWidth(screenWidth*0.15);
        deckSelection.setMinHeight(screenHeight);
        fullBox.getChildren().add(deckSelection);

        // Buttons for decks
        pageButton.setAlignment(Pos.CENTER);
        pageButton.setMinWidth(deckSelection.getMinWidth()-70);
        pageButton.setMinHeight(160);
        deckSelection.getChildren().add(pageButton);
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
        /*xpToggleButton = new Button("START XP TRACKING ");
        xpToggleButton.setOnAction(e -> toggleXPtracking());
        xpToggleButton.getStyleClass().add("xpbar");
        xpToggleButton.setMinHeight(50);

        todolist.getChildren().addAll(toDoListV.getToDoListView(), digitalTree.getTreeImageview(), xpView, xpToggleButton);
        fullBox.getChildren().add(todolist);

         */
        VBox tags = new VBox();
        addTagsAndSearchToLayout(tags);
        todolist.getChildren().addAll(toDoListV.getToDoListView(), tags);
        fullBox.getChildren().add(todolist);
        //Tags and stuff to work?

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

    // Section for managing tags
    private VBox tagsSection = new VBox();
    private TextField tagInputField = new TextField();
    private Button addTagButton = new Button("Add Tag");

    private VBox displayedTags = new VBox();

    // Section for searching notes
    private HBox searchSection = new HBox();
    private TextField searchField = new TextField();
    private Button searchButton = new Button("Search");

    private void initializeTagsSection() {
        tagInputField.setPromptText("Enter a tag...");
        addTagButton.setPrefSize(85, 25);
        addTagButton.setStyle("-fx-font-size: 15px;");
        addTagButton.setOnAction(e -> {
            String newTag = tagInputField.getText();
            if (!newTag.isEmpty()) {
                noteController.addTagNote(newTag);
                tagInputField.clear();
                updateDisplayedTags();
            }
        });
        tagsSection.getChildren().addAll(tagInputField, addTagButton, displayedTags);
        tagsSection.setSpacing(10);
        tagsSection.setAlignment(Pos.CENTER_LEFT);
        updateDisplayedTags();
    }

    private void initializeSearchSection() {
        searchField.setPromptText("Search for a note...");
        searchButton.setPrefSize(85, 25);
        searchButton.setStyle("-fx-font-size: 15px;");
        searchButton.setOnAction(e -> {
            String keyword = searchField.getText();
            if (!keyword.isEmpty()) {
                boolean found = noteController.searchNoteByKeyword(keyword);
                if (found) {
                    showSearchResultPopup("FOUND THE TAG");
                } else {
                    showSearchResultPopup("TAG NOT FOUND :(");
                }
            }
        });

        searchSection.getChildren().addAll(searchField, searchButton);
        searchSection.setSpacing(10);
        searchSection.setAlignment(Pos.CENTER_LEFT);
    }

    private void addTagsAndSearchToLayout(VBox mainLayout) {
        initializeSearchSection();
        initializeTagsSection();
        mainLayout.getChildren().addAll(searchSection, tagsSection);
    }
    private void updateDisplayedTags() {
        displayedTags.getChildren().clear(); // Clear the previous display

        for (String tag : noteModel.getTags()) {
            HBox tagItem = new HBox();
            tagItem.setSpacing(10);

            // Display the tag
            Label tagLabel = new Label(tag);

            // Add a remove button for each tag
            Button removeButton = new Button("Remove");
            removeButton.setStyle("-fx-font-size: 10px;");
            removeButton.setPrefSize(70,25);
            removeButton.setOnAction(e -> {
                noteController.removeTagFromNote(tag); // Remove the tag
                updateDisplayedTags(); // Refresh the display
            });

            tagItem.getChildren().add(tagLabel);
            tagItem.getChildren().add(removeButton);
            displayedTags.getChildren().add(tagItem);
        }
    }
    private void showSearchResultPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Search Result");
        alert.setHeaderText(null); // No header text
        alert.setContentText(message);
        alert.showAndWait(); // Blocks until the user dismisses the alert
    }
}
