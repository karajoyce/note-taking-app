package flashcard;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;



public class CardView extends StackPane {

    private Card myCard;

    public CardView(){
        Button next = new Button("Next");
        next.setMaxWidth(50);
        next.setMaxHeight(30);
        this.getStyleClass().add("cardview");
        this.getChildren().add(next);
        this.setMinWidth(500);
        this.setMinHeight(500);
    }

}
