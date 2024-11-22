package com.example.demo.model;

/**

CMPT 370, T05, Team 4, Prof. Jon Lovering
Author: Kara Leier, kjl061, 11293306
Nathan Balilis, ncb421, 11295020
Trushank Lakdawala, nus429, 11350445
Jinny Kim, yek738, 11304174
Sara Shakeel, gvk731, 11367521

 **/

public class Card {

    // The front of the flashcard, either a question or a word to define, for example.
    private String CardFront;

    // The back of the flashcard, the answer to the front.
    private String CardBack;
    // Private confidence level selected by user, default as 0
    private boolean confidenceLevel;

    /**
     * @param front: the front of the flashcard
     * @param back: the back of the flashcard
     * postcond: confidence level default is false
     */
    public Card(String front, String back) {
        this.CardFront = front;
        this.CardBack = back;
        this.confidenceLevel = false;
    }

    /**
     * @return String, the front of the card
     */
    public String getCardFront() {
        return this.CardFront;
    }

    /**
     * @param cardFront: String, the front of the card to be applied.
     */
    public void setCardFront(String cardFront) {
        CardFront = cardFront;
    }

    /**
     * @return String, the back of the card
     */
    public String getCardBack() {
        return this.CardBack;
    }

    /**
     * @param cardBack: String, the back of the card to be applied
     */
    public void setCardBack(String cardBack) {
        CardBack = cardBack;
    }

    /**
     *
     * @return confidence level
     */
    public boolean getConfidenceLevel(){
        return confidenceLevel;
    }

    /**
     * @param conf confidence to set
     */
    public void setConfidenceLevel(boolean conf) {
        this.confidenceLevel = conf;
    }

    /**
     * @return String, a string example of the instance of this card.
     */
    public String toString(){
        return "Card - Front: " + this.CardFront + ". Back: " + this.CardBack;
    }
}
