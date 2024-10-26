package notes;


import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class NoteController {

    private NoteModel model;
    private TextArea textArea;

    public NoteController(NoteModel model) {
        this.model = model;
        this.textArea = model.getTextArea();


    }

    protected void onSave() {

    }

    protected void onClose() {
        System.exit(0);
    }

    protected void decrementFontSize() {
        model.setFontSize(model.getFontSize()-1);
        textArea.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, model.getFontSize()));
    }

    protected void incrementFontSize() {
        model.setFontSize(model.getFontSize()+1);
        textArea.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, model.getFontSize()));

    }

    protected void append(String text) {
        model.getTextArea().appendText(text + "\n");
    }


}
