/**
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 */

package notes;

import org.fxmisc.richtext.StyleClassedTextArea;

/**
 * The Model class of the text editor (MVC Model)
 * Holds the data required such as font size and the text area
 * itself so the controller and the view can access it
 */
public class NoteModel {

    private StyleClassedTextArea textArea = new StyleClassedTextArea();
    private double fontsize;

    public NoteModel() {
        fontsize = 12;  /* default */

    }

    public StyleClassedTextArea getTextArea() {
        return textArea;
    }
}
