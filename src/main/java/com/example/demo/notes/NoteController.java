/**
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 */

package com.example.demo.notes;

import java.util.*;
import javafx.scene.control.Alert;

import com.example.demo.FilerSystem.FlashcardStorage;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;

import com.example.demo.model.Card;
import com.example.demo.model.Deck;

/**
 * The Controller class of the text editor (MVC Model)
 * Has all the functions to be called in response to action events.
 */
public class NoteController {

    NoteModel noteModel;
    /* !!!!!!! REMOVE AFTER !!!!! JUST TEMPORARY !!!!!! */
    Deck TEMPORARY_DECK = new Deck("TESTING TEMPORARY DECK");

    public NoteController(NoteModel model) {
        noteModel = model;

        /* Set up listeners */
        noteModel.getTextArea().textProperty().addListener(((observableValue, s, t1) -> applyCurrentStyleToNewText()));
        noteModel.getTextArea().textProperty().addListener((obs, oldText, newText) -> {
            trackBack(oldText, newText);
        });

        noteModel.getTextArea().textProperty().addListener((obs, oldText, newText) -> {
            trackFront(oldText, newText);
        });

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
        // Save textarea into a JSON ?

    }

    /** Function that prints out everything in the deck. Debugging purposes ONLY */
    private void printDeck(Deck deck) {
        System.out.println(deck.getCards());
    }

    /** Helper function for knowing where a new character has been inserted */
    private int findDifferenceIndex(String oldText, String newText) {
        int index = 0;
        int maxlen = newText.length();
        while (oldText.toCharArray()[index] == newText.toCharArray()[index]) {
            index++;
            if (index == maxlen - 1) break;
        }

        return index;
    }

    /**
     * When auto flashcards are enabled, listen for the user to input a period (.)
     * @param oldText
     * @param newText
     */
    public void trackBack(String oldText, String newText) {
        StringBuilder backBuffer = noteModel.getBackBuffer();

        /* Only start gathering data for the back of the card if auto flashcard making is
        enabled and if the program is waiting for back input
         */
        if (noteModel.isAutoFlashcardEnabled() && noteModel.isWaitingForBackInput() &&
                !noteModel.isBoldEnabled() && !noteModel.isWaitingforFrontInput()) {

            // User is backspacing. Remove the previous input character from back buffer
            if (newText.length() < oldText.length()) {

                if (backBuffer.length() > 0) {
                    backBuffer.deleteCharAt(noteModel.getTextArea().getCaretPosition() - noteModel.getBackBufferIndex());
                }

            // User is currently typing in new characters ...
            } else {

                int changeIndex = findDifferenceIndex(oldText, newText);
                String addedText = newText.substring(changeIndex);
                if (!addedText.isEmpty()) {
                    char lastChar = addedText.charAt(addedText.length() - 1);

                    // If the user inputs a '.', the back of the card is ready!
                    if (lastChar == '.') {
                        noteModel.setWaitingForBackInput(false);

                        // Create the new flashcard
                        TEMPORARY_DECK.addCard(new Card(noteModel.getCurrentCardFront().toString().strip(),
                                backBuffer.toString().strip()));
                        FlashcardStorage.SaveDeck(TEMPORARY_DECK);

                        // Once the card is made, reset the front and back buffers for the next card to be made
                        noteModel.setCurrentCardFront("");
                        noteModel.resetBackBuffer("");

                        printDeck(TEMPORARY_DECK); // REMOVE LATER

                    } else {
                        // Else, add the text to the buffer
                        noteModel.resetBackBuffer(newText.substring(noteModel.getBackBufferIndex()));
                    }
                }
            }

        }
    }

