package notes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class NoteView {

    private TextArea textArea;
    private BorderPane panel;

    public NoteView(NoteModel model) {
        panel = new BorderPane();
        textArea = new TextArea();

        textArea.setPrefHeight(400);
        textArea.setPrefWidth(500);
        textArea.setText(model.getText());


    }


}
