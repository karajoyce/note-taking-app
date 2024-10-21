package notes;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class NoteView {

    private TextArea textArea;
    private BorderPane panel;

    public NoteView(NoteModel model, NoteController controller) {
        panel = new BorderPane();
        textArea = new TextArea();

        textArea.setPrefHeight(400);
        textArea.setPrefWidth(500);
        textArea.setText(model.getText());

        Button saveButton = new Button("Save");
        Button closeButton = new Button("Close");

        Button incrementFontSizeButton = new Button("Smaller");
        Button decrementFontSizeButton = new Button("Larger");

        VBox buttonBox = new VBox();

        buttonBox.getChildren().addAll(saveButton, closeButton, incrementFontSizeButton, decrementFontSizeButton);

        panel.setTop(buttonBox);
        panel.setCenter(textArea);

        saveButton.setOnAction(actionEvent -> controller.onSave());
        closeButton.setOnAction(actionEvent -> controller.onClose());
        incrementFontSizeButton.setOnAction(actionEvent -> controller.incrementFontSize(1));
        decrementFontSizeButton.setOnAction(actionEvent -> controller.decrementFontSize(1));





    }

    public BorderPane getPanel() {
        return panel;
    }




}
