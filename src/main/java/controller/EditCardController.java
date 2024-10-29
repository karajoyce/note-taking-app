package controller;

import javafx.stage.Stage;
import model.Card;
import model.FlashcardScreen;
import view.EditCardView;

public class EditCardController {

    Card currentCard;
    EditCardView editView;
    FlashcardScreen fModel;
    FlashcardScreenController fControl;

    public EditCardController(Card card, FlashcardScreen model, FlashcardScreenController controller, Stage stage){
        currentCard = card;
        fModel = model;
        fControl = controller;
        editView = new EditCardView(stage, this::addCard);
        stage.show();
    }

    public void addCard(){
        String frontCard = editView.getFrontDescription();
        String backCard = editView.getBackDescription();
        // todo
    }
}
