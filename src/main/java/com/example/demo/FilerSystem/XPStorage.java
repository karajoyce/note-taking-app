package com.example.demo.FilerSystem;


import com.example.demo.model.XPModel;
import com.google.gson.Gson;
import java.io.*;
import java.util.Objects;

public class XPStorage {

    //file path needed to put the flashcard under a file.
    private static final String directoryPath = "StorageJSONS";
    private static final String filePath = directoryPath + File.separator + "XP.json";
    //intialize gson
    private static final Gson gson = new Gson();


    public static void SaveXPBar(XPModel xp) {
        // Ensure the directory exists
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            /*CHANGES BY NATHAN THIS iS A LIL WEIRD AND IDK HOW TO GET RID OF THE WARNING*/
            //nvm fixed it
            if (!directory.mkdirs()) {
                throw new RuntimeException("Failed to create directory");
            }
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
            // Default maxXP
            return Objects.requireNonNullElseGet(xp, () -> new XPModel(100));
        } catch (FileNotFoundException e) {
            return new XPModel(100); // Default maxXP
        } catch (Exception e) {
            System.out.println("Error loading XP file: " + e.getMessage());
            return new XPModel(100); // Default maxXP
        }
    }

}
