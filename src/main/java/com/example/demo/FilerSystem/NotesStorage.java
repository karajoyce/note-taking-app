package com.example.demo.FilerSystem;
import com.example.demo.model.Notebook;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NotesStorage {

     //file path needed to put the flashcard under a file.
     private static String directoryPath = "StorageJSONS/Notes";
     private static String filePath = directoryPath + File.separator;
     //intialize gson
     private static Gson gson = new Gson();

     public static void SaveNotes(Notebook notebook) {
     // Ensure the directory exists
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();  // Creates the directory if it doesn't exist
        }

        try {
            FileWriter write = new FileWriter(filePath+notebook.getTitle()+".json");
            gson.toJson(notebook, write);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
     }

     public static Notebook LoadNotes(String title) {

        try {
            FileReader NotesPath = new FileReader(filePath+title+".json");

            Type NoteArray = new TypeToken<Notebook>(){}.getType();

            return gson.fromJson(NotesPath, NoteArray);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * Load the title names of all decks
     * @return List of names
     */
    public static List<String> GeneratePageTitles(){
        ArrayList<String> titles = new ArrayList<>();

        File file = new File(filePath);
        if (file.exists()){
            for (File page: file.listFiles()){
                titles.add(page.getName().replace(".json", ""));
            }
        }
        return titles;
    }


}


