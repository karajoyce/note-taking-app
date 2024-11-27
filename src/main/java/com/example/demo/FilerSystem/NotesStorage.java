package com.example.demo.FilerSystem;
import com.example.demo.model.Deck;
import com.example.demo.model.Notebook;
import com.example.demo.model.Page;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.fxmisc.richtext.InlineCssTextArea;
import org.fxmisc.richtext.model.*;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            StyledDocument<String, String, String> document = page.getContents().getDocument();

            JsonArray paragraphs = new JsonArray();
            pageobj.add("paragraphs", paragraphs);

            for(Paragraph<String, String, String> paragraph : document.getParagraphs()) {
                JsonObject paragraphObject = new JsonObject();
                paragraphs.add(paragraphObject);
                paragraphObject.addProperty("paragraphStyle", paragraph.getParagraphStyle());
                JsonArray paragraphArray = new JsonArray();
                paragraphObject.add("segments", paragraphArray);

                for(StyledSegment<String, String> segment : paragraph.getStyledSegments()) {
                    JsonObject segmentObject = new JsonObject();
                    paragraphArray.add(segmentObject);
                    segmentObject.addProperty("text", segment.getSegment());
                    segmentObject.addProperty("style", segment.getStyle());
                }
            }
            //pageobj.add("content", gson.toJsonTree(page.getContents().getDocument()));
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

            JsonObject jsonobj = (JsonObject)JsonParser.parseString(str);

            Notebook tempNotebook = new Notebook(jsonobj.get("name").getAsString());
            JsonArray jsonarr = jsonobj.getAsJsonArray("pages");
            for (int i = 0; i < jsonarr.size(); i++){

                JsonObject pageobj = (JsonObject)jsonarr.get(i);
                Page tempPage = new Page(pageobj.get("name").getAsString());
                ReadOnlyStyledDocumentBuilder<String, String, String> document = new ReadOnlyStyledDocumentBuilder<>(SegmentOps.styledTextOps(), "");
                JsonArray paragraphs = pageobj.getAsJsonArray("paragraphs");
                for(int x = 0; x < paragraphs.size(); x++) {
                    JsonObject paragraphObject = (JsonObject) paragraphs.get(x);
                    ArrayList<StyledSegment<String, String>> segments = new ArrayList<>();

                    JsonArray segmentArray = paragraphObject.getAsJsonArray("segments");


                    for(int y = 0; y < segmentArray.size(); y++) {
                        JsonObject segmentObject = segmentArray.get(y).getAsJsonObject();
                        String text = segmentObject.get("text").getAsString();
                        String style = segmentObject.get("style").getAsString();
                        segments.add(new StyledSegment<>(text, style));
                    }

                    document.addParagraph(segments);
                }

                tempPage.setContents(new InlineCssTextArea(new SimpleEditableStyledDocument<>(document.build())));
                tempNotebook.addPage(tempPage);
            }
            return tempNotebook;
        } catch (IOException e){
            throw new RuntimeException(e);
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