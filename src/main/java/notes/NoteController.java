package notes;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class NoteController {

    @FXML
    private TextArea areaText;
    private NoteModel model;

    public NoteController(NoteModel model) {
        this.model = model;
    }

    @FXML
    private void onSave() {

    }

    @FXML
    private void onLoad() {

    }

    @FXML
    private void onClose() {
        System.exit(0);
    }

    @FXML
    private void onAbout() {

    }
}
