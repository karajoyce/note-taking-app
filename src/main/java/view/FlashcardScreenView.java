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
        this.deck.add(new Card("What is CSPIP? HGFOIJ DIFHGOIUSFOUIFHBGSJB JBOB lJBJFBJB J GSF GSJFBG JSBF LFBGSFB SKJBF", "Computer Science Professional Internship Program"));
        //-------------------------

        // General class things/size
        this.getStyleClass().add("cardview");
        this.setMaxHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        this.setMaxWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
        //-------------------------END

        //-------------------------
        // Main box to hold elements
        HBox fullBox = new HBox();
        this.getChildren().add(fullBox);
        fullBox.getStyleClass().add("bigbox");
        fullBox.setMaxWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()-50);
        fullBox.setMaxHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight()-50);

        //-------------------------END

        //-------------------------
        // Set up flashcard middle section
        VBox cardSection = new VBox();
        cardSection.getStyleClass().add("cardsection");
        fullBox.getChildren().add(cardSection);
        cardSection.setAlignment(Pos.CENTER_LEFT);
        cardSection.maxWidth(fullBox.getWidth()*0.666667);
        cardSection.maxHeight(fullBox.getHeight());

        HBox fCard = new HBox();
        Text frontText = new Text(this.deck.get(0).getCardFront());
        frontText.getStyleClass().add("textflow");
        fCard.getChildren().add(frontText);
        frontText.setTextAlignment(TextAlignment.CENTER);
//        fCard.setMinWidth(500);
//        fCard.setMinHeight(300);
        cardSection.getChildren().add(fCard);


        HBox bottomButtons = new HBox();
        bottomButtons.getStyleClass().add("hbox");
        cardSection.getChildren().add(bottomButtons);
        bottomButtons.setAlignment(Pos.BOTTOM_CENTER);
//        bottomButtons.setMinHeight(200);
//        bottomButtons.setMinWidth(500);


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
        fullBox.getChildren().add(todolist);
        todolist.getStyleClass().add("fake");
        todolist.setAlignment(Pos.TOP_RIGHT);
        Text todoL = new Text("Pick up kids.");
        todolist.getChildren().add(todoL);
        todolist.maxWidth(fullBox.getWidth()*333333);
        todolist.minHeight(fullBox.getHeight());
        //-------------------------END
    }
}
