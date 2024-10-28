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

/**
 * The Controller class of the text editor (MVC Model)
 * Has all the functions to be called in response to action events.
 */
public class NoteController {

    NoteModel noteModel;

    public NoteController(NoteModel model) {
        noteModel = model;

        noteModel.getTextArea().textProperty().addListener(((observableValue, s, t1) -> applyCurrentStyleToNewText()));

    }

    /**
     * error message when doing file stuff
     *
     * @param title   title of the alert
     * @param message message displayed to user
     */
    private void displayError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Open a file from the computer's filesystem
     */
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

    /**
     * Save the document you're working on
     */
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

    protected void toggleBold() {
        /* Get the user's selected text */
        int start = noteModel.getTextArea().getSelection().getStart();
        int end = noteModel.getTextArea().getSelection().getEnd();

        noteModel.toggleBold();

        if (start != end) {
            noteModel.getTextArea().setStyle(start, end, convertCss());
            //return;
        }

        /* Toggles whether or not the next characters that the user types
        is bold or not
         */

        if (noteModel.getCurrStyle().contains("-fx-font-weight: bold;")) {
            noteModel.getCurrStyle().remove("-fx-font-weight: bold;");
        } else {
            noteModel.getCurrStyle().add("-fx-font-weight: bold;");
        }
    }

    protected void toggleItalic() {
        /* Get the user's selected text */
        int start = noteModel.getTextArea().getSelection().getStart();
        int end = noteModel.getTextArea().getSelection().getEnd();

        noteModel.toggleItalic();

        if (start != end) {

            noteModel.getTextArea().setStyle(start, end, convertCss());
            //return;
        }


        /* Toggles whether or not the next characters that the user types
        is italic or not
         */
        if (noteModel.getCurrStyle().contains("-fx-font-style: italic;")) {
            noteModel.getCurrStyle().remove("-fx-font-style: italic;");
        } else {
            noteModel.getCurrStyle().add("-fx-font-style: italic;");
        }
    }

    protected void toggleUnderline() {
        /* Get the user's selected text */
        int start = noteModel.getTextArea().getSelection().getStart();
        int end = noteModel.getTextArea().getSelection().getEnd();

        noteModel.toggleUnderline();
        if (start != end) {
            /* Must I really check and change each individual character */
            for (int i = start; i < end; i++) {
                noteModel.getTextArea().setStyle(i, convertCss());
                //return;
            }
        }


        /* Toggles whether or not the next characters that the user types
        is underlined or not
         */
        if (noteModel.getCurrStyle().contains("-fx-underline: true;")) {
            noteModel.getCurrStyle().remove("-fx-underline: true;");
        } else {
            noteModel.getCurrStyle().add("-fx-underline: true;");
        }
    }

    private String convertCss() {
        StringBuilder css = new StringBuilder();

        if (noteModel.isBoldEnabled()) {
            css.append("-fx-font-weight: bold;");
            System.out.println("was bold!");
        } else {
            css.append("-fx-font-weight: normal;");
        }

        if (noteModel.isItalicEnabled()) {
            css.append("-fx-font-style: italic;");
            System.out.println("was italic!");
        } else {
            css.append("-fx-font-style: normal;");
        }

        if (noteModel.isUnderlineEnabled()) {
            css.append("-fx-underline: true");
            System.out.println("was underlined!");
        } else {
            css.append("-fx-underline: false");
        }

        return css.toString();
    }

    protected void applyCurrentStyleToNewText() {
        int caretPosition = noteModel.getTextArea().getCaretPosition();
        noteModel.getTextArea().setStyle(caretPosition - 1, caretPosition, String.join(" ", noteModel.getCurrStyle()));
    }

}