package flashcard;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class CardView extends StackPane {

    private Card myCard;

    public CardView(){
        Button next = new Button("Next");
        next.setMaxWidth(50);
        next.setMaxHeight(30);
        this.getChildren().add(next);
        this.setMinWidth(500);
        this.setMinHeight(500);
    }

}
