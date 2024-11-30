package com.example.demo.FilerSystem;

import com.example.demo.model.Card;
import com.example.demo.model.Deck;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
/*CHANGES MADE BY NATHAN, CHANGING CODE BELOW TO FIX WARNING*/
//REMOVED UNUSED IMPORT STATEMENTS
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FlashcardStorage {

    //file path needed to put the flashcard under a file.
    /*CHANGES MADE BY NATHAN, CHANGING CODE BELOW TO FIX WARNING*/
    //THINGS BEING NEEDED TO CHANGE TO FINAL
    private static final String directoryPath = "StorageJSONS/Decks";
    private static final String filePath = directoryPath + File.separator;
    //intialize gson
    private static final Gson gson = new Gson();

    /**This function should save the flashcards in a filepatg
     * @param deck from list of flashcards
     */
    public static void SaveDeck(Deck deck) {

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            /*FROM NATHAN WEIRD ERROR THAT DRK HOW TO FIX*/
            //MAYBE FIXED IT?
            if (!directory.mkdirs()) {
                throw new RuntimeException("Failed to create directory");
            }
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
            /*FROM NATHAN WEIRD ERROR THAT DRK HOW TO FIX*/
            //WHY DO WE NEED TO COLLAPSE HERE?
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    /*NOTE FROM NATHAN DUPLICATED CODE???*/
    /**
     * Load the title names of all decks
     * @return List of names
     */
    public static List<String> GenerateDeckTitles(){
        ArrayList<String> titles = new ArrayList<>();


        File file = new File(filePath);
        if (file.exists()){
            /*CHANGES MADE BY NATHAN, CHANGING CODE BELOW TO FIX WARNING*/
            //CHANGED THIS TO Objects.requireNonNull
            for (File deck: Objects.requireNonNull(file.listFiles())){
                titles.add(deck.getName().replace(".json", ""));
            }
        }

        /*CHANGES MADE BY NATHAN, CHANGING CODE BELOW TO FIX WARNING*/
        //CAN BE CHANGED TO isEmpty()?
        //if(titles.size() == 0) {
        if(titles.isEmpty()) {
            titles.add("Empty Deck");
            Deck deck = new Deck("Empty Deck");
            deck.addCard(new Card("", ""));
            SaveDeck(deck);
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


