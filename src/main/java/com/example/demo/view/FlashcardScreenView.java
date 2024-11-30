package com.example.demo.view;

import com.example.demo.FilerSystem.FlashcardStorage;
import com.example.demo.FilerSystem.ToDoStorage;
import com.example.demo.controller.ToDoListController;
import com.example.demo.controller.XPController;
import com.example.demo.model.*;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**

 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Author: Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521

 **/
public class FlashcardScreenView extends StackPane {

    private boolean isBack = false; // checking if we should be on the back of the card
    private FlashcardScreen flashcardModel; // instance of the model
    private Button next; // button to page next on flashcards
    private Button flip; // button to turn over flashcard
    private Button edit; // button to edit a flashcard
    private Button back; // button to move backwards in the deck
    private Button pageBack; // button to go back to main menu
    private Button removeCard; // button to remove card

    private Button addFlashcard; // button to add a flashcard
    private Button thumbsUpButton; // button for confidence gauging
    private Button thumbsDownButton; // button for confidence gauging
    private Card tempCard; // temporary card for when we remove the only card in a deck
    private Card currentCard = null; // store the card we are on
    private javafx.event.EventHandler<javafx.event.ActionEvent> deckHandler;
    //Adding XP Bar and System
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

    public FlashcardScreenView() {

        //-------------------------
        // Buttons set up
        next = new Button(" > ");
        back = new Button(" < ");
        flip = new Button(" ⭯ ");
        edit = new Button(" ✎ ");
        addFlashcard = new Button(" + ");
        pageBack = new Button(" Back ");
        removeCard = new Button(" Remove ");
        tempCard = new Card("Insert more cards", "");
        thumbsUpButton = new Button("Confident");
        thumbsDownButton = new Button("Not Confident");
        ToDoList toDoList = ToDoStorage.LoadToDoList();
        this.toDoListV = new ToDoListView(toDoList);
        ToDoListController toDoListController = new ToDoListController(toDoList, toDoListV, xpModel);

        //Initializing XP bar and system;
        xpModel = XPManager.getXPModel();
        xpView = new XPView();
        //Initializing new Tree
        digitalTree = new DigitalTree();
        xpController = new XPController(xpModel, xpView, digitalTree);

        toDoList = ToDoStorage.LoadToDoList();
        toDoListV = new ToDoListView(toDoList);
        toDoCont = new ToDoListController(toDoList, toDoListV, xpModel);
    }

