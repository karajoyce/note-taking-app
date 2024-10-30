package com.example.demo.FilerSystem;

import com.google.gson.Gson;
import com.example.demo.model.Card;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FlashcardStorage {

    //file path needed to put the flashcard under a file.
    private static String FilePath = "sigmaballs/flashcard.json";
    //intialize gson
    private static Gson gson = new Gson();

    /**This function should save the flashcards in a filepatg
     * @param flashcard from list of flashcards
     */
    public static void SaveFlashCards(ArrayList<Card> flashcard) {

        try {
            FileWriter flash = new FileWriter(FilePath);
            gson.toJson(flashcard);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Card> LoadFlashCards(){
        return null;
    }


}
