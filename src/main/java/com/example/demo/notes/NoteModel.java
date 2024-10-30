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

    /** Constructor */
    public NoteModel() {

        boldEnabled = false;
        italicEnabled = false;
        underlineEnabled = false;
        strikethroughEnabled = false;

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

    public String getFontsize() {
        return fontsize;
    }

    public void setFontsize(String fontsize) {
        this.fontsize = fontsize;
    }

    public InlineCssTextArea getTextArea() {
        return textArea;
    }
}
