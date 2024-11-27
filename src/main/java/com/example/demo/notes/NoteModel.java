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

    /**Added by Nathan, Fields for tags and images **/
    private Set<String> tags = new HashSet<>();
    private String imagePath;

    /** if auto flashcard creating turned on, true. otherwise, false */
    private boolean autoFlashcardEnabled;

    /** if auto flashcards are on and waiting for back input */
    private boolean waitingForBackInput;
    /** If auto flashcards are on on and waiting for front input */
    private boolean waitingforFrontInput;
    /** the current card's front text */
    private StringBuilder currentCardFront;
    /** the current card's back text buffer */
    private StringBuilder backBuffer;
    /** the start of the buffers within the text area */
    private int frontBufferIndex;
    private int backBufferIndex;

    /** Constructor */
    public NoteModel() {

        boldEnabled = false;
        italicEnabled = false;
        underlineEnabled = false;
        strikethroughEnabled = false;

        waitingForBackInput = false;
        waitingforFrontInput = false;
        autoFlashcardEnabled = false;
        currentCardFront = new StringBuilder();
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
    public void setTextArea(InlineCssTextArea textA){ this.textArea = textA; }

    public void resetTextAreaStyles(NoteController nCont){
        if (isBoldEnabled()){
            nCont.toggleBold();
        }
        if (isItalicEnabled()){
            nCont.toggleItalic();
        }
        if (isUnderlineEnabled()){
            nCont.toggleUnderline();
        }
        if (isStrikethroughEnabled()){
            nCont.toggleStrikethrough();
        }
    }

    /** Getter setter for auto flashcard making enabling */
    public boolean isAutoFlashcardEnabled() {
        return autoFlashcardEnabled;
    }

    public void toggleAutoFlashcard() {
        this.autoFlashcardEnabled = !autoFlashcardEnabled;

        /* If autoflashcards were turned off, we want to clear the buffers so they don't affect the next
        auto flash card making session
         */
        if (!this.autoFlashcardEnabled) {
            resetBackBuffer("");
            setCurrentCardFront("");
            setWaitingforFrontInput(false);
            setWaitingForBackInput(false);
        } else {
            setWaitingforFrontInput(true);
            setWaitingForBackInput(false);
        }
    }

    /** functions for auto flashcard front and back buffers ------------------------- */
    public boolean isWaitingForBackInput() {
        return this.waitingForBackInput;
    }

    public void setWaitingForBackInput(boolean state) {
        this.waitingForBackInput = state;
    }

    public StringBuilder getCurrentCardFront() {
        return this.currentCardFront;
    }

    public void setCurrentCardFront(String cardFront) {
        this.currentCardFront = new StringBuilder(cardFront);
    }

    public StringBuilder getBackBuffer() {
        return this.backBuffer;
    }

    public void resetBackBuffer(String start) {
        this.backBuffer = new StringBuilder(start);
    }

    public boolean isWaitingforFrontInput() {
        return this.waitingforFrontInput;
    }

    public void setWaitingforFrontInput(boolean state) {
        this.waitingforFrontInput = state;
    }

    /** starting indexes of the text in the text area */
    public int getFrontBufferIndex() {
        return this.frontBufferIndex;
    }

    public int getBackBufferIndex() {
        return backBufferIndex;
    }

    public void setFrontBufferIndex(int index) {
        this.frontBufferIndex = index;
    }

    public void setBackBufferIndex(int index) {
        this.backBufferIndex = index;
    }
    /** -------------------------------------------------------------------- */

    /**ADDED BY NATHAN SETTERS AND GETTERS FOR TAGS AND IMAGE**/
    public void addTag(String tag){
        tags.add(tag);
    }

    public void removeTag(String tag){
        tags.remove(tag);
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public boolean containsKeyword(String keyword){
        return tags.contains(keyword) || textArea.getText().contains(keyword);
    }
}


