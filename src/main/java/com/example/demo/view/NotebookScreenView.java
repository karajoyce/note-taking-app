package com.example.demo.view;

import com.example.demo.FilerSystem.ToDoStorage;
import com.example.demo.HelloApplication;
import com.example.demo.controller.ToDoListController;
import com.example.demo.controller.XPController;
import com.example.demo.model.*;
import com.example.demo.notes.NoteController;
import com.example.demo.notes.NoteModel;
import com.example.demo.notes.NoteView;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import org.fxmisc.richtext.InlineCssTextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.*;
import java.util.Objects;
import javafx.scene.control.Label;

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
    private MotivationalMessagesView motivationalMessagesView;

    //Digital Tree
    private DigitalTree digitalTree;

    /**CHANGES BY NATHAN FOR TAGS*/
    //UI things for tags, MIGHT NEED TO CHANGE TO A NEW WINDOW WE'LL SEE
    private FlowPane tagDisplayPane = new FlowPane();
    private Button manageTagsButton = new Button("Manage Tags");
    private VBox tagSection = new VBox();
    //Action Listeners for tags
    private OnTagActionListener onAddTagListener;
    private OnTagActionListener onRemoveTagListener;

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
        screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 100;
        screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 100;

        renamePage = new Button("");
        Image imgR = new Image(getClass().getResourceAsStream("/renameicon.png"));
        ImageView imgViewR = new ImageView(imgR);
        imgViewR.setFitHeight(30);
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

        toDoList = ToDoStorage.LoadToDoList();
        toDoListV = new ToDoListView(toDoList);
        toDoListV.setTaskList(ToDoStorage.LoadToDoList(), xpModel);
        toDoCont = new ToDoListController(toDoList, toDoListV, xpModel);
        motivationalMessagesView = new MotivationalMessagesView();

        currentFolder = new String();

        noteModel = new NoteModel();

        currentNotebook = currNotebook;
        setCurrentPage(currNotebook.getNotes().getFirst());

        /* Initialize (MVC) */
        noteController = new NoteController(noteModel);
        noteView = new NoteView(noteController);

        /**CHANGES BY NATHAN **/
        /**Might need to change this later*/
        initializeTagSection();

        runScreenUpdate();
    }

    public void setNotebook(Notebook notebook) {
        this.currentNotebook = notebook;
        runScreenUpdate();
    }


    public void setCurrentFolder(String folderName) {
        // Update the current folder
        this.currentFolder = folderName;



        // Update the title or header to reflect the current folder
        this.getChildren().clear(); // Clear the existing view

        // Re-run the update logic with the current folder set
        runScreenUpdate();

        // Add a label or update the screen to show the folder name
        VBox headerBox = new VBox();
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setSpacing(10);

        javafx.scene.control.Label folderLabel = new javafx.scene.control.Label("Current Folder: " + folderName);
        folderLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");
        headerBox.getChildren().add(folderLabel);

        // Add the header box at the top of the UI
        this.getChildren().add(0, headerBox);
    }

    public void runScreenUpdate() {
        // General class things/size
        this.getStyleClass().add("wholescreen");
        double screenHeight = Screen.getPrimary().getBounds().getMaxY() - 100;
        double screenWidth = Screen.getPrimary().getBounds().getMaxX() - 100;
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
        deckSelection.setMinWidth(screenWidth * 0.15);
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
        topLine.setSpacing(5);
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
        cardSection.setMinWidth((screenWidth * 0.6) - 10);
        cardSection.setMinHeight(screenHeight);
        fullBox.getChildren().add(cardSection);

        InlineCssTextArea textArea = noteModel.getTextArea();

        /*
        javafx.scene.control.MenuBar menuBar = noteView.createMenuBar(HelloApplication.getStage());
        menuBar.getStyleClass().add("menuBar");
        */

        ToolBar toolBar = noteView.createToolBar();

        HBox menuItems = new HBox();
        menuItems.getStyleClass().add("textEditorBar");
        menuItems.setMaxHeight(50);
        menuItems.setMinHeight(50);
        menuItems.setAlignment(Pos.CENTER);
        menuItems.setMaxWidth(cardSection.getMinWidth());

        HBox fileButton = new HBox();
        fileButton.setStyle("-fx-padding: 0");
        /*
        menuBar.setMinHeight(50);
        menuBar.setMaxHeight(50);
        fileButton.setMinWidth(55);
        fileButton.setMaxWidth(55);
        fileButton.getChildren().add(menuBar);
*/
        HBox stylesButton = new HBox();
        stylesButton.setMinHeight(50);
        stylesButton.setMaxHeight(50);
        stylesButton.getChildren().add(toolBar);
        toolBar.setMinWidth(cardSection.getMinWidth() - 55);
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
        fCard.setMinHeight(cardSection.getMinHeight() * (6.0 / 8.0));
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

        //todolist.getChildren().addAll(toDoListV.getToDoListView(), digitalTree.getTreeImageview(), xpView, xpToggleButton);
        toDoListV.setTaskList(ToDoStorage.LoadToDoList(), this.xpModel);
        //fullBox.getChildren().add(todolist);
        VBox tags = new VBox();
//        addTagsAndSearchToLayout(tags);
        todolist.getChildren().addAll(motivationalMessagesView.getMotivmsgView(),toDoListV.getToDoListView(), tags);
        /*CHANGES BY NATHAN TAG SECTION REDO*/
        ScrollPane tagScrollPane = new ScrollPane(tagDisplayPane);
        tagScrollPane.setFitToWidth(true);
        todolist.getChildren().addAll(tagScrollPane,manageTagsButton);
        fullBox.getChildren().add(todolist);

        // Buttons for pages
        populatePages(deckSelection);
        //-------------------------END
    }

    /**
     * Function that grabs the list of decks from the JSON to make the buttons on the deck selection pane
     *
     * @param pageBox: the box to draw the buttons in
     */
    public void populatePages(ListView<Button> pageBox) {
        // Get names from the JSON
        for (Page page : currentNotebook.getNotes()) {
            Button tButton = new Button(page.getTitle());
            tButton.setAlignment(Pos.CENTER);
            tButton.setMinWidth(pageBox.getMinWidth() - 70);
            tButton.setMaxWidth(pageBox.getMinWidth() - 70);
            tButton.setMinHeight(160);
            tButton.wrapTextProperty().setValue(true);
            tButton.setTextAlignment(TextAlignment.CENTER);

            if (Objects.equals(currentPage.getTitle(), page.getTitle())){
                tButton.setStyle("-fx-background-color: #9f6395");
            }

            pageBox.getItems().add(tButton);
            tButton.setOnAction(pageHandler);
        }

    }

    public NoteController getNoteController() {
        return this.noteController;
    }



    public void setChangeButton(javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        pageHandler = handler;
    }

    public Button getBackButton() {
        return pageBack;
    }

    public void setAddPageButton(javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        pageHandler = handler;
    }

    /**
     * An event handler for the confident button
     */
    public void setAddPage(javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        addPage.setOnAction(handler);
    }

    /**
     * An event handler for the confident button
     */
    public void setDeletePage(javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        removePage.setOnAction(handler);
    }

    /**
     * An event handler for the confident button
     */
    public void setRenamePage(javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        renamePage.setOnAction(handler);
    }

    //Adding XP tracking when entering this screen
    public void startXPtracking() {
        xpController.startXPTimer();
    }

    public void stopXPtracking() {
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

    public void setCurrentPage(Page page) {
        currentPage = page;
        currentPage.getContents().setPrefHeight(800);
        currentPage.getContents().setPrefWidth(800);
        noteModel.setTextArea(page.getContents());
        noteModel.resetTextAreaStyles(noteController);
        noteModel.getTextArea().textProperty().addListener(((observableValue, s, t1) -> noteController.applyCurrentStyleToNewText()));
    }

    public Page getCurrentPage() {
        return this.currentPage;
    }

    /**CHANGES BY NATHAN FOR TAGS*/
    private void initializeTagSection() {
        // Configure the "Manage Tags" button
        manageTagsButton.setOnAction(e -> {
            System.out.println("Manage Tags button clicked!"); // Placeholder action
        });

        // Configure the tag display pane (horizontal flow layout)
        tagDisplayPane.setHgap(10); // Horizontal gap between tags
        tagDisplayPane.setVgap(10); // Vertical gap (if wrapping occurs)
        tagDisplayPane.setPadding(new Insets(5));
        tagDisplayPane.setAlignment(Pos.CENTER_LEFT);
        tagDisplayPane.setPrefWrapLength(300); // Adjust width to control wrapping

        // Add components to the tag section
        tagSection.getChildren().addAll(tagDisplayPane, manageTagsButton);
        tagSection.setSpacing(10);
        tagSection.setAlignment(Pos.CENTER_LEFT);
        tagSection.setPadding(new Insets(10));

        // Populate the initial tag display
        updateDisplayedTags();
    }

    // Allow the controller to set the action for the "Manage Tags" button
    public void setManageTagsButtonAction(Runnable action) {
        manageTagsButton.setOnAction(e -> action.run());
    }
    public void updateDisplayedTags() {
        tagDisplayPane.getChildren().clear(); // Clear current tag display

        // Loop through tags and create styled "pill-shaped" labels
        for (String tag : currentNotebook.getTags()) {
            HBox tagBox = createTagBox(tag);
            tagDisplayPane.getChildren().add(tagBox); // Add to the display
        }
    }

    private HBox createTagBox(String tag) {
        HBox tagBox = new HBox();
        Label tagLabel = new Label(tag);
        Button removeButton = new Button("x");

        // Style the tag box
        tagBox.setStyle("-fx-background-color: #e0e0e0; -fx-background-radius: 15; -fx-padding: 5;");
        tagBox.setAlignment(Pos.CENTER);
        tagBox.setSpacing(5);

        // Style the tag label
        tagLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 12px;");

        // Style the remove button
        removeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: red; -fx-font-size: 12px;");
        removeButton.setOnAction(e -> {
            // Optional: Notify controller of tag removal
            System.out.println("Remove tag: " + tag);
        });

        tagBox.getChildren().addAll(tagLabel, removeButton); // Add the label and remove button
        return tagBox;
    }

    public interface OnTagActionListener{
        void onTagAction(String tag);
    }
}






