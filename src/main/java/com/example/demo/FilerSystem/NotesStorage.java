package com.example.demo.FilerSystem;
import com.google.gson.Gson;

public class NotesStorage {

    /**

     //file path needed to put the flashcard under a file.
     private static String directoryPath = "StorageJSONS";
     private static String filePath = directoryPath + File.separator + "note.json";
     //intialize gson
     private static Gson gson = new Gson();

     public static void SaveNotes(Notes note) {
     // Ensure the directory exists
     File directory = new File(directoryPath);
     if (!directory.exists()) {
     directory.mkdirs();  // Creates the directory if it doesn't exist
     }

     try {
     FileWriter write = new FileWriter(filePath);
     gson.toJson(note, write);

     } catch (IOException e) {
     throw new RuntimeException(e);
     }
     }

     public static ArrayList<Notes> LoadNotes() {

     try {
     FileReader NotesPath = new FileReader(filePath);

     Type NoteArray = new TypeToken<ArrayList<Notes>>() {
     }.getType();

     return gson.fromJson(NotesPath, NoteArray);

     } catch (FileNotFoundException e) {
     throw new RuntimeException(e);
     }


     }


     */
}
