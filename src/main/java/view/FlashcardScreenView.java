package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import model.Card;
import model.FlashcardScreen;
import java.util.ArrayList;

/**

 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Author: Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521

 **/

public class FlashcardScreenView extends StackPane {

    private boolean isBack = false;
    private FlashcardScreen flashcardModel = new FlashcardScreen();
    private ArrayList<Card> deck = flashcardModel.getDeck();

    public FlashcardScreenView() {

        //-------------------------
        // Deck initialization, needs to change
        flashcardModel.addCard("How much wood could a wood chuck chuck if a wood chuck could chuck wood. Would the wood chuck chuck the wood or would he choose to chuck not the wood?", "A wood chuck could chuck all the wood if a wood chuck could chuck wood.");
//        double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight()-100;
        double screenHeight = Screen.getPrimary().getBounds().getMaxY()-100;
        double screenWidth = Screen.getPrimary().getBounds().getMaxX()-100;
//        double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth()-100;

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
        // Deck selection pane
        VBox deckSelection = new VBox();
        deckSelection.getStyleClass().add("deck");
        deckSelection.setAlignment(Pos.TOP_CENTER);
        deckSelection.setMinWidth(screenWidth*0.15);
        deckSelection.setMinHeight(screenHeight);
        fullBox.getChildren().add(deckSelection);

        // Buttons for decks
        Button deckButton = new Button("Test Desk"); // this should be a deck name later
        deckButton.setAlignment(Pos.CENTER);
        deckButton.setMinWidth(deckSelection.getMinWidth()-40);
        deckButton.setMinHeight(80);
        deckSelection.getChildren().add(deckButton);
        //-------------------------END

        //-------------------------
        // Set up flashcard middle section
        VBox cardSection = new VBox();
        cardSection.getStyleClass().add("cardsection");
        cardSection.setAlignment(Pos.CENTER_LEFT);
        cardSection.setMinWidth(screenWidth*0.6);
        cardSection.setMinHeight(screenHeight);
        fullBox.getChildren().add(cardSection);


        //Card text setup
        HBox fCard = new HBox();
        fCard.setMinHeight(cardSection.getMinHeight()*(6.0/8.0));
        fCard.getStyleClass().add("textflow");
        fCard.setAlignment(Pos.CENTER);

        // Back button
        HBox topButtons = new HBox();
        topButtons.setMinHeight(cardSection.getMinHeight()*(1.0/8.0));
        topButtons.getStyleClass().add("hbox");
        cardSection.getChildren().add(topButtons);

        VBox leftBar = new VBox();
        leftBar.setMinHeight(topButtons.getMinHeight()-18);
        leftBar.setMinWidth((cardSection.getMinWidth()-50)/2);
        leftBar.getStyleClass().add("topbar");
        Button pageBack = new Button(" <-- ");
        pageBack.setMinHeight(50);
        pageBack.setMinWidth(80);
        pageBack.setAlignment(Pos.CENTER);
        leftBar.setAlignment(Pos.CENTER_LEFT);
        leftBar.getChildren().add(pageBack);
        topButtons.getChildren().add(leftBar);

        VBox rightBar = new VBox();
        rightBar.setMinHeight(topButtons.getMinHeight()-18);
        rightBar.setMinWidth((cardSection.getMinWidth()-50)/2);
        rightBar.getStyleClass().add("topbar");
        Button removeCard = new Button(" X ");
        removeCard.setMinHeight(50);
        removeCard.setMinWidth(80);
        removeCard.setAlignment(Pos.CENTER);
        rightBar.setAlignment(Pos.CENTER_RIGHT);
        rightBar.getChildren().add(removeCard);
        topButtons.getChildren().add(rightBar);

        if (!isBack) {
            Text frontText = new Text(this.deck.get(0).getCardFront());
            frontText.setWrappingWidth(cardSection.getMinWidth() - 40);
            fCard.getChildren().add(frontText);
            frontText.setTextAlignment(TextAlignment.CENTER);
        } else {
            Text backText = new Text(this.deck.get(0).getCardBack());
            backText.setWrappingWidth(cardSection.getMinHeight() - 40);
            fCard.getChildren().add(backText);
            backText.setTextAlignment(TextAlignment.CENTER);
        }
        cardSection.getChildren().add(fCard);

        // Buttons for card control
        HBox bottomButtons = new HBox();
        bottomButtons.setMinHeight(cardSection.getMinHeight()*(1.0/8.0));
        bottomButtons.getStyleClass().add("hbox");
        cardSection.getChildren().add(bottomButtons);
        bottomButtons.setAlignment(Pos.CENTER);


        // Bottom Buttons set up
        Button next = new Button(" > ");
        Button back = new Button(" < ");
        Button flip = new Button(" ⭯ ");
        Button edit = new Button(" ✎ ");

        next.setMaxWidth(80);
        next.setMinWidth(80);
        back.setMaxWidth(80);
        back.setMinWidth(80);
        flip.setMaxWidth(80);
        flip.setMinWidth(80);
        edit.setMinWidth(80);

        next.setMaxHeight(50);
        next.setMinHeight(50);
        back.setMaxHeight(50);
        back.setMinHeight(50);
        flip.setMaxHeight(50);
        flip.setMinHeight(50);
        edit.setMinHeight(50);

        bottomButtons.getChildren().add(back);
        bottomButtons.getChildren().add(flip);
        bottomButtons.getChildren().add(edit);
        bottomButtons.getChildren().add(next);
        //-------------------------END

        //-------------------------
        VBox todolist = new VBox();
        todolist.getStyleClass().add("fake");
        todolist.setAlignment(Pos.TOP_LEFT);
        Text todoL = new Text("Pick up kids     Oct 14: 10AM");
        todolist.setMinWidth((screenWidth*0.25));
        todolist.setMinHeight(screenHeight);
        todolist.getChildren().add(todoL);
        fullBox.getChildren().add(todolist);
        //-------------------------END
    }

    public void updateDeckList(Card newCard){
        this.deck.add(newCard);
    }
}
