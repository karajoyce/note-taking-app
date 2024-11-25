package com.example.demo.view;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**

 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Author: Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521

 **/

public class MotivationalMessagesView extends StackPane {

    private List<String> messages; // hold the messages
    private Random rand;
    private HBox motivmsg;


    /**
     * Constructor for the view of motivational messages
     */
    public MotivationalMessagesView(){
        rand = new Random();
        motivmsg = new HBox();

        // file found at https://gist.github.com/robatron/a66acc0eed3835119817#file-quotes-txt
        String txtInput = readString("/quotes.txt");
        messages = Arrays.asList(txtInput.split("\n"));
        this.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        drawMotivationalMessage();
    }

    public void drawMotivationalMessage(){

        // set up layout
        motivmsg.setPadding(new javafx.geometry.Insets(10));
        motivmsg.setMinHeight(325);
        motivmsg.setMaxHeight(325);
        motivmsg.setMinWidth(375);
        motivmsg.setMaxWidth(375);
        motivmsg.getStyleClass().add("motivmsg");
        motivmsg.setAlignment(Pos.CENTER);

        Text display = new Text(messages.get(rand.nextInt(94)));
        display.setWrappingWidth(motivmsg.getMinWidth()-30);
        display.setTextAlignment(TextAlignment.CENTER);
        motivmsg.getChildren().add(display);

        this.getChildren().add(motivmsg);
    }

    /**
     This code allows text files to be read when they are inside a jar.
     */
    public String readString(String path) {
        StringBuilder stringBuilder = new StringBuilder();
        InputStream stream = this.getClass().getResourceAsStream(path);
        if(stream == null) {
            System.out.println("Cant read file: " + path);
            return "";
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        reader.lines().forEach(string -> stringBuilder.append(string).append("\n"));
        return stringBuilder.toString();
    }

    public HBox getMotivmsgView(){
        return motivmsg;
    }

}



