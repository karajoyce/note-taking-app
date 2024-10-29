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
import java.util.Collections;
import java.util.HashSet;

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

    /**
     * On the event of clicking the Bold button, if:
     * (1) User has selected a chunk of text, toggle bold but retain previous set styles.
     * (2) User has not selected text, toggle bold for the subsequent times they type until
     * it is toggled again.
     */
    protected void toggleBold() {
        /* Get the user's selected text */
        int start = noteModel.getTextArea().getSelection().getStart();
        int end = noteModel.getTextArea().getSelection().getEnd();

        boolean currentlySetToBold = noteModel.isBoldEnabled();

        /* If user is selecting a block of text, retain the other styles/formatting but
         * toggle the desired style */
        if (start != end) {

            for (int i = start; i < end; i++) {
                Collection<String> styles = new HashSet<String>(Collections.singleton(noteModel.getTextArea().getStyleOfChar(i)));

                if (currentlySetToBold) {
                    styles.add("-fx-font-weight: normal;");
                } else {
                    styles.add("-fx-font-weight: bold;");
                }

                styles = retainStyles(styles, "-fx-underline: true;");
                styles = retainStyles(styles, "-fx-font-style: italic;");

                noteModel.getTextArea().setStyle(i, i+1, String.join(" ", styles));
            }
            noteModel.toggleBold();
            return;

        }

        /* Toggles whether or not the next characters that the user types
        is bold or not
         */

        noteModel.toggleBold();

        if (noteModel.getCurrStyle().contains("-fx-font-weight: bold;")) {
            noteModel.getCurrStyle().remove("-fx-font-weight: bold;");
        } else {
            noteModel.getCurrStyle().add("-fx-font-weight: bold;");
        }
    }

    /**
     * On the event of clicking the Italic button, if:
     * (1) User has selected a chunk of text, toggle italics but retain previous set styles.
     * (2) User has not selected text, toggle italics for the subsequent times they type until
     * it is toggled again.
     */
    protected void toggleItalic() {
        /* Get the user's selected text */
        int start = noteModel.getTextArea().getSelection().getStart();
        int end = noteModel.getTextArea().getSelection().getEnd();

        boolean currentlySetToItalic = noteModel.isItalicEnabled();

        /* If user is selecting a block of text, retain the other styles/formatting but
         * toggle the desired style */
        if (start != end) {

            for (int i = start; i < end; i++) {
                Collection<String> styles = new HashSet<String>(Collections.singleton(noteModel.getTextArea().getStyleOfChar(i)));

                if (currentlySetToItalic) {
                    styles.add("-fx-font-style: normal;");
                } else {
                    styles.add("-fx-font-style: italic;");
                }

                styles = retainStyles(styles, "-fx-font-weight: bold;");
                styles = retainStyles(styles, "-fx-underline: true;");

                noteModel.getTextArea().setStyle(i, i+1, String.join(" ", styles));
            }
            noteModel.toggleItalic();
            return;

        }


        /* Toggles whether or not the next characters that the user types
        is italic or not
         */
        noteModel.toggleItalic();
        if (noteModel.getCurrStyle().contains("-fx-font-style: italic;")) {
            noteModel.getCurrStyle().remove("-fx-font-style: italic;");
        } else {
            noteModel.getCurrStyle().add("-fx-font-style: italic;");
        }
    }

    /**
     * On the event of clicking the Underline button, if:
     * (1) User has selected a chunk of text, toggle underline but retain previous set styles.
     * (2) User has not selected text, toggle underline for the subsequent times they type until
     * it is toggled again.
     */
    protected void toggleUnderline() {
        /* Get the user's selected text */
        int start = noteModel.getTextArea().getSelection().getStart();
        int end = noteModel.getTextArea().getSelection().getEnd();

        boolean currentlySetToUnderlined = noteModel.isUnderlineEnabled();

        /* If user is selecting a block of text, retain the other styles/formatting but
        * toggle the desired style */
        if (start != end) {

            for (int i = start; i < end; i++) {
                Collection<String> styles = new HashSet<String>(Collections.singleton(noteModel.getTextArea().getStyleOfChar(i)));

                if (currentlySetToUnderlined) {
                    styles.add("-fx-underline: false;");
                } else {
                    styles.add("-fx-underline: true;");
                }

                styles = retainStyles(styles, "-fx-font-weight: bold;");
                styles = retainStyles(styles, "-fx-font-style: italic;");

                noteModel.getTextArea().setStyle(i, i+1, String.join(" ", styles));
            }
            noteModel.toggleUnderline();
            return;

        }

        /* If the user has not selected text, they will change the formatting for the
        next character they type
         */
        noteModel.toggleUnderline();

        if (noteModel.getCurrStyle().contains("-fx-underline: true;")) {
            noteModel.getCurrStyle().remove("-fx-underline: true;");
        } else {
            noteModel.getCurrStyle().add("-fx-underline: true;");
        }
    }

    /**
     * Helper function that reduces redundant code when toggling styles/formatting.
     * Adds styles we want to keep/retain into a list to return
     * @param styleList list of styles we want to apply to a character
     * @param style string of CSS formatting we're searching for
     * @return modified styleList
     */
    private Collection<String> retainStyles(Collection<String> styleList, String style) {
        if (styleList.contains(style)) {
            styleList.add(style);
        } else {
            styleList.remove(style);
        }

        return styleList;

    }

    /**
     * Function that applies the current applied styles/formatting to the text the user types
     */
    protected void applyCurrentStyleToNewText() {
        int caretPosition = noteModel.getTextArea().getCaretPosition();
        noteModel.getTextArea().setStyle(caretPosition - 1, caretPosition, String.join(" ", noteModel.getCurrStyle()));
    }

}