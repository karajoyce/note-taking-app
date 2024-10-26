package notes;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;


public class NoteView {

    private BorderPane panel;

    private Button saveButton;
    private Button closeButton;
    private Button decrementFontSizeButton;
    private Button incrementFontSizeButton;

    public NoteView(NoteModel model, NoteController controller) {
        panel = new BorderPane();
        TextArea textArea = model.getTextArea();

        // Default values
        textArea.setPrefHeight(400);
        textArea.setPrefWidth(500);
        textArea.setText(model.getText());
        textArea.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, model.getFontSize()));

        saveButton = new Button("Save");
        closeButton = new Button("Close");

        decrementFontSizeButton = new Button("Smaller");
        incrementFontSizeButton = new Button("Larger");

        HBox buttonBox = new HBox();

        buttonBox.getChildren().addAll(saveButton, closeButton, incrementFontSizeButton, decrementFontSizeButton);

        panel.setTop(buttonBox);
        panel.setCenter(textArea);

        saveButton.setOnAction(actionEvent -> controller.onSave());
        closeButton.setOnAction(actionEvent -> controller.onClose());
        incrementFontSizeButton.setOnAction(actionEvent -> controller.incrementFontSize());
        decrementFontSizeButton.setOnAction(actionEvent -> controller.decrementFontSize());


    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getCloseButton() {
        return closeButton;
    }

    public Button getDecrementFontSizeButton() {
        return decrementFontSizeButton;
    }

    public Button getIncrementFontSizeButton() {
        return incrementFontSizeButton;
    }

    public BorderPane getPanel() {
        return panel;
    }




}
