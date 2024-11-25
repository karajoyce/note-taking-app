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

        /*
        JsonArray pages = new JsonArray();
        JsonObject jsonobj = new JsonObject();
        jsonobj.add("pages", pages);
        jsonobj.addProperty("name", notebook.getTitle());
        for (Page page: notebook.getNotes()){
            JsonObject pageobj = new JsonObject();
            pageobj.addProperty("name", page.getTitle());
            // saving contents of the InlineCSSTextArea
            pageobj.addProperty("text", page.getContents().getText());
            //pageobj.addProperty("style", page.getContents().getStyle());

            //StyleSpans<String> span = page.getContents().getStyleSpans(0, page.getContents().getLength());

            pageobj.addProperty("stylespans", gson.toJson(page.getContents()));
            pages.add(pageobj);
        }
         System.out.println(jsonobj);

         */
         Gson gson = new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() {
             @Override
             public boolean shouldSkipField(FieldAttributes f) {
                 return false;
             }

             @Override
             public boolean shouldSkipClass(Class<?> clazz) {
                 return clazz.equals(javafx.css.StyleableObjectProperty.class);
             }
         }).create();



        try {
            FileWriter flash = new FileWriter(filePath+notebook.getTitle()+".json");
           // gson.toJson(notebook, flash);

            //FileWriter writer = new FileWriter(filePath+notebook.getTitle()+".json");
            //writer.write(jsonobj.toString());
            flash.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
     }

     public static Notebook LoadNotes(String title) {

        try {
            FileInputStream stream = new FileInputStream(filePath+title+".dat");
            ObjectInputStream s = new ObjectInputStream(stream);
            return (Notebook) s.readObject();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
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
                titles.add(page.getName().replace(".dat", ""));
            }
        }
        return titles;
    }


    private static Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(StyleSpans.class, new StyleSpansAdapter())
                .setPrettyPrinting()
                .create();
    }

    /**
     * Serializes an InlineCssTextArea to a JSON file
     */
    public static void serialize(InlineCssTextArea textArea, String filePath) throws IOException {
        TextAreaState state = new TextAreaState(
                textArea.getText(),
                textArea.getStyle(),
                textArea.getStyleSpans(0, textArea.getLength())
        );

        Gson gson = createGson();
        String json = gson.toJson(state);

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
        }
    }

    /**
     * Deserializes an InlineCssTextArea from a JSON file
     */
    public static InlineCssTextArea deserialize(String filePath) throws IOException {
        Gson gson = createGson();

        try (FileReader reader = new FileReader(filePath)) {
            TextAreaState state = gson.fromJson(reader, TextAreaState.class);
            InlineCssTextArea textArea = new InlineCssTextArea();
            textArea.replaceText(state.text);
            textArea.setStyle(state.style);
            textArea.setStyleSpans(0, state.styleSpans);
            return textArea;
        }
    }

    /**
     * Data class to hold the serializable state
     */
    private static class TextAreaState {
        private String text;
        private String style;
        private StyleSpans<String> styleSpans;

        public TextAreaState(String text, String style, StyleSpans<String> styleSpans) {
            this.text = text;
            this.style = style;
            this.styleSpans = styleSpans;
        }
    }

    /**
     * Custom type adapter for StyleSpans
     */
    private static class StyleSpansAdapter implements JsonSerializer<StyleSpans<String>>,
            JsonDeserializer<StyleSpans<String>> {

        @Override
        public JsonElement serialize(StyleSpans<String> src, Type typeOfSrc,
                                     JsonSerializationContext context) {
            JsonArray array = new JsonArray();

            // Convert StyleSpans to a list of style-length pairs
            src.iterator().forEachRemaining(span -> {
                JsonObject spanObj = new JsonObject();
                spanObj.addProperty("style", span.getStyle());
                spanObj.addProperty("length", span.getLength());
                array.add(spanObj);
            });

            return array;
        }

        @Override
        public StyleSpans<String> deserialize(JsonElement json, Type typeOfT,
                                              JsonDeserializationContext context) throws JsonParseException {
            StyleSpansBuilder<String> builder = new StyleSpansBuilder<>();
            JsonArray array = json.getAsJsonArray();

            // Rebuild StyleSpans from the style-length pairs
            for (JsonElement element : array) {
                JsonObject spanObj = element.getAsJsonObject();
                String style = spanObj.get("style").getAsString();
                int length = spanObj.get("length").getAsInt();
                builder.add(style, length);
            }

            return builder.create();
        }
    }

    /**
     * Utility method to get the JSON string without saving to file
     */
    public static String toJson(InlineCssTextArea textArea) {
        TextAreaState state = new TextAreaState(
                textArea.getText(),
                textArea.getStyle(),
                textArea.getStyleSpans(0, textArea.getLength())
        );
        return createGson().toJson(state);
    }

    /**
     * Utility method to create InlineCssTextArea from JSON string
     */
    public static InlineCssTextArea fromJson(String json) {
        TextAreaState state = createGson().fromJson(json, TextAreaState.class);
        InlineCssTextArea textArea = new InlineCssTextArea();
        textArea.replaceText(state.text);
        textArea.setStyle(state.style);
        textArea.setStyleSpans(0, state.styleSpans);
        return textArea;
    }

}