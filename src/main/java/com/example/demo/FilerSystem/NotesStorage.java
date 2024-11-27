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
            if (!directory.mkdirs()) {
                throw new RuntimeException("Failed to create directory: " + directoryPath);
            }
        }

        String fullFilePath = filePath + notebook.getTitle() + ".json";
        try (FileWriter writer = new FileWriter(fullFilePath)) {
            JsonObject json = new JsonObject();
            json.addProperty("name", notebook.getTitle());

            JsonArray pages = new JsonArray();
            for (Page page : notebook.getNotes()) {
                JsonObject pageJson = new JsonObject();
                pageJson.addProperty("name", page.getTitle());
                // Serialize page content to JSON
                pageJson.addProperty("content", page.getContents().getText()); // Get plain text content
                pages.add(pageJson);
            }
            json.add("pages", pages);

            writer.write(json.toString());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save notebook: " + notebook.getTitle(), e);
        }
    }

    public static Notebook LoadNotes(String title) {

        String fullFilePath = filePath + title + ".json";
        File file = new File(fullFilePath);

        if (!file.exists()) {
            System.err.println("Notebook file not found: " + fullFilePath);
            return null;
        }

        try (FileReader reader = new FileReader(file)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            Notebook notebook = new Notebook(json.get("name").getAsString());

            JsonArray pages = json.getAsJsonArray("pages");
            for (JsonElement pageElement : pages) {
                JsonObject pageObj = pageElement.getAsJsonObject();
                String pageName = pageObj.get("name").getAsString();
                String pageContent = pageObj.get("content").getAsString();

                Page page = new Page(pageName);
                InlineCssTextArea textArea = page.getContents();
                textArea.replaceText(pageContent); // Populate the content
                page.setContents(textArea);       // Bind content to Page

                notebook.addPage(page);
            }

            return notebook;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load notebook: " + title, e);
        }
    }

    /**
     * Load the title names of all decks
     * @return List of names
     */
    public static List<String> GenerateNotebookTitles(){
        ArrayList<String> titles = new ArrayList<>();

        File file = new File(filePath);
        if (file.exists()){
            for (File page: file.listFiles()){
                titles.add(page.getName().replace(".json", ""));
            }
        }
        return titles;
    }

    public static void DeleteNotebook(String folderName) {
        String fullFilePath = filePath + folderName + ".json";
        File file = new File(fullFilePath);

        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Deleted file: " + fullFilePath);
            } else {
                System.err.println("Failed to delete file: " + fullFilePath);
            }
        } else {
            System.err.println("File not found for deletion: " + fullFilePath);
        }
    }


}




