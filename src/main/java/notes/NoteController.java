/**
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 */

package notes;


import javafx.scene.control.Alert;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;

/**
 * The Controller class of the text editor (MVC Model)
 * Has all the functions to be called in response to action events.
 */
public class NoteController {

    NoteModel noteModel;

    public NoteController(NoteModel model) {
        noteModel = model;

    }

    /** error message when doing file stuff
     *
     * @param title title of the alert
     * @param message message displayed to user
     */
    private void displayError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /** Open a file from the computer's filesystem */
    protected void openFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Text File");
        File file = fileChooser.showOpenDialog(stage);

        try {
            if (file != null) {
                String content = Files.readString(Path.of(file.getPath()));
                noteModel.getTextArea().replaceText(content);
            }
        } catch (IOException e) {
            displayError("Error opening file", e.getMessage());
        }
    }

    /** Save a document */
    protected void saveFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Text File");
        File file = fileChooser.showSaveDialog(stage);

        /* Attempt to save the file */
        if (file != null) {
            try {
                Files.writeString(Path.of(file.getPath()), noteModel.getTextArea().getText(), StandardOpenOption.CREATE);
            } catch (IOException e) {
                displayError("Error saving file", e.getMessage());
            }
        }
    }

    /** Rich text editing toggles for styling .
     * e.g. bold, underline, italics.
     * @param styleClass a style (e.g. bold)
     */
    protected void toggleStyle(String styleClass) {

        /* Get the user's selected text */
        int start = noteModel.getTextArea().getSelection().getStart();
        int end = noteModel.getTextArea().getSelection().getEnd();

        if (start == end) {
            return; /* Nothing to do. exit! */
        }

        /* Detect the style within the selected text */
        Collection<String> styles = noteModel.getTextArea().getStyleAtPosition(start);
        if (styles.contains(styleClass)) {
            noteModel.getTextArea().setStyleClass(start, end, ""); /* Disable style if enabled already */
        } else {
            noteModel.getTextArea().setStyleClass(start, end, styleClass); /* Enable style if disabled */
        }
    }


}