    /**
     * Function that will re-draw the screen when something is changed
     * Post-condition: screen will reflect the newest changes
     */
    public void runDeckUpdate(){

        if (flashcardModel.checkCurrentDeck()){
            if (flashcardModel.getDeck().getCards().isEmpty()){
                setCurrentCard(new Card("", ""));
            } else {
                setCurrentCard(flashcardModel.getDeck().getCards().getFirst());
            }
        }

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
        ListView<Button> deckSelection = new ListView<>();
        deckSelection.setMaxHeight(screenHeight-100);
        deckSelection.getStyleClass().add("deck");
        deckSelection.setMinWidth(screenWidth*0.15);
        deckSelection.setMinHeight(screenHeight);
        fullBox.getChildren().add(deckSelection);

        // Buttons for decks
        populateButtons(deckSelection);
        //-------------------------END

        //-------------------------
        // Set up flashcard middle section
        VBox cardSection = new VBox();
        cardSection.getStyleClass().add("cardsection");
        cardSection.setAlignment(Pos.CENTER_LEFT);
        cardSection.setMinWidth((screenWidth*0.6)-30);
        cardSection.setMaxWidth((screenWidth*0.6)-30);
        cardSection.setMinHeight(screenHeight);
        fullBox.getChildren().add(cardSection);


        //Card text setup
        VBox fCard = new VBox();
        fCard.setMinHeight(cardSection.getMinHeight()*(6.0/8.0));
        fCard.getStyleClass().add("textflow");
        fCard.setAlignment(Pos.CENTER);

        // Back button
        HBox topButtons = new HBox();
        topButtons.setMinHeight(cardSection.getMinHeight()*(1.0/8.0));
        topButtons.getStyleClass().add("hbox");
        cardSection.getChildren().add(topButtons);

        // Setting up header bar
        VBox leftBar = new VBox();
        leftBar.setMinHeight(topButtons.getMinHeight()-18);
        leftBar.setMinWidth((cardSection.getMinWidth()-50)/2);
        leftBar.getStyleClass().add("topbar");
        pageBack.setMinHeight(50);
        pageBack.setMinWidth(80);
        pageBack.setAlignment(Pos.CENTER);
        leftBar.setAlignment(Pos.CENTER_LEFT);
        leftBar.getChildren().add(pageBack);
        topButtons.getChildren().add(leftBar);

        HBox rightBar = new HBox();
        rightBar.setMinHeight(topButtons.getMinHeight()-18);
        rightBar.setMinWidth((cardSection.getMinWidth()-50)/2);
        rightBar.getStyleClass().add("topbar");
        rightBar.setAlignment(Pos.CENTER_RIGHT);
        topButtons.getChildren().add(rightBar);

        HBox rightBarPart = new HBox();
        rightBarPart.getStyleClass().add("topbar");
        rightBarPart.setMinHeight(topButtons.getMinHeight()-18);
        removeCard.setMinHeight(50);
        removeCard.setMinWidth(80);
        removeCard.setAlignment(Pos.CENTER);
        rightBarPart.setAlignment(Pos.CENTER);
        rightBarPart.getChildren().add(removeCard);
        rightBar.getChildren().add(rightBarPart);

        HBox middleBar = new HBox();
        middleBar.setMinHeight(topButtons.getMinHeight()-18);
        middleBar.setAlignment(Pos.CENTER);
        middleBar.getStyleClass().add("topbar");
        addFlashcard.setMinHeight(50);
        addFlashcard.setMinWidth(80);
        addFlashcard.setAlignment(Pos.CENTER);
        middleBar.getChildren().add(addFlashcard);
        rightBar.getChildren().add(middleBar);

        if (currentCard == null){
            Text frontText = new Text(tempCard.getCardFront());
            frontText.setWrappingWidth(cardSection.getMinWidth() - 40);
            fCard.getChildren().add(frontText);
            frontText.setTextAlignment(TextAlignment.CENTER);
        } else {
            if (!isBack) {
                Text frontText = new Text(this.currentCard.getCardFront());
                frontText.setWrappingWidth(cardSection.getMinWidth() - 40);
                fCard.getChildren().add(frontText);
                frontText.setTextAlignment(TextAlignment.CENTER);
            } else {
                Text backText = new Text(this.currentCard.getCardBack());
                HBox textBox = new HBox(backText);
                textBox.setMinHeight(fCard.getMinHeight()/2);
                backText.setWrappingWidth(cardSection.getMinWidth() - 40);

                HBox confButtons = new HBox();
                confButtons.setStyle("-fx-spacing: 20px");
                confButtons.setMinHeight((fCard.getMinHeight()/2)-20);
                confButtons.getChildren().addAll(thumbsUpButton, thumbsDownButton);
                fCard.getChildren().addAll(backText, confButtons);

                confButtons.setAlignment(Pos.BOTTOM_CENTER);
                textBox.setAlignment(Pos.BOTTOM_CENTER);
                backText.setTextAlignment(TextAlignment.CENTER);
                fCard.setAlignment(Pos.BOTTOM_CENTER);
                fCard.setStyle("-fx-background-color: #faeefd");
            }
        }
        cardSection.getChildren().add(fCard);

        // Buttons for card control
        HBox bottomButtons = new HBox();
        bottomButtons.setMinHeight(cardSection.getMinHeight()*(1.0/8.0));
        bottomButtons.getStyleClass().add("hbox");
        cardSection.getChildren().add(bottomButtons);
        bottomButtons.setAlignment(Pos.CENTER);

        next.setMaxWidth(80);
        next.setMinWidth(80);
        back.setMaxWidth(80);
        back.setMinWidth(80);
        flip.setMaxWidth(80);
        flip.setMinWidth(80);
        edit.setMinWidth(80);

        next.setMaxHeight(50);
        next.setMinHeight(50);
        back.setMaxHeight(50);
        back.setMinHeight(50);
        flip.setMaxHeight(50);
        flip.setMinHeight(50);
        edit.setMinHeight(50);

        bottomButtons.getChildren().add(back);
        bottomButtons.getChildren().add(flip);
        bottomButtons.getChildren().add(edit);
        bottomButtons.getChildren().add(next);
        //-------------------------END

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

        toDoListV.setMaxHeight(350);
        toDoListV.setTaskList(ToDoStorage.LoadToDoList(), this.xpModel);
        todolist.getChildren().addAll(toDoListV.getToDoListView(), digitalTree.getTreeImageview(), this.xpView, xpToggleButton);
        toDoListV.setTaskList(ToDoStorage.LoadToDoList(), this.xpModel);
        fullBox.getChildren().add(todolist);
        //-------------------------END

    }

    /**
     * Function that grabs the list of decks from the JSON to make the buttons on the deck selection pane
     * @param buttonBox: the box to draw the buttons in
     */
    public void populateButtons(ListView<Button> buttonBox){
        // Get names from the JSON
        List<String> titles = FlashcardStorage.GenerateDeckTitles();
        for (String title: titles){
            if (title.equals("Empty Deck") && titles.size() > 1){
                FlashcardStorage.DeleteDeck("Empty Deck");
                continue;
            }
            Button tButton = new Button(title);
            tButton.setAlignment(Pos.CENTER);
            tButton.setMinWidth(buttonBox.getMinWidth()-45);
            tButton.setMaxWidth(buttonBox.getMinWidth()-45);
            tButton.setMinHeight(80);
            tButton.wrapTextProperty().setValue(true);
            tButton.setTextAlignment(TextAlignment.CENTER);

            if (Objects.equals(flashcardModel.getDeck().getTitle(), title)){
                tButton.setStyle("-fx-background-color: #9f6395");
            }

            buttonBox.getItems().add(tButton);
            tButton.setOnAction(deckHandler);
        }
    }

