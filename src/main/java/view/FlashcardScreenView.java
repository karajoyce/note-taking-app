package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Card;
import java.util.ArrayList;
import java.awt.*;

/**

 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Author: Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521

 **/

public class FlashcardScreenView extends StackPane {

    private ArrayList<Card> deck = new ArrayList<>();

    public FlashcardScreenView() {

        //-------------------------
        // Deck initialization, needs to change
        this.deck.add(new Card("How much wood could a wood chuck chuck if a wood chuck could chuck wood?", "A wood chuck could chuck all the wood if a wood chuck could chuck wood."));
        double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight()-100;
        double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth()-100;

        // General class things/size
        this.getStyleClass().add("cardview");
        //-------------------------END

        //-------------------------
        // Main box to hold elements
        HBox fullBox = new HBox();
        this.getChildren().add(fullBox);
        fullBox.getStyleClass().add("bigbox");
        fullBox.setMaxWidth(screenWidth);
        fullBox.setMaxHeight(screenHeight);

        //-------------------------END

        //-------------------------
        // Set up flashcard middle section
        VBox cardSection = new VBox();
        cardSection.getStyleClass().add("cardsection");
        cardSection.setAlignment(Pos.CENTER_LEFT);
        cardSection.setMinWidth(screenWidth*0.666667);
        cardSection.setMinHeight(screenHeight);
        fullBox.getChildren().add(cardSection);

        HBox fCard = new HBox();
        fCard.setMinHeight(cardSection.getMinHeight()*(5.0/6.0));
        Text frontText = new Text(this.deck.get(0).getCardFront());
        frontText.getStyleClass().add("textflow");
        fCard.getChildren().add(frontText);
        frontText.setTextAlignment(TextAlignment.CENTER);
        fCard.setAlignment(Pos.CENTER);
        cardSection.getChildren().add(fCard);


        HBox bottomButtons = new HBox();
        bottomButtons.setMinHeight(cardSection.getMinHeight()*(1.0/6.0));
        bottomButtons.getStyleClass().add("hbox");
        cardSection.getChildren().add(bottomButtons);
        bottomButtons.setAlignment(Pos.BOTTOM_CENTER);


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

        bottomButtons.getChildren().add(back);
        bottomButtons.getChildren().add(flip);
        bottomButtons.getChildren().add(next);
        //-------------------------END

        //-------------------------

        VBox todolist = new VBox();
        todolist.getStyleClass().add("fake");
        todolist.setAlignment(Pos.TOP_RIGHT);
        Text todoL = new Text("Pick up kids.");
        //todolist.maxWidth(screenWidth*0.333333);
        todolist.setMinWidth(screenWidth*0.333333);
        todolist.setMinHeight(screenHeight);
        todolist.getChildren().add(todoL);
        fullBox.getChildren().add(todolist);
        //-------------------------END
    }
}
