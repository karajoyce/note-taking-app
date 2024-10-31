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
     * @param card from list of flashcards
     */
    public static void SaveFlashCard(Card card) {
        // Ensure the directory exists
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();  // Creates the directory if it doesn't exist
        }

        try {
            FileWriter flash = new FileWriter(filePath);
            gson.toJson(card, flash);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void SaveDeck(ArrayList<Card> deck) {

        try {
            FileWriter flash = new FileWriter(filePath);
            gson.toJson(deck, flash);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static Card LoadCard() {

        try{
            FileReader card = new FileReader(filePath);

            Type CardDeck = new TypeToken<Card>(){}.getType();
            return gson.fromJson(card, CardDeck);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
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
