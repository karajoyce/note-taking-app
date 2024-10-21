package notes;


import javafx.scene.control.TextArea;

public class NoteController {

    private TextArea textArea;
    private NoteModel model;

    public NoteController(NoteModel model) {
        this.model = model;

    }

    protected void onSave() {

    }

    protected void onClose() {
        System.exit(0);
    }

    protected void decrementFontSize(double change) {
        model.setFontSize(model.getFontSize()-change);
    }

    protected void incrementFontSize(double change) {
        model.setFontSize(model.getFontSize()+change);

    }

    protected void append(String text) {
        textArea.appendText(text + "\n");
    }


}
