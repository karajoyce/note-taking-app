package com.example.demo.controller;

import com.example.demo.FilerSystem.FlashcardStorage;
import javafx.stage.Stage;
import com.example.demo.model.Card;
import com.example.demo.model.FlashcardScreen;
import com.example.demo.view.EditCardView;

public class EditCardController {

    Card currentCard;
    EditCardView editView;
    FlashcardScreen fModel;
    FlashcardScreenController fControl;

    public EditCardController(Card card, FlashcardScreen model, FlashcardScreenController controller, Stage stage){
        currentCard = card;
        fModel = model;
        fControl = controller;
        editView = new EditCardView(stage, this::editCard);
        stage.show();
    }

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


