package com.example.demo.model;

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

    public Deck(String name){
        this.title = name;
        cards = new ArrayList<>();
    }

    public void addCard(Card newCard){
        cards.add(newCard);
    }

    public void removeCard(Card oldCard){
        this.cards.remove(oldCard);
    }

    public int getSize(){
        return cards.size();
    }

    public ArrayList<Card> getCards(){
        return this.cards;
    }

}
