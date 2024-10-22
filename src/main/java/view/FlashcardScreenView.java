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
        this.setMinWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()-100);
        this.setMinHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight()-100);
        //-------------------------END

        //-------------------------
        // Set up flashcard middle section
        HBox cardSection = new HBox();
        cardSection.getStyleClass().add("cardsection");
        this.getChildren().add(cardSection);
        cardSection.setAlignment(Pos.CENTER);
        Text frontText = new Text(this.deck.get(0).getCardFront());
        frontText.getStyleClass().add("textflow");
        cardSection.getChildren().add(frontText);
        frontText.setTextAlignment(TextAlignment.CENTER);
        cardSection.maxWidth(500);
        cardSection.maxHeight(500);
        //-------------------------END

        //-------------------------
        // Fake things until I can connect the real things
        VBox motiv = new VBox();
        this.getChildren().add(motiv);
        motiv.getStyleClass().add("fake");
        motiv.setAlignment(Pos.TOP_RIGHT);
        Text motivM = new Text("You miss 100% of the shots you don't take!");
        motiv.getChildren().add(motivM);
        motiv.maxWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3);
        motiv.maxHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/3);

        VBox todolist = new VBox();
        this.getChildren().add(todolist);
        todolist.getStyleClass().add("fake");
        todolist.setAlignment(Pos.BOTTOM_RIGHT);
        Text todoL = new Text("Pick up kids.");
        todolist.getChildren().add(todoL);
        todolist.maxWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3);
        todolist.maxHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/3 *2);


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
