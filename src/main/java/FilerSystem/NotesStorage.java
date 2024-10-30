package FilerSystem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Card;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
public class NotesStorage {

    //file path needed to put the flashcard under a file.
    private static String FilePath = "sigmaballs/notes.json";
    //intialize gson
    private static Gson gson = new Gson();

    /**This function should save the notes in a filepatg
     * @param deck from list of flashcards
     */
    /**
    public static void SaveNotes(ArrayList<Notes> note) {

        try {
            FileWriter note = new FileWriter(FilePath);
            gson.toJson(note);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
**/

    /*

      public static ArrayList<Card> LoadNotes() {

        try{
            FileReader note = new FileReader(FilePath);

            Type Notes = new TypeToken<ArrayList<Note>>(){}.getType();
            return gson.fromJson(note, Notes);

    } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }



     */

}
