package com.example.demo.controller;

/*
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Author: Kara Leier, kjl061, 11293306
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
    private XPModel xpModel; // the model for the xp feature

    /**
     * Constructor for flashcard screen. sets the xpModel to the model of the screen before.
     * Holds functions for all button clicks.
     * @param model = the flashcard model
     * @param view = the flashcard view
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

        // set an action for when the user wants to change cards forwards
        fCardView.setNextCardButton(e -> {
            fCardView.setChangeCard(true);
            if (fCardView.checkBack()){
                fCardView.flipIsBack();
            }
            deckUpdate();
        });

        // set an action for the user wants to change cards backwards
        fCardView.setBackCardButton(e -> {
            fCardView.setChangeCard(false);
            if (fCardView.checkBack()){
                fCardView.flipIsBack();
            }
            deckUpdate();
        });

        // set an action for when the user wants to edit a card
        fCardView.setEditCardButton(e -> {
            new EditCardController(fCardView.getCurrentCard(), fCardModel, this, new Stage());
            deckUpdate();
        });

        // set an action for when a user wants to delete a card
        fCardView.setDeleteButton(e -> {
            // different functionality for if the deck has one card left.
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
            // save the new deck
            FlashcardStorage.SaveDeck(fCardModel.getDeck());
        });

        // set an action for when the user wants to add a new card
        fCardView.setAddFlashcardButton(e -> {
            Card temp = new Card("", "");
            fCardModel.getDeck().addCard(temp);
            new EditCardController(temp, fCardModel, this, new Stage());
            if (temp.getCardFront().isEmpty()){
                fCardModel.getDeck().removeCard(temp);
            }
            deckUpdate();
        });

        // set an action for when the user wants to change the deck they are in
        fCardView.setChangeDeckButton(e -> {
            Deck newDeck = FlashcardStorage.LoadFlashCards(((Button)e.getSource()).getText());
            if (newDeck.getCards().isEmpty()){
                fCardView.setCurrentCard(new Card("", ""));
            } else {
                fCardView.setCurrentCard(newDeck.getCards().getFirst());
            }
            fCardModel.setDeck(newDeck);
            deckUpdate();
        });

        // set an action for when the user clicks the confident button
        fCardView.setConfidentButton(e -> {
            fCardView.getCurrentCard().setConfidenceLevel(true);

            // flip to next card to allow only one choice
            fCardView.setChangeCard(true);
            if (fCardView.checkBack()){
                fCardView.flipIsBack();
            }
            deckUpdate();

        });

        // set an action for when the user clicks the not confident button
        fCardView.setNotConfidentButton(e -> {
            fCardView.getCurrentCard().setConfidenceLevel(false);

            // flip to next card to allow only one choice
            fCardView.setChangeCard(true);
            if (fCardView.checkBack()){
                fCardView.flipIsBack();
            }
            deckUpdate();

        });
        deckUpdate();
    }

    /**
     * get the instance of the model from the controller
     * @return flashcard model
     */
    public FlashcardScreen getfCardModel() {
        return fCardModel;
    }

    /**
     * redraw the screen after changes occur
     */
    public void deckUpdate(){
        fCardView.runDeckUpdate();
    }

    public void addFlashCardXp(double xp){
        xpModel.addXP(xp);
    }

    /**
     * add a new flashcard deck to the screen
     * @param name = the title of the deck
     */
    public void addFlashcardDeck(String name){
        Deck newDeck = new Deck(name);
        FlashcardStorage.SaveDeck(newDeck);
    }

    /**
     *  remove a deck from the screen
     * @param name = title of the deck to be removed
     */
    public void deleteDeck(String name){
        FlashcardStorage.DeleteDeck(name);
    }

}

