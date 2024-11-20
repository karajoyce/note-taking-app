package com.example.demo.controller;

/*
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 */

import com.example.demo.FilerSystem.FlashcardStorage;
import com.example.demo.model.Card;
import javafx.stage.Stage;
import com.example.demo.model.FlashcardScreen;
import com.example.demo.view.FlashcardScreenView;

public class FlashcardScreenController {

    private FlashcardScreen fCardModel; // The com.example.demo.model that represents the cards/deck
    private FlashcardScreenView fCardView; // The com.example.demo.view of the screen


    /**
     * Constructor
     */
    public FlashcardScreenController(FlashcardScreen model, FlashcardScreenView view){
        fCardModel = model;
        fCardView = view;

        fCardView.setCardModel(model);

        // set an action for the flip card button
        fCardView.setFlipCardButton(e -> {
            fCardView.flipIsBack();
            deckUpdate();
        });

        fCardView.setNextCardButton(e -> {
            fCardView.setChangeCard(true);
            if (fCardView.checkBack()){
                fCardView.flipIsBack();
            }
            deckUpdate();
        });

        fCardView.setBackCardButton(e -> {
            fCardView.setChangeCard(false);
            if (fCardView.checkBack()){
                fCardView.flipIsBack();
            }
            deckUpdate();
        });

        fCardView.setEditCardButton(e -> {
            new EditCardController(fCardView.getCurrentCard(), fCardModel, this, new Stage());
            deckUpdate();
        });

        fCardView.setDeleteButton(e -> {

            if (fCardModel.getDeck().getSize()==1){
                fCardModel.removeCard(fCardView.getCurrentCard());
                fCardView.setCurrentCard(null);
                deckUpdate();
            } else {
                Card temp = fCardView.getCurrentCard();
                fCardView.setChangeCard(true);
                fCardModel.removeCard(temp);
                deckUpdate();
            }
        });

        fCardView.setAddFlashcardButton(e -> {
            Card temp = new Card("", "");
            fCardModel.getDeck().addCard(temp);
            new EditCardController(temp, fCardModel, this, new Stage());
            deckUpdate();
        });

        fCardView.setChangeDeckButton(e -> {
            FlashcardStorage.LoadFlashCards();// todo, add string title to arguments
        });

        fCardView.setConfidentButton(e -> {
            fCardView.getCurrentCard().setConfidenceLevel(true);

            // flip to next card to allow only one choice
            fCardView.setChangeCard(true);
            if (fCardView.checkBack()){
                fCardView.flipIsBack();
            }
            deckUpdate();

            // todo save conf level to json
        });

        fCardView.setNotConfidentButton(e -> {
            fCardView.getCurrentCard().setConfidenceLevel(false);

            // flip to next card to allow only one choice
            fCardView.setChangeCard(true);
            if (fCardView.checkBack()){
                fCardView.flipIsBack();
            }
            deckUpdate();

            // todo save conf level to json
        });
    }

    public void deckUpdate(){
        fCardView.runDeckUpdate();
    }

}
