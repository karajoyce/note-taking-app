package com.example.demo.FilerSystem;
import com.example.demo.model.Notebook;
import com.example.demo.model.Page;
import com.google.gson.*;
import org.fxmisc.richtext.InlineCssTextArea;
import org.fxmisc.richtext.model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

public class NotesStorage {

    //file path needed to put the flashcard under a file.
    private static String directoryPath = "StorageJSONS/Notes";
    private static String filePath = directoryPath + File.separator;
    //intialize gson
    private static Gson gson = new Gson();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Function to save the notebook
     * @param notebook = notebook to save
     */
    public static void SaveNotes(Notebook notebook) {
        // Ensure the directory exists
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();  // Creates the directory if it doesn't exist
        }

        // create notebook by saving the title and creating a place to save the pages
        JsonArray pages = new JsonArray();
        JsonObject jsonobj = new JsonObject();
        JsonArray tagsArray = new JsonArray();
        jsonobj.add("pages", pages);
        jsonobj.addProperty("name", notebook.getTitle());
        /*Changes Added from Nathan, adding a property to save the creation date, as well as
         * the tags*/
        jsonobj.addProperty("creationDate", notebook.getCreationDate().format(FORMATTER)); //Adding creation date

        /*CHANGES BY NATHAN FOR MOST RECENTLY ACCESSED FOLDER*/
        jsonobj.addProperty("lastAccessed", notebook.getLastAccessed().format(FORMATTER));


        //Trying to save tags
        for (String tag : notebook.getTags()) {
            tagsArray.add(tag);
        }
        jsonobj.add("tags", tagsArray);

        // iterate through the pages to save each text sections, this adds the page title and its contents
        // into the json array
        for (Page page : notebook.getNotes()) {
            JsonObject pageobj = new JsonObject();
            pageobj.addProperty("name", page.getTitle());
            // saving contents of the InlineCSSTextArea
            StyledDocument<String, String, String> document = page.getContents().getDocument();

            // save paragraphs individually based on what is in the inlinecss
            JsonArray paragraphs = new JsonArray();
            pageobj.add("paragraphs", paragraphs);

            // iterate through the paragraphs
            for (Paragraph<String, String, String> paragraph : document.getParagraphs()) {
                JsonObject paragraphObject = new JsonObject();
                paragraphs.add(paragraphObject);
                paragraphObject.addProperty("paragraphStyle", paragraph.getParagraphStyle());
                JsonArray paragraphArray = new JsonArray();
                paragraphObject.add("segments", paragraphArray);

                // iterate through each segment, the segment will have specific formatting based on  what the user
                // entered
                for (StyledSegment<String, String> segment : paragraph.getStyledSegments()) {
                    JsonObject segmentObject = new JsonObject();
                    paragraphArray.add(segmentObject);
                    segmentObject.addProperty("text", segment.getSegment());
                    segmentObject.addProperty("style", segment.getStyle());
                }
            }
            // add all the pages to the page section we made at the top
            pages.add(pageobj);
        }