    /**
     * An event handler for the edit button
     */
    public void setEditCardButton(javafx.event.EventHandler<javafx.event.ActionEvent> handler){
        edit.setOnAction(handler);
    }

    /**
     * An event handler for the confident button
     */
    public void setConfidentButton(javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        thumbsUpButton.setOnAction(event -> {
            // Execute original handler logic, if any
            if (handler != null) {
                handler.handle(event);
                xpModel.addXP(10);
            }

        });
    }


    /**
     * An event handler for the confident button
     */
    public void setNotConfidentButton(javafx.event.EventHandler<javafx.event.ActionEvent> handler){
        thumbsDownButton.setOnAction(handler);
    }


    /**
     * An event handler for the next button
     */
    public void setNextCardButton(javafx.event.EventHandler<javafx.event.ActionEvent> handler){
        next.setOnAction(handler);
    }
    /**
     * An event handler for the flip button
     */
    public void setFlipCardButton(javafx.event.EventHandler<javafx.event.ActionEvent> handler){
        flip.setOnAction(handler);
    }

    public Button getBackButton() {
        return pageBack;
    }

    /**
     * An event handler for the back button
     */
    public void setBackCardButton(javafx.event.EventHandler<javafx.event.ActionEvent> handler){
        back.setOnAction(handler);
    }

    /**
     * An event handler for the back button
     */
    public void setDeleteButton(javafx.event.EventHandler<javafx.event.ActionEvent> handler){
        removeCard.setOnAction(handler);
    }
    /**
     * An event handler for the add button
     */
    public void setAddFlashcardButton(javafx.event.EventHandler<javafx.event.ActionEvent> handler){
        addFlashcard.setOnAction(handler);
    }

    /**
     * An event handler for the add button
     */
    public void setChangeDeckButton(javafx.event.EventHandler<javafx.event.ActionEvent> handler){
        deckHandler = handler;
    }

    /**
     * Method to tell the card to flip itself.
     */
    public void flipIsBack(){
        isBack = !isBack;
    }
    /**
     * Update current card by either adding (true) or subtracting (false)
     */
    public void setChangeCard(boolean bool) {
        Card newCard = null;
        int index = 0;
        for (Card card: flashcardModel.getDeck().getCards()){
            if (card == currentCard){
                break;
            }
            index +=1;
        }
        // If bool is true, they want to move forward
        if (bool) {
            // Set up new ID
            int ID = index + 1;
            // Case for hitting the end and wrapping to the beginning
            newCard = this.flashcardModel.getDeck().getCards().getFirst();
            // Loop to find the next card
            if (ID < this.flashcardModel.getDeck().getSize()){
                newCard = this.flashcardModel.getDeck().getCards().get(ID);
            }
        } else {
            // Set up new ID
            int ID = index - 1;
            // Case for hitting the beginning and wrapping to the end
            newCard = this.flashcardModel.getDeck().getCards().getLast();
            // Loop to find the next card
            if (ID >= 0){
                newCard = this.flashcardModel.getDeck().getCards().get(ID);
            }
        }

        if (newCard != null) {
            currentCard = newCard;
        }
    }

    /**
     * Function to get the current card
     */
    public Card getCurrentCard(){
        return currentCard;
    }

    /**
     * Function to initiate the card model
     * @param model
     */
    public void setCardModel(FlashcardScreen model){
        if (currentCard==null){
            model.addCard("Insert cards", "");
            currentCard = model.getDeck().getCards().getFirst();
        } else {
            currentCard = model.getDeck().getCards().getFirst();
        }
        flashcardModel = model;
        runDeckUpdate();
    }

    public void setCurrentCard(Card card){
        currentCard = card;
    }

    public boolean checkBack(){
        return isBack;
    }


    //Adding XP tracking when entering this screen
    public void startXPtracking(){
        xpController.startXPTimer();
    }

    public void stopXPtracking(){
        xpController.stopXPTimer();
    }
    //BUTTON FUNCTIONS FOR XP TRACKING FOR NOW
    private void toggleXPtracking(){
        if (isTrackingXP){
            stopXPtracking();
            xpToggleButton.setText("START XP TRACKING");
        }
        else{
            startXPtracking();
            xpToggleButton.setText("STOP XP TRACKING");
        }
        isTrackingXP = !isTrackingXP;
    }

    /**
     * Updates the to-do list view with the latest tasks.
     */
    public void updateToDoListView() {
        toDoListV.setTaskList(ToDoStorage.LoadToDoList(), xpModel);
    }
}


