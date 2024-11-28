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
import com.example.demo.model.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import com.example.demo.view.FlashcardScreenView;

public class FlashcardScreenController {

    private FlashcardScreen fCardModel; // The com.example.demo.model that represents the cards/deck
    private FlashcardScreenView fCardView; // The com.example.demo.view of the screen
    private XPModel xpModel;

    /**
     * Constructor
     */
    public FlashcardScreenController(FlashcardScreen model, FlashcardScreenView view){
        fCardModel = model;
        fCardView = view;
        this.xpModel = XPManager.getXPModel();

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
            FlashcardStorage.SaveDeck(fCardModel.getDeck());
        });

        fCardView.setAddFlashcardButton(e -> {
            Card temp = new Card("", "");
            fCardModel.getDeck().addCard(temp);
            new EditCardController(temp, fCardModel, this, new Stage());
            if (temp.getCardFront().isEmpty()){
                fCardModel.getDeck().removeCard(temp);
            }
            deckUpdate();
        });

        fCardView.setChangeDeckButton(e -> {
            Deck newDeck = FlashcardStorage.LoadFlashCards(((Button)e.getSource()).getText());
            if (newDeck.getCards().isEmpty()){
                fCardView.setCurrentCard(new Card("", ""));
            } else {
                fCardView.setCurrentCard(newDeck.getCards().getFirst());
            }
            fCardModel.setDeck(newDeck);
            fCardView.runDeckUpdate();
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
        deckUpdate();
    }

    public void deckUpdate(){
        fCardView.runDeckUpdate();
    }

    public void addFlashCardXp(double xp){
        xpModel.addXP(xp);
    }

    public void addFlashcardDeck(String name){
        System.out.println(name);
        Deck newDeck = new Deck(name);
        FlashcardStorage.SaveDeck(newDeck);
    }

}

