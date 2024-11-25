package com.example.demo.view;

import com.example.demo.HelloApplication;
import com.example.demo.controller.ToDoListController;
import com.example.demo.model.DigitalTree;
import com.example.demo.model.XPModel;
import com.example.demo.model.ToDoList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import org.fxmisc.richtext.InlineCssTextArea;

import java.awt.*;

public class MainMenuScreenView extends StackPane {
    double screenHeight;
    double screenWidth;

    private ToDoListView toDoListV;
    private ToDoListController toDoCont;
    private ToDoList toDoList;
    private Button pageButton; // button to choose a deck
    private MotivationalMessagesView mView;

    private XPModel xpModel;


    public MainMenuScreenView(){
        // Deck initialization, needs to change
        screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight()-100;
        screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth()-100;
        pageButton = new Button("Test Page"); // this should be a deck name later

        toDoListV = new ToDoListView();
        toDoList = new ToDoList();
        toDoCont = new ToDoListController(toDoList, toDoListV, xpModel);

        mView = new MotivationalMessagesView();

        runMainScreenUpdate();
    }

    public void runMainScreenUpdate(){
        // General class things/size
        this.getStylesheets().add("/styles.css");
        double screenHeight = Screen.getPrimary().getBounds().getMaxY()-100;
        double screenWidth = Screen.getPrimary().getBounds().getMaxX()-100;
        this.getChildren().clear();
        //-------------------------END

        //-------------------------
        // Main box to hold elements
        HBox fullBox = new HBox();
        fullBox.getStyleClass().add("bigbox");
        fullBox.setMaxWidth(screenWidth);
        fullBox.setMaxHeight(screenHeight);
        //-------------------------END

        //-------------------------
        // Set up flashcard middle section
        VBox cardSection = new VBox();
        cardSection.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        cardSection.getStyleClass().add("cardsection");
        cardSection.setAlignment(Pos.CENTER_LEFT);
        cardSection.setMinWidth((screenWidth*0.7)-10);
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


        //-------------------------
        VBox todolist = new VBox();
        todolist.setMinWidth(screenWidth*0.3);
        todolist.setAlignment(Pos.TOP_CENTER);
        todolist.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        todolist.getStyleClass().add("rightVbox");

        todolist.getChildren().addAll(mView.getMotivmsgView(), toDoListV.getToDoListView());
        fullBox.getChildren().add(todolist);
        this.getChildren().add(fullBox);
    }

}
