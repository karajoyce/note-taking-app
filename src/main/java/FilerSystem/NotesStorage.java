package FilerSystem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

}
