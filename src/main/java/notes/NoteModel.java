/**
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 */

package notes;


import javafx.scene.control.TextArea;

public class NoteModel {

    private TextArea textArea;
    private double fontSize;
    private String text = "Test\n";

    public NoteModel() {
        this.textArea = new TextArea();
        fontSize = 12;      /* default size */

    }

    protected TextArea getTextArea() {
        return this.textArea;
    }

    public double getFontSize() {
        return fontSize;
    }

    public void setFontSize(double fontSize) {
        this.fontSize = fontSize;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String string) {
        this.text = string;
    }

}
