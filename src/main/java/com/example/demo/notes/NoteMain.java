/**
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 */

package com.example.demo.notes;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.fxmisc.richtext.InlineCssTextArea;

import java.awt.geom.Rectangle2D;

/**
 * Runs the application of the text editor
 */
public class NoteMain extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        /* Getting the screen's layout */
        double screenWidth = Screen.getPrimary().getBounds().getMaxX();
        double screenHeight = Screen.getPrimary().getBounds().getMaxY();

        /* Initialize (MVC) */
        NoteModel model = new NoteModel();
        NoteController controller = new NoteController(model);
        NoteView view = new NoteView(controller);

        InlineCssTextArea textArea = model.getTextArea();

        MenuBar menuBar = view.createMenuBar(stage);
        ToolBar toolBar = view.createToolBar(stage);

        ScrollPane scrollPane = new ScrollPane(textArea);
        scrollPane.setPrefHeight(screenHeight);

        BorderPane root = new BorderPane();
        root.setTop(new HBox(menuBar, toolBar));
        root.setCenter(scrollPane);

        Scene scene = new Scene(root, screenWidth, screenHeight);

        scene.getStylesheets().add(getClass().getResource("/noteStyle.css").toExternalForm());

        /* TO DO :
        - CENTER THE TEXT AREA!!!
         */

        //scrollPane.getStyleClass().add("text-area");


        /* Add shortcuts for toggling font formatting ! */
        scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.B, KeyCombination.SHORTCUT_DOWN),
                new Runnable() {
                    @Override
                    public void run() {
                        controller.toggleBold();
                    }
                }
        );

        scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.I, KeyCombination.SHORTCUT_DOWN),
                new Runnable() {
                    @Override
                    public void run() {
                        controller.toggleItalic();
                    }
                }
        );

        scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.U, KeyCombination.SHORTCUT_DOWN),
                new Runnable() {
                    @Override
                    public void run() {
                        controller.toggleUnderline();
                    }
                }
        );

        scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN),
                new Runnable() {
                    @Override
                    public void run() {
                        controller.toggleStrikethrough();
                    }
                }
        );

        /* Auto flash card listener */

        model.getTextArea().textProperty().addListener((obs, oldText, newText) -> {
            controller.trackBack(oldText, newText);
        });


        stage.setTitle("NoteMain");
        stage.setScene(scene);
        stage.show();


    }
}