    /**
     * Dynamically typing the autoflashcard's front
     * @param oldText previous text
     * @param newText text after typing
     */
    public void trackFront(String oldText, String newText) {

        if (noteModel.isBoldEnabled() && noteModel.isWaitingforFrontInput() && noteModel.isAutoFlashcardEnabled()) {

            // User backspaced, so delete the previously typed character from buffer
            if (newText.length() < oldText.length()) {

                if (noteModel.getCurrentCardFront().length() > 0) {
                    noteModel.getCurrentCardFront().deleteCharAt(noteModel.getTextArea().getCaretPosition() - noteModel.getFrontBufferIndex());
                }
            } else {
                noteModel.setCurrentCardFront(newText.substring(noteModel.getFrontBufferIndex()));

            }
        }
        // Once user turns off bold again, and the card front bufer is not empty then the card front is ready. Now we wait for the back
        else if (!noteModel.isBoldEnabled() && noteModel.getCurrentCardFront().length() > 0) {

            // Only set the back buffer index once
            if (!noteModel.isWaitingForBackInput()) {
                noteModel.setBackBufferIndex(noteModel.getTextArea().getCaretPosition());
            }

            noteModel.setWaitingforFrontInput(false);
            noteModel.setWaitingForBackInput(true);

        }
    }

