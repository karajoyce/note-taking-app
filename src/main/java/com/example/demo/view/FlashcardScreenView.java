package com.example.demo.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import com.example.demo.model.Card;
import com.example.demo.model.FlashcardScreen;
import java.util.ArrayList;

/**

 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Author: Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521

 **/

public class FlashcardScreenView extends StackPane {

    private boolean isBack = false;
    private FlashcardScreen flashcardModel;
    private ArrayList<Card> deck;
    private Button next; // button to page next on flashcards
    private Button flip; // button to turn over flashcard
    private Button edit; // button to edit a flashcard
    private Button back; // button to move backwards in the deck

    private Button deckButton;
    private Button pageBack;
    private Button removeCard;
    private Card tempCard;
    private Card currentCard = null;
//    private ToDoListView toDoListV;
//    private ToDoListController toDoCont;
//    private ToDoList toDoList;

    public FlashcardScreenView() {

        //-------------------------
        // Bottom Buttons set up
        next = new Button(" > ");
        back = new Button(" < ");
        flip = new Button(" ⭯ ");
        edit = new Button(" ✎ ");
        deckButton = new Button("Test Desk"); // this should be a deck name later
        pageBack = new Button(" <-- ");
        removeCard = new Button(" X ");
        tempCard = new Card("Insert more cards", "");

//        toDoListV = new ToDoListView();
//        toDoList = new ToDoList();
//        toDoCont = new ToDoListController(toDoList, toDoListV);
    }

    public void runDeckUpdate(){

        // General class things/size
        this.getStyleClass().add("cardview");
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
        deckButton.setAlignment(Pos.CENTER);
        deckButton.setMinWidth(deckSelection.getMinWidth()-40);
        deckButton.setMinHeight(80);
        deckSelection.getChildren().add(deckButton);
        //-------------------------END

        //-------------------------
        // Set up flashcard middle section
        VBox cardSection = new VBox();
        cardSection.getStyleClass().add("cardsection");
        cardSection.setAlignment(Pos.CENTER_LEFT);
        cardSection.setMinWidth(screenWidth*0.6);
        cardSection.setMinHeight(screenHeight);
        fullBox.getChildren().add(cardSection);


        //Card text setup
        HBox fCard = new HBox();
        fCard.setMinHeight(cardSection.getMinHeight()*(6.0/8.0));
        fCard.getStyleClass().add("textflow");
        fCard.setAlignment(Pos.CENTER);

        // Back button
        HBox topButtons = new HBox();
        topButtons.setMinHeight(cardSection.getMinHeight()*(1.0/8.0));
        topButtons.getStyleClass().add("hbox");
        cardSection.getChildren().add(topButtons);

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

        VBox rightBar = new VBox();
        rightBar.setMinHeight(topButtons.getMinHeight()-18);
        rightBar.setMinWidth((cardSection.getMinWidth()-50)/2);
        rightBar.getStyleClass().add("topbar");
        removeCard.setMinHeight(50);
        removeCard.setMinWidth(80);
        removeCard.setAlignment(Pos.CENTER);
        rightBar.setAlignment(Pos.CENTER_RIGHT);
        rightBar.getChildren().add(removeCard);
        topButtons.getChildren().add(rightBar);

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
                backText.setWrappingWidth(cardSection.getMinHeight() - 40);
                fCard.getChildren().add(backText);
                backText.setTextAlignment(TextAlignment.CENTER);
                fCard.setStyle("-fx-background-color: #c3c7d7");
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
//        todolist.getChildren().add(toDoListV.getToDoListView());
        todolist.setAlignment(Pos.TOP_CENTER); // this isn't working
        todolist.setMinWidth((screenWidth*0.25));
        todolist.setMinHeight(screenHeight);
        todolist.getStyleClass().add("fake");
        Text todoL = new Text("Pick up kids     Oct 14: 10AM");
        todolist.getChildren().add(todoL);
        fullBox.getChildren().add(todolist);
        //-------------------------END
    }
    /**
     * Will update the deck if any new cards are added.
     */
    public void updateDeckList(Card newCard){
        this.deck.add(newCard);
        runDeckUpdate();
    }
    /**
     * An event handler for the edit button
     */
    public void setEditCardButton(javafx.event.EventHandler<javafx.event.ActionEvent> handler){
        edit.setOnAction(handler);
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
        for (Card card: deck){
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
            newCard = this.deck.getFirst();
            // Loop to find the next card
            if (ID < this.deck.size()){
                newCard = this.deck.get(ID);
            }
        } else {
            // Set up new ID
            int ID = index - 1;
            // Case for hitting the beginning and wrapping to the end
            newCard = this.deck.getLast();
            // Loop to find the next card
            if (ID >= 0){
                newCard = this.deck.get(ID);
            }
        }

        if (newCard != null) {
            currentCard = newCard;
        }
    }

    public Card getCurrentCard(){
        return currentCard;
    }

    public void setCardModel(FlashcardScreen model){
        currentCard = model.getDeck().getFirst();
        flashcardModel = model;
        this.deck = model.getDeck();
        runDeckUpdate();
    }

    public void setCurrentCard(Card card){
        currentCard = card;
    }

    public boolean checkBack(){
        return isBack;
    }
}
