package com.example.demo.controller;

import com.example.demo.FilerSystem.FlashcardStorage;
import javafx.stage.Stage;
import com.example.demo.model.Card;
import com.example.demo.model.FlashcardScreen;
import com.example.demo.view.EditCardView;

/*
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Author: Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 */

public class EditCardController {

    Card currentCard; // hold the instance of the card we are changing
    EditCardView editView; // hold the instance of the view
    FlashcardScreen fModel; // hold the instance of the flashcard model to make the changes
    FlashcardScreenController fControl; // hold the instance of the flashcard controller to make the changes


    /**
     * Constructor for the EditCardController, editview will be created here and assigned to the editcard function
     * @param card = currentCard, set the current card
     * @param model = flashcard model, set the model
     * @param controller = flashcard controller, set the flashcard controller
     * @param stage = a new stage to display
     */
    public EditCardController(Card card, FlashcardScreen model, FlashcardScreenController controller, Stage stage){
        currentCard = card;
        fModel = model;
        fControl = controller;
        editView = new EditCardView(stage, this::editCard);
        stage.setAlwaysOnTop(true);
        stage.showAndWait();
    }

    /**
     * This function grabs the text the user inputs and changes the current card the user had showing.
     * If the text is empty it will be unchanged
     */
    public void editCard(){
        String frontCard = editView.getFrontDescription();
        String backCard = editView.getBackDescription();

        if (!frontCard.isEmpty()) {
            currentCard.setCardFront(frontCard);
        }
        if (!backCard.isEmpty()) {
            currentCard.setCardBack(backCard);
        }
        fControl.deckUpdate();
        FlashcardStorage.SaveDeck(fModel.getDeck());
    }
}


