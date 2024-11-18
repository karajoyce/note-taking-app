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
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.fxmisc.richtext.InlineCssTextArea;

/**
 * Runs the application of the text editor
 */
public class NoteMain extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        /* Initialize (MVC) */
        NoteModel model = new NoteModel();
        NoteController controller = new NoteController(model);
        NoteView view = new NoteView(controller);

        InlineCssTextArea textArea = model.getTextArea();

        MenuBar menuBar = view.createMenuBar(stage);
        ToolBar toolBar = view.createToolBar();

        BorderPane root = new BorderPane();
        root.setTop(new HBox(menuBar, toolBar));
        root.setCenter(new ScrollPane(textArea));

        Scene scene = new Scene(root, 800, 800);

        scene.getStylesheets().add(getClass().getResource("/noteStyle.css").toExternalForm());

        stage.setTitle("NoteMain");
        stage.setScene(scene);
        stage.show();


    }
}
