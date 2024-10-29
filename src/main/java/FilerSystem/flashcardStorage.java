package FilerSystem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
public class flashcardStorage {

    //file path needed to put the flashcard under a file.
    private static String FilePath = "sigmaballs/flashcard.json";
    //intialize gson
    private static Gson gson = new Gson();

    /**This function should save the flashcards in a filepatg
     * @param flashcard from list of flashcards
     */
    public static void SaveFlashCards(List<Flashcard> flashcard) {

        try {
            FileWriter flash = new FileWriter(FilePath);
            gson.toJson(flashcard);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Flashcartd> LoadFlashCards(){








    }


}
