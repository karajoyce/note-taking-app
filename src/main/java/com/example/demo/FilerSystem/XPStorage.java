package com.example.demo.FilerSystem;

import com.example.demo.model.ToDoList;
import com.example.demo.model.XPManager;
import com.example.demo.model.XPModel;
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


    public static void SaveXPBar(XPModel xp) {
        // Ensure the directory exists
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();  // Creates the directory if it doesn't exist
        }

        try {
            FileWriter write = new FileWriter(filePath);
            gson.toJson(xp, write);
            write.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static XPModel LoadXPBar() {
        try {
            FileReader xpFile = new FileReader(filePath);
            XPModel xp = gson.fromJson(xpFile, XPModel.class);
            xpFile.close();
            if (xp == null) {
                return new XPModel(100); // Default maxXP
            }
            return xp;
        } catch (FileNotFoundException e) {
            return new XPModel(100); // Default maxXP
        } catch (Exception e) {
            System.out.println("Error loading XP file: " + e.getMessage());
            return new XPModel(100); // Default maxXP
        }
    }





}
