/**
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 */

package notes;

import java.util.*;
import javafx.scene.control.Alert;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
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

            retainStyles("-fx-font-weight: bold; ", "-fx-font-weight: normal; ", currentlySetToBold, start, end);
            noteModel.toggleBold();
            return;

        }

        /* Toggles whether or not the next characters that the user types
        is bold or not
         */
        noteModel.toggleBold();
        if (noteModel.getCurrStyle().contains("-fx-font-weight: bold; ")) {
            noteModel.getCurrStyle().remove("-fx-font-weight: bold; ");
            noteModel.getCurrStyle().add("-fx-font-weight: normal; ");
        } else {
            noteModel.getCurrStyle().remove("-fx-font-weight: normal; ");
            noteModel.getCurrStyle().add("-fx-font-weight: bold; ");
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

            retainStyles("-fx-font-style: italic; ", "-fx-font-style: normal; ", currentlySetToItalic, start, end);
            noteModel.toggleItalic();
            return;

        }

        /* Toggles whether or not the next characters that the user types
        is italic or not
         */
        noteModel.toggleItalic();
        if (noteModel.getCurrStyle().contains("-fx-font-style: italic; ")) {
            noteModel.getCurrStyle().remove("-fx-font-style: italic; ");
            noteModel.getCurrStyle().add("-fx-font-style: normal; ");
        } else {
            noteModel.getCurrStyle().remove("-fx-font-style: normal; ");
            noteModel.getCurrStyle().add("-fx-font-style: italic; ");
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

            retainStyles("-fx-underline: true; ", "-fx-underline: false; ", currentlySetToUnderlined, start, end);

            noteModel.toggleUnderline();
            return;

        }

        /* If the user has not selected text, they will change the formatting for the
        next character they type
         */
        noteModel.toggleUnderline();

        if (noteModel.getCurrStyle().contains("-fx-underline: true; ")) {
            noteModel.getCurrStyle().remove("-fx-underline: true; ");
            noteModel.getCurrStyle().add("-fx-underline: false; ");
        } else {
            noteModel.getCurrStyle().remove("-fx-underline: false; ");
            noteModel.getCurrStyle().add("-fx-underline: true; ");
        }
    }

    /**
     * Helper function that reduces redundant code when toggling styles/formatting.
     * Adds styles we want to keep/retain into a list to return
     * @param style string of CSS formatting we're removing
     * @return modified styleList
     */
    private void retainStyles(String style, String opStyle, boolean state, int start, int end) {

        String[] arrayOfCss;

        for (int i = start; i < end; i++) {
            Set<String> newSet = new HashSet<>();
            /* Split up CSS styles by the semicolon */
            arrayOfCss = noteModel.getTextArea().getStyleOfChar(i).split("[;]");

            /* Add the semicolon back */
            for (int j = 0; j < arrayOfCss.length; j++) {

                /* Add to the new set if it isn't the one we want to remove */
                if (!arrayOfCss[j].startsWith(style)) {
                    newSet.add(arrayOfCss[j].strip() + ";");
                }
                newSet.remove(";"); /* Remove any empty spaces */
            }

            System.out.println(newSet);
            if (state) {
                newSet.add(opStyle);
            } else {
                newSet.add(style);
            }
            noteModel.getTextArea().setStyle(i, i+1, String.join(" ", newSet));

        }
    }

    /* MUST CHANGE CHANGEFONTSIZE */

    /**
     * Change font size according to dropbox menu
     * @param fontSize font size option from dropdown menu
     */
    protected void changeFontSize(String fontSize) {
        /* Get the user's selected text */
        int start = noteModel.getTextArea().getSelection().getStart();
        int end = noteModel.getTextArea().getSelection().getEnd();

        //noteModel.setFontsize(fontSize);

        /* If user is selecting a block of text, retain the other styles/formatting but
         * toggle the desired style */
        if (start != end) {

            for (int i = start; i < end; i++) {
                Collection<String> styles = new HashSet<>(Collections.singleton(noteModel.getTextArea().getStyleOfChar(i)));

                styles.removeIf(style -> style.startsWith("-fx-font-size"));

                styles.add("-fx-font-size: " + fontSize + "; ");

                noteModel.getTextArea().setStyle(i, i + 1, String.join(" ", styles));
            }
            return;

        }
        noteModel.setFontsize(fontSize);

        /* If the user has not selected text, they will change the formatting for the
        next character they type
         */
        noteModel.getCurrStyle().removeIf(style -> style.startsWith("-fx-font-size"));
        noteModel.getCurrStyle().add("-fx-font-size: " + fontSize + "; ");

    }

    /**
     * Function that applies the current applied styles/formatting to the text the user types
     */
    protected void applyCurrentStyleToNewText() {
        int caretPosition = noteModel.getTextArea().getCaretPosition();
        noteModel.getTextArea().setStyle(caretPosition - 1, caretPosition, String.join(" ", noteModel.getCurrStyle()));
    }

}