package com.example.demo.FilerSystem;

import com.example.demo.model.Task;
import com.example.demo.model.ToDoList;
import com.google.gson.Gson;
import com.example.demo.model.Card;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ToDoStorage {

    //file path needed to put the flashcard under a file.
    private static String directoryPath = "StorageJSONS";
    private static String filePath = directoryPath + File.separator + "todo.json";
    //intialize gson
    private static Gson gson = new Gson();

    public static void SaveToDoList(ArrayList<Task> todo) {
        // Ensure the directory exists
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();  // Creates the directory if it doesn't exist
        }

        try {
            FileWriter write = new FileWriter(filePath);

            gson.toJson(todo, write);
            write.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Task> LoadToDoList() {

        try {
            FileReader ToDoListPath = new FileReader(filePath);

            Type toDoList = new TypeToken<ArrayList<Task>>() {
            }.getType();

            ArrayList<Task> tasks = gson.fromJson(ToDoListPath, toDoList);

            return tasks;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }



    }


}

