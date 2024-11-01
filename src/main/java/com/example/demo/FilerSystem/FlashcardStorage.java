package com.example.demo.FilerSystem;

import com.google.gson.Gson;
import com.example.demo.model.Card;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class FlashcardStorage {

    //file path needed to put the flashcard under a file.
    private static String directoryPath = "StorageJSONS";
    private static String filePath = directoryPath + File.separator + "flashcard.json";
    //intialize gson
    private static Gson gson = new Gson();

    /**This function should save the flashcards in a filepatg
     * @param deck from list of flashcards
     */
    public static void SaveDeck(ArrayList<Card> deck) {

        try {
            FileWriter flash = new FileWriter(filePath);

            gson.toJson(deck, flash);

            flash.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Load all the flash cards
     * @return flashcards
     */
    public static ArrayList<Card> LoadFlashCards() {

        try{
            FileReader deckPath = new FileReader(filePath);

            Type Cardeck = new TypeToken<ArrayList<Card>>(){}.getType();

            return gson.fromJson(deckPath, Cardeck);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }



}
