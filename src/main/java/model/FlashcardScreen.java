package model;

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

    ArrayList<Card> deck; // storing the deck of cards
    int cardCounter;

    public FlashcardScreen(){
        // this needs to access database for proper deck based on controller button function
        this.deck = new ArrayList<>();
        this.cardCounter = 1;
    }

    public ArrayList<Card> getDeck(){
        return this.deck;
    }

    public void addCard(String front, String back){
        this.deck.add(new Card(front, back, cardCounter++));
    }

}
