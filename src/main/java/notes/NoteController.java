package notes;


import javafx.scene.control.TextArea;

public class NoteController {


    private TextArea textArea;
    private NoteModel model;

    public NoteController(NoteModel model) {
        this.model = model;

    }

    private void onSave() {

    }

    private void onLoad() {

    }

    private void onClose() {
        System.exit(0);
    }

    private void onAbout() {

    }
}
