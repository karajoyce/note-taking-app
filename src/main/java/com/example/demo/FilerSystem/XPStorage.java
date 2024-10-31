package com.example.demo.FilerSystem;

import com.example.demo.model.ToDoList;
import com.google.gson.Gson;
import com.example.demo.model.Card;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
public class XPStorage {

    //file path needed to put the flashcard under a file.
    private static String directoryPath = "StorageJSONS";
    private static String filePath = directoryPath + File.separator + "XP.json";
    //intialize gson
    private static Gson gson = new Gson();

    /**
    public static void SaveXPBar(XPModel xp) {
        // Ensure the directory exists
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();  // Creates the directory if it doesn't exist
        }

        try {
            FileWriter write = new FileWriter(filePath);
            gson.toJson(xp, write);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static XPModel LoadToDoList() {

        try {
            FileReader ToDoListPath = new FileReader(filePath);

            return gson.fromJson(ToDoListPath, XPModel.class);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
            return new XPModel(10);
        }

    }

     */


}
