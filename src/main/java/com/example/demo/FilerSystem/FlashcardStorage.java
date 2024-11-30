package com.example.demo.FilerSystem;

import com.example.demo.model.Deck;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FlashcardStorage {

    //file path needed to put the flashcard under a file.
    private static String directoryPath = "StorageJSONS/Decks";
    private static String filePath = directoryPath + File.separator;
    //intialize gson
    private static Gson gson = new Gson();

    /**This function should save the flashcards in a filepatg
     * @param deck from list of flashcards
     */
    public static void SaveDeck(Deck deck) {

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {

            FileWriter flash = new FileWriter(filePath+deck.getTitle()+".json");

            gson.toJson(deck, flash);

            flash.flush();
            flash.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Load all the flash cards
     * @return flashcards
     */
    public static Deck LoadFlashCards(String title) {

        try{
            FileReader deckPath = new FileReader(filePath+title+".json");

            Type Cardeck = new TypeToken<Deck>(){}.getType();

            Deck deck = gson.fromJson(deckPath, Cardeck);
            deckPath.close();
            return deck;


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * Load the title names of all decks
     * @return List of names
     */
    public static List<String> GenerateDeckTitles(){
        ArrayList<String> titles = new ArrayList<>();


        File file = new File(filePath);
        if (file.exists()){
            for (File deck: file.listFiles()){
                titles.add(deck.getName().replace(".json", ""));
            }
        }

        return titles;
    }

    public static void DeleteDeck(String title) {
        String fullFilePath = filePath + title + ".json";
        File file = new File(fullFilePath);

        try {
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}


