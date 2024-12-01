package com.example.demo.model;

import com.example.demo.FilerSystem.FlashcardStorage;

import java.util.ArrayList;

/**

 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Author: Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521

 **/

public class Deck {
    private String title;
    private ArrayList<Card> cards; // instance of the deck

    /**
     * set up the deck
     * @param name
     */
    public Deck(String name){
        this.title = name;
        cards = new ArrayList<>();
    }

    /**
     * add a card to the deck instance
     * @param newCard = card to add
     */
    public void addCard(Card newCard){
        cards.add(newCard);

    }

    /**
     * remove a card from the deck instance
     * @param oldCard = card to remove
     */
    public void removeCard(Card oldCard){
        this.cards.remove(oldCard);
    }

    /**
     * get the number of cards in the deck
     * @return
     */
    public int getSize(){
        return cards.size();
    }

    /**
     * get the list of cards in the deck
     * @return a list of cards
     */
    public ArrayList<Card> getCards(){
        return this.cards;
    }

    /**
     * @return the title of the deck
     */
    public String getTitle(){
        return this.title;
    }

}

