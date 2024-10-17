package notes;

public class Note {
    String noteTitle;
    long dataCreated;
    int fontSize;
    boolean fontBold;
    boolean fontItalic;

    boolean fontUnderline;

    public Note(String noteTitle, long dataCreated, int fontSize) {
        this.noteTitle = noteTitle;
        this.dataCreated = dataCreated;
        this.fontSize = fontSize;
        this.fontBold = false;
        this.fontItalic = false;
        this.fontUnderline = false;
    }


}
