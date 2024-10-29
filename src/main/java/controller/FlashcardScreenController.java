package controller;

/*
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 */

import javafx.stage.Stage;
import model.FlashcardScreen;
import view.EditCardView;
import view.FlashcardScreenView;

public class FlashcardScreenController {

    private FlashcardScreen fCardModel; // The model that represents the cards/deck
    private FlashcardScreenView fCardView; // The view of the screen


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
            fCardView.runDeckUpdate();
        });

        fCardView.setNextCardButton(e -> {
            fCardView.setCurrentCard(true);
            if (fCardView.checkBack()){
                fCardView.flipIsBack();
            }
            fCardView.runDeckUpdate();
        });

        fCardView.setBackCardButton(e -> {
            fCardView.setCurrentCard(false);
            if (fCardView.checkBack()){
                fCardView.flipIsBack();
            }
            fCardView.runDeckUpdate();
        });

        fCardView.setEditCardButton(e -> {
            new EditCardController(fCardView.getCurrentCard(), fCardModel, this, new Stage());
            fCardView.runDeckUpdate();
        });
    }

}
