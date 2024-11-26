package com.example.demo.FilerSystem;
import com.example.demo.model.Notebook;
import com.example.demo.model.Page;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.fxmisc.richtext.InlineCssTextArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
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


        JsonArray pages = new JsonArray();
        JsonObject jsonobj = new JsonObject();
        jsonobj.add("pages", pages);
        jsonobj.addProperty("name", notebook.getTitle());
        for (Page page: notebook.getNotes()){
            JsonObject pageobj = new JsonObject();
            pageobj.addProperty("name", page.getTitle());
            // saving contents of the InlineCSSTextArea
            pageobj.addProperty("content", gson.toJson(page.getContents().getDocument()));
            pages.add(pageobj);
        }

        try {
            FileWriter flash = new FileWriter(filePath+notebook.getTitle()+".json");
            flash.write(jsonobj.toString());
            flash.flush();
            flash.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
     }

     public static Notebook LoadNotes(String title) {

        try {
            Path filep = Path.of(filePath+title+".json");
            String str = Files.readString(filep);
            System.out.println(str);
        } catch (IOException e){
            throw new RuntimeException(e);
         }

        return null;
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
                titles.add(page.getName().replace(".dat", ""));
            }
        }
        return titles;
    }

}