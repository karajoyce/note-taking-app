package flashcard;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;


public class CardView extends StackPane {

    private Card myCard;

    public CardView(){
        //-------------------------
        //Initialize Vars
        myCard = new Card("What is CSPIP?", "Computer Science Professional Internship Program");
        //-------------------------

        // ------------------------
        // General class things/size
        this.getStyleClass().add("cardview");
        this.setMinWidth(500);
        this.setMinHeight(500);
        //-------------------------END

        //-------------------------
        // Set up flashcard middle section
        BorderPane cardSection = new BorderPane();
        cardSection.getStyleClass().add("cardsection");
        this.getChildren().add(cardSection);
        cardSection.centerProperty();
//        Label front = new Label(myCard.getCardFront());
        Text frontText = new Text(myCard.getCardFront());
        TextFlow front = new TextFlow(frontText);
        front.getStyleClass().add("textflow");
        front.setTextAlignment(TextAlignment.CENTER);
        cardSection.setCenter(front);
        //-------------------------END

        //-------------------------
        // Bottom Buttons set up
        Button next = new Button(" > ");
        Button back = new Button(" < ");
        Button flip = new Button(" ⭯ ");

        next.setMaxWidth(60);
        next.setMinWidth(60);
        back.setMaxWidth(60);
        back.setMinWidth(60);
        flip.setMaxWidth(60);
        flip.setMinWidth(60);

        next.setMaxHeight(45);
        next.setMinHeight(45);
        back.setMaxHeight(45);
        back.setMinHeight(45);
        flip.setMaxHeight(45);
        flip.setMinHeight(45);

        HBox bottomButtons = new HBox();
        bottomButtons.getStyleClass().add("hbox");
        this.getChildren().add(bottomButtons);
        bottomButtons.setAlignment(Pos.BOTTOM_CENTER);

        bottomButtons.getChildren().add(back);
        bottomButtons.getChildren().add(flip);
        bottomButtons.getChildren().add(next);
        //-------------------------END
    }

}