        // try saving the whole thing
        try {
            FileWriter flash = new FileWriter(filePath + notebook.getTitle() + ".json");
            flash.write(jsonobj.toString());
            flash.flush();
            flash.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * fucntion for loading notes given the note title
     * @param title = notebook sought
     * @return Notebook loaded
     */
    public static Notebook LoadNotes(String title) {

        try {
            // make the file path
            Path filep = Path.of(filePath + title + ".json");
            String str = Files.readString(filep);

            // reverse the save notes function, load the text from file into a json object
            JsonObject jsonobj = (JsonObject) JsonParser.parseString(str);
            Notebook tempNotebook = new Notebook(jsonobj.get("name").getAsString());

            /**Changes from Nathan, Trying to load Tags and CreationDate*/
            //LOADING TAGS, MAY HAVE FUCKY FUNCTIONALITY
            if (jsonobj.has("creationDate")) {
                String creationDateString = jsonobj.get("creationDate").getAsString();
                tempNotebook.setCreationDate(LocalDateTime.parse(creationDateString, FORMATTER));
            }
            /**Changes from Nathan, Trying to Last Accessed Time*/
            //LOADING LAST ACCESSED
            if (jsonobj.has("lastAccessed")) {
                String lastAccessedString = jsonobj.get("lastAccessed").getAsString();
                tempNotebook.setLastAccessed(LocalDateTime.parse(lastAccessedString, FORMATTER));
            } else {
                tempNotebook.setLastAccessed(tempNotebook.getLastAccessed());
            }
            //LOAD TAGS
            if (jsonobj.has("tags")) {
                JsonArray tagsArray = jsonobj.get("tags").getAsJsonArray();
                for (int i = 0; i < tagsArray.size(); i++) {
                    tempNotebook.addTag(tagsArray.get(i).getAsString());
                }
            }

            // extract the list of pages and iterate through it
            JsonArray jsonarr = jsonobj.getAsJsonArray("pages");
            for (int i = 0; i < jsonarr.size(); i++) {

                // for each page, get its name and all the paragraphs
                JsonObject pageobj = (JsonObject) jsonarr.get(i);
                Page tempPage = new Page(pageobj.get("name").getAsString());
                ReadOnlyStyledDocumentBuilder<String, String, String> document = new ReadOnlyStyledDocumentBuilder<>(SegmentOps.styledTextOps(), "");
                JsonArray paragraphs = pageobj.getAsJsonArray("paragraphs");

                // iterate paragraphs
                for (int x = 0; x < paragraphs.size(); x++) {
                    JsonObject paragraphObject = (JsonObject) paragraphs.get(x);
                    ArrayList<StyledSegment<String, String>> segments = new ArrayList<>();
                    JsonArray segmentArray = paragraphObject.getAsJsonArray("segments");

                    // for each paragraph, rebuild the segments
                    for (int y = 0; y < segmentArray.size(); y++) {
                        JsonObject segmentObject = segmentArray.get(y).getAsJsonObject();
                        String text = segmentObject.get("text").getAsString();
                        String style = segmentObject.get("style").getAsString();
                        segments.add(new StyledSegment<>(text, style));
                    }
                    // add the paragraph back to the tempnotebook
                    document.addParagraph(segments, paragraphObject.get("paragraphStyle").getAsString());
                }
                // rebuild the notebook
                tempPage.setContents(new InlineCssTextArea(new SimpleEditableStyledDocument<>(document.build())));
                tempNotebook.addPage(tempPage);
            }
            return tempNotebook;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Load the title names of all decks
     *
     * @return List of names
     */
    public static List<String> GenerateNotebookTitles() {
        ArrayList<String> titles = new ArrayList<>();

        File file = new File(filePath);
        if (file.exists()) {
            for (File page : file.listFiles()) {
                titles.add(page.getName().replace(".json", ""));
            }
        }
        return titles;
    }

    /**
     * function for deleting a notebook from the json
     * @param folderName
     */
    public static void DeleteNotebook(String folderName) {
        String fullFilePath = filePath + folderName + ".json";
        File file = new File(fullFilePath);

        if (file.exists()) {
            if (file.delete()) {
                //System.out.println("Deleted file: " + fullFilePath);
            } else {
                System.err.println("Failed to delete file: " + fullFilePath);
            }
        } else {
            System.err.println("File not found for deletion: " + fullFilePath);
        }
    }


    /**
     * function for getting the time a folder was made
     * @param folderName = folder sought out
     * @return = the time it was created
     */
    public static LocalDateTime GetFolderCreationDate(String folderName) {
        try {
            Notebook notebook = LoadNotes(folderName);
            if (notebook != null && notebook.getCreationDate() != null) {
                return notebook.getCreationDate();
            }
        } catch (Exception e) {
            System.err.println("Error retrieving creation date for folder: " + folderName);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * function for getting the time a folder was last used
     * @param folderName = folder sought out
     * @return = the time it was last used
     */
    public static LocalDateTime GetFolderLastAccess(String folderName) {
        try {
            Notebook notebook = LoadNotes(folderName);
            if (notebook != null && notebook.getLastAccessed() != null) {
                return notebook.getLastAccessed();
            }
        } catch (Exception e) {
            System.err.println("Error retrieving Last Access for folder: " + folderName);
            e.printStackTrace();
        }
        return null;
    }
}
