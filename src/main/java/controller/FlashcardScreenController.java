package controller;

/**
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 */

import model.FlashcardScreen;
import view.FlashcardScreenView;

public class FlashcardScreenController {

    private FlashcardScreen fCardModel; // The model that represents the cards/deck
    private FlashcardScreenView fCardView; // The view of the screen


    /**
     * Constructor
     */
    public FlashcardScreenController(){
        fCardModel = new FlashcardScreen();
        fCardView = new FlashcardScreenView();
    }


}
