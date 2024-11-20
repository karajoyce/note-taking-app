/**
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 */

package com.example.demo.notes;

import org.fxmisc.richtext.InlineCssTextArea;

import java.util.HashSet;
import java.util.Set;

/**
 * The Model class of the text editor (MVC Model)
 * Holds the data required such as font size and the text area
 * itself so the controller and the view can access it
 */
public class NoteModel {

    /** the main text area of the text editor */
    private InlineCssTextArea textArea = new InlineCssTextArea();

    /** the list of styles that is being applied to the typed text */
    private Set<String> currStyle = new HashSet<>();

    /** if bold is enabled, true. otherwise, false */
    private boolean boldEnabled;

    /** If italics is enabled, true. otherwise, false */
    private boolean italicEnabled;

    /** if underline enabled, true. otherwise, false */
    private boolean underlineEnabled;

    /** if strikethrough enabled, true. otherwise, false */
    private boolean strikethroughEnabled;

    /** the font size of the text being written */
    private String fontsize = "12px";

    /** font style. e.g. Verdana, Times New Roman, etc. */
    private String fontType;

    /** if auto flashcard creating turned on, true. otherwise, false */
    private boolean autoFlashcardEnabled;

    /** if auto flashcards are on and waiting for back input */
    private boolean waitingForBackInput;
    /** the current card's front text */
    private String currentCardFront;
    /** the current card's back text buffer */
    private StringBuilder backBuffer;

    /** Constructor */
    public NoteModel() {

        boldEnabled = false;
        italicEnabled = false;
        underlineEnabled = false;
        strikethroughEnabled = false;

        waitingForBackInput = false;
        autoFlashcardEnabled = false;
        currentCardFront = "";
        backBuffer = new StringBuilder();

        fontType = "Arial";

        /* Default current style */
        currStyle.add("-fx-font-size: 12px; ");
        currStyle.add("-fx-font-weight: normal; ");
        currStyle.add("-fx-font-style: normal; ");
        currStyle.add("-fx-underline: false; ");
        currStyle.add("-fx-strikethrough: false; ");
        currStyle.add("-fx-font-family: " + fontType + "; ");

        /* prevents text from going off the screen and scrolling horizontally */
        textArea.setWrapText(true);

    }


    /** Getter methods and toggle methods for styles */
    public Set<String> getCurrStyle() {
        return this.currStyle;
    }

    public String getFontType() {
        return this.fontType;
    }

    public void setFontType(String font) {
        this.fontType = font;
    }

    public boolean isBoldEnabled() {
        return boldEnabled;
    }

    public void toggleBold() {
        this.boldEnabled = !boldEnabled;
    }

    public boolean isItalicEnabled() {
        return italicEnabled;
    }

    public void toggleItalic() {
        this.italicEnabled = !italicEnabled;
    }

    public boolean isUnderlineEnabled() {
        return underlineEnabled;
    }

    public void toggleUnderline() {
        this.underlineEnabled = !underlineEnabled;
    }

    public boolean isStrikethroughEnabled() {
        return strikethroughEnabled;
    }

    public void toggleStrikethrough() {
        this.strikethroughEnabled = !strikethroughEnabled;
    }

    /** Getter setter for font size */
    public String getFontsize() {
        return fontsize;
    }

    public void setFontsize(String fontsize) {
        this.fontsize = fontsize;
    }

    /** Getter for the main text area */
    public InlineCssTextArea getTextArea() {
        return textArea;
    }

    /** Getter setter for auto flashcard making enabling */
    public boolean isAutoFlashcardEnabled() {
        return autoFlashcardEnabled;
    }

    public void toggleAutoFlashCard() {
        this.autoFlashcardEnabled = !autoFlashcardEnabled;
    }

    public boolean isWaitingForBackInput() {
        return this.waitingForBackInput;
    }

    public void setWaitingForBackInput(boolean state) {
        this.waitingForBackInput = state;
    }

    public String getCurrentCardFront() {
        return this.currentCardFront;
    }

    public void setCurrentCardFront(String cardFront) {
        this.currentCardFront = cardFront;
    }

    public StringBuilder getBackBuffer() {
        return this.backBuffer;
    }


}
