/**
CMPT 370, T05, Team 4, Prof. Jon Lovering
Kara Leier, kjl061, 11293306
Nathan Balilis, ncb421, 11295020
Trushank Lakdawala, nus429, 11350445
Jinny Kim, yek738, 11304174
Sara Shakeel, gvk731, 11367521
 */

package notes;

/**
 * TO DO DOC STRING
 */
public class Note {

    /**
     * the title of the note
     */
    String noteTitle;

    /**
     * the date of creation for the note
     * template:
     * DD / MM / YYYY
     */
    long dateCreated;

    /**
     * the size of the font written
     */
    int fontSize;

    /**
     * true if the font is bold, false otherwise
     */
    boolean fontBold;

    /**
     * true if the font is italicized, false otherwise
     */
    boolean fontItalic;

    /**
     * true if the font is underlined, false otherwise
     */
    boolean fontUnderline;

    /**
     * Constructor method
     * @param noteTitle what the new note will be named
     * @param dateCreated when the new note was created
     */
    public Note(String noteTitle, long dateCreated) {
        this.noteTitle = noteTitle;
        this.dateCreated = dateCreated;
        this.fontBold = false;
        this.fontItalic = false;
        this.fontUnderline = false;
    }

    /* Methods */

    /**
     * Get the current note title
     * @return note title
     */
    public String getTitle() {
        return this.noteTitle;
    }

    /**
     * Set a new title
     * @param newTitle the soon-to-be title
     */
    public void setTitle(String newTitle) {
        this.noteTitle = newTitle;
    }

    /**
     * Get the current font size
     * @return the current font size
     */
    public int getFontSize() {
        return this.fontSize;
    }

    /**
     * Set the new font size
     * @param newSize new font size
     */
    public void setFontSize(int newSize) {
        this.fontSize = newSize;
    }

    /**
     * It toggles the bold option
     * (If it's true, turns it to false, and vice versa)
     */
    public void toggleBold() {
        this.fontBold = !this.fontBold;
    }

    /**
     * It toggles the italicization option
     * (If it's true, turns it to false, and vice versa)
     */
    public void toggleItalic() {
        this.fontItalic = !this.fontItalic;
    }

    /**
     * It toggles the underlining option
     * (If it's true, turns it to false, and vice versa)
     */
    public void toggleUnderline() {
        this.fontUnderline = !this.fontUnderline;
    }

    /** Creates an embedded hyperlink */
    public String createHyperlink() {

        /** TO DO */
        return null;
    }





}
