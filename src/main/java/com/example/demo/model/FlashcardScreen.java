package com.example.demo.model;

import com.example.demo.FilerSystem.*;

import java.util.ArrayList;

/**

 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Author: Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521

 **/

public class FlashcardScreen {

    private Deck currentDeck; // storing the deck of cards

    public FlashcardScreen(){ // send in current deck?
        // this needs to access database for proper deck based on controller button function
        currentDeck = new Deck("Test Deck");
    }

    public Deck getDeck(){
        return this.currentDeck;
    }
    public void setDeck(Deck deck){
        this.currentDeck = deck;
    }

    public void addCard(String front, String back){


        Card newcard =  new Card(front, back);
        this.currentDeck.addCard(newcard);
        FlashcardStorage.SaveDeck(currentDeck);


    }

    public void removeCard(Card card){
        this.getDeck().removeCard(card);
    }

    public void editCard(Card card, String front, String back){
        card.setCardFront(front);
        card.setCardBack(back);
    }

}
