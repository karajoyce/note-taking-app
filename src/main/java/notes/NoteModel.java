/**
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 */

package notes;

import org.fxmisc.richtext.InlineCssTextArea;

import java.util.HashSet;
import java.util.Set;

/**
 * The Model class of the text editor (MVC Model)
 * Holds the data required such as font size and the text area
 * itself so the controller and the view can access it
 */
public class NoteModel {

    private InlineCssTextArea textArea = new InlineCssTextArea();
    private Set<String> currStyle = new HashSet<>();
    private boolean boldEnabled;
    private boolean italicEnabled;
    private boolean underlineEnabled;

    private double fontsize;

    public NoteModel() {
        fontsize = 12;  /* default */

        boldEnabled = false;
        italicEnabled = false;
        underlineEnabled = false;

        /* prevents text from going off the screen and scrolling horizontally */
        textArea.setWrapText(true);

    }

    public Set<String> getCurrStyle() {
        return this.currStyle;
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

    public double getFontsize() {
        return fontsize;
    }

    public void setFontsize(double fontsize) {
        this.fontsize = fontsize;
    }

    public InlineCssTextArea getTextArea() {
        return textArea;
    }
}