    /**
     * On the event of clicking the Bold button, if:
     * (1) User has selected a chunk of text, toggle bold but retain previous set styles.
     * (2) User has not selected text, toggle bold for the subsequent times they type until
     * it is toggled again.
     */
    public void toggleBold() {
        /* Get the user's selected text */
        int start = noteModel.getTextArea().getSelection().getStart();
        int end = noteModel.getTextArea().getSelection().getEnd();

        boolean currentlySetToBold = noteModel.isBoldEnabled();

        /* If user is selecting a block of text, retain the other styles/formatting but
         * toggle the desired style */
        if (start != end) {

            retainStyles("-fx-font-weight: bold; ", "-fx-font-weight: normal; ",
                    currentlySetToBold, start, end);
            noteModel.toggleBold();

            /* If auto flashcard making is enabled, we want to make the selected bold text the front of the
             * new flashcard */
            if (noteModel.isAutoFlashcardEnabled() && noteModel.isBoldEnabled()) {
                String front = noteModel.getTextArea().getText(start, end);
                noteModel.setCurrentCardFront(front);

                noteModel.setWaitingForBackInput(true);
                noteModel.toggleBold();

            }
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
        /* We're now typing the text for the front of our card */
        if (noteModel.isAutoFlashcardEnabled() && noteModel.isBoldEnabled()) {

            noteModel.setFrontBufferIndex(noteModel.getTextArea().getCaretPosition());
            noteModel.setWaitingforFrontInput(true);
        }
    }

    /**
     * On the event of clicking the Strikethrough button, if:
     * (1) User has selected a chunk of text, toggle strikethrough but retain previous set styles.
     * (2) User has not selected text, toggle strikethrough for the subsequent times they type until
     * it is toggled again.
     */
    public void toggleStrikethrough() {
        /* Get the user's selected text */
        int start = noteModel.getTextArea().getSelection().getStart();
        int end = noteModel.getTextArea().getSelection().getEnd();

        boolean currentlySetToStrikethrough = noteModel.isStrikethroughEnabled();

        /* If user is selecting a block of text, retain the other styles/formatting but
         * toggle the desired style */
        if (start != end) {

            retainStyles("-fx-strikethrough: true; ", "-fx-strikethrough: false; ",
                    currentlySetToStrikethrough, start, end);
            noteModel.toggleStrikethrough();
            return;

        }

        /* Toggles whether or not the next characters that the user types
        is bold or not
         */
        noteModel.toggleStrikethrough();
        if (noteModel.getCurrStyle().contains("-fx-strikethrough: true; ")) {
            noteModel.getCurrStyle().remove("-fx-strikethrough: true; ");
            noteModel.getCurrStyle().add("-fx-strikethrough: false; ");
        } else {
            noteModel.getCurrStyle().remove("-fx-strikethrough: false; ");
            noteModel.getCurrStyle().add("-fx-strikethrough: true; ");
        }
    }


    /**
     * On the event of clicking the Italic button, if:
     * (1) User has selected a chunk of text, toggle italics but retain previous set styles.
     * (2) User has not selected text, toggle italics for the subsequent times they type until
     * it is toggled again.
     */
    public void toggleItalic() {
        /* Get the user's selected text */
        int start = noteModel.getTextArea().getSelection().getStart();
        int end = noteModel.getTextArea().getSelection().getEnd();

        boolean currentlySetToItalic = noteModel.isItalicEnabled();

        /* If user is selecting a block of text, retain the other styles/formatting but
         * toggle the desired style */
        if (start != end) {

            retainStyles("-fx-font-style: italic; ", "-fx-font-style: normal; ",
                    currentlySetToItalic, start, end);
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
    public void toggleUnderline() {
        /* Get the user's selected text */
        int start = noteModel.getTextArea().getSelection().getStart();
        int end = noteModel.getTextArea().getSelection().getEnd();

        boolean currentlySetToUnderlined = noteModel.isUnderlineEnabled();

        /* If user is selecting a block of text, retain the other styles/formatting but
        * toggle the desired style */
        if (start != end) {

            retainStyles("-fx-underline: true; ", "-fx-underline: false; ",
                    currentlySetToUnderlined, start, end);

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
     * @param style style to remove
     * @param opStyle alternative style to remove
     * @param state current style's state
     * @param start starting index of text selection
     * @param end ending index of text selection
     */
    private void retainStyles(String style, String opStyle, boolean state, int start, int end) {

        String[] arrayOfCss;

        /* the styles we are searching to remove from the set before adding anew */
        String searchFor = style.replace(";", "").strip();
        String searchForAlt = opStyle.replace(";", "").strip();

        for (int i = start; i < end; i++) {
            Set<String> newSet = new HashSet<>();
            /* Split up CSS styles by the semicolon */
            arrayOfCss = noteModel.getTextArea().getStyleOfChar(i).split("[;]");

            /* Add the semicolon back */
            for (int j = 0; j < arrayOfCss.length; j++) {

                /* Add to the new set if it isn't the one we want to remove */
                if (!arrayOfCss[j].strip().startsWith(searchFor) && !arrayOfCss[j].strip().startsWith(searchForAlt)) {
                    newSet.add(arrayOfCss[j].strip() + ";");
                }
                newSet.remove(";"); /* Remove any empty spaces */
            }

            /* Depending on the current state, toggle the style */
            if (state) {
                newSet.add(opStyle);
            } else {
                newSet.add(style);
            }

            /* Apply the styles to the current character */
            noteModel.getTextArea().setStyle(i, i+1, String.join(" ", newSet));

        }
    }

    /**
     * Change font size according to dropbox menu
     * @param fontSize font size option from dropdown menu
     */
    protected void changeFontSize(String fontSize) {
        /* Get the user's selected text */
        int start = noteModel.getTextArea().getSelection().getStart();
        int end = noteModel.getTextArea().getSelection().getEnd();

        String[] arrayCss;

        /* If user is selecting a block of text, retain the other styles/formatting but
         * toggle the desired style */
        if (start != end) {

            for (int i = start; i < end; i++) {
                Set<String> newSet = new HashSet<>();
                arrayCss = noteModel.getTextArea().getStyleOfChar(i).split("[;]");

                /* Add the semicolon back */
                for (int j = 0; j < arrayCss.length; j++) {

                    /* Add to the new set if it isn't the one we want to remove */
                    if (!arrayCss[j].strip().startsWith("-fx-font-size")) {
                        newSet.add(arrayCss[j].strip() + ";");
                    }
                    newSet.remove(";"); /* Remove any empty spaces */
                }

                newSet.add("-fx-font-size: " + fontSize + ";");

                noteModel.getTextArea().setStyle(i, i + 1, String.join(" ", newSet));
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
    public void applyCurrentStyleToNewText() {
        int caretPosition = noteModel.getTextArea().getCaretPosition();
        if (caretPosition != 0) {
            noteModel.getTextArea().setStyle(caretPosition - 1, caretPosition, String.join(" ",
                    noteModel.getCurrStyle()));
        }
    }

    /**
     * Change paragraph text alignment
     * @param pos text alignment position (left, center, right)
     */
    protected void setTextAlignment(String pos) {
        /* Get the paragraph indexes of user's selected text */
        int start = noteModel.getTextArea().getCaretSelectionBind().getAnchorParIndex();
        int end = noteModel.getTextArea().getCaretSelectionBind().getParagraphIndex();

        /* If there's no selected text */
        if (start == end) {
            noteModel.getTextArea().setParagraphStyle(noteModel.getTextArea().getCurrentParagraph(),
                    "-fx-text-alignment: " + pos + "; ");
            return;
        }

        if (start < end) {
            for (int i = start; i < end; i++) {
                noteModel.getTextArea().setParagraphStyle(i, "-fx-text-alignment: " + pos + ";");
            }
        } else {
            for (int i = end; i < start; i++) {
                noteModel.getTextArea().setParagraphStyle(i, "-fx-text-alignment: " + pos + ";");
            }
        }

    }

    /**
     * Change the font type (font family) for either the selected text or
     * next characters the user types
     * @param font font type/family (e.g. Verdana, 'Times New Roman', etc.)
     */
    protected void changeFontType(String font) {
        /* Get the user's selected text */
        int start = noteModel.getTextArea().getSelection().getStart();
        int end = noteModel.getTextArea().getSelection().getEnd();

        String[] arrayCss;

        /* If user is selecting a block of text, retain the other styles/formatting but
         * toggle the desired style */
        if (start != end) {

            for (int i = start; i < end; i++) {
                Set<String> newSet = new HashSet<>();
                arrayCss = noteModel.getTextArea().getStyleOfChar(i).split("[;]");

                /* Add the semicolon back */
                for (int j = 0; j < arrayCss.length; j++) {

                    /* Add to the new set if it isn't the one we want to remove */
                    if (!arrayCss[j].strip().startsWith("-fx-font-family")) {
                        newSet.add(arrayCss[j].strip() + ";");
                    }
                    newSet.remove(";"); /* Remove any empty spaces */
                }

                switch (font) {
                    case "Times New Roman" -> newSet.add("-fx-font-family: 'Times New Roman', Times, serif;");
                    case "Courier New" -> newSet.add("-fx-font-family: 'Courier New';");
                    default -> newSet.add("-fx-font-family: " + font + ";");
                }

                noteModel.getTextArea().setStyle(i, i + 1, String.join(" ", newSet));
            }
            return;

        }
        noteModel.setFontType(font);

        /* If the user has not selected text, they will change the formatting for the
        next character they type
         */
        noteModel.getCurrStyle().removeIf(style -> style.startsWith("-fx-font-family"));

        switch (font) {
            case "Times New Roman" -> noteModel.getCurrStyle().add("-fx-font-family: 'Times New Roman'; ");
            case "Courier New" -> noteModel.getCurrStyle().add("-fx-font-family: 'Courier New'; ");
            case "Comic Sans MS" -> noteModel.getCurrStyle().add("-fx-font-family: 'Comic Sans MS'; ");
            default -> noteModel.getCurrStyle().add("-fx-font-family: " + font + "; ");
        }
        System.out.println(noteModel.getCurrStyle());
    }

    /**CHANGES ADDED BY NATHAN FOR TAGS AND WHATNOT**/
    public void addTagNote(String tag){
        noteModel.addTag(tag);
    }

    public void removeTagFromNote(String tag){
        noteModel.removeTag(tag);
    }

    public boolean searchNoteByKeyword(String keyword){
        return noteModel.containsKeyword(keyword);
    }
}