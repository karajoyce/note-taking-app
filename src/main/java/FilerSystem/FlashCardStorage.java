package FilerSystem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Card;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class FlashCardStorage {

    //file path needed to put the flashcard under a file.
    private static String FilePath = "sigmaballs/flashcard.json";
    //intialize gson
    private static Gson gson = new Gson();

    /**This function should save the flashcards in a filepatg
     * @param card from list of flashcards
     */
    public static void SaveFlashCard(Card card) {

        try {
            FileWriter flash = new FileWriter(FilePath);
            gson.toJson(card, flash);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void SaveDeck(ArrayList<Card> deck) {

        try {
            FileWriter flash = new FileWriter(FilePath);
            gson.toJson(deck, flash);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static Card LoadCard() {

        try{
            FileReader card = new FileReader(FilePath);

            Type CardDeck = new TypeToken<Card>(){}.getType();
            return gson.fromJson(card, CardDeck);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
    public static ArrayList<Card> LoadFlashCards() {

        try{
            FileReader deckPath = new FileReader(FilePath);

            Type Cardeck = new TypeToken<ArrayList<Card>>(){}.getType();

            return gson.fromJson(deckPath, Cardeck);

    } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}