package controller;

import javafx.stage.Stage;
import model.Card;
import view.EditCardView;

public class EditCardController {

    Card currentCard;

    public EditCardController(Card card, EditCardView view, Stage stage){
        currentCard = card;
    }
}
