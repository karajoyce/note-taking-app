package com.example.demo.FilerSystem;

import com.example.demo.model.Task;
import com.example.demo.model.ToDoList;
import com.google.gson.Gson;
/*CHANGES MADE BY NATHAN, CHANGING CODE BELOW TO FIX WARNING*/
//REMOVED UNUSED IMPORTS
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ToDoStorage {

    /*CHANGES MADE BY NATHAN, CHANGING CODE BELOW TO FIX WARNING*/
    //CHANGED THINGS TO FINAL
    //file path needed to put the flashcard under a file.
    private static final String directoryPath = "StorageJSONS";
    private static final String filePath = directoryPath + File.separator + "todo.json";
    //intialize gson
    private static final Gson gson = new Gson();

    public static void SaveToDoList(ArrayList<Task> todo) {
        // Ensure the directory exists
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            /*FROM NATHAN WEIRD ERROR THAT DRK HOW TO FIX*/
            //again with this, I don't think we should take it out but idrk how to fix it
            directory.mkdirs();  // Creates the directory if it doesn't exist
        }

        try {
            FileWriter write = new FileWriter(filePath);

            gson.toJson(todo, write);
            write.flush();
            write.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ToDoList LoadToDoList() {

        try {
            FileReader ToDoListPath = new FileReader(filePath);

            Type toDoList = new TypeToken<ArrayList<Task>>() {
            }.getType();

            ArrayList<Task> tasks = gson.fromJson(ToDoListPath, toDoList);

            ToDoListPath.close();
            return new ToDoList(tasks);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
            /*FROM NATHAN WEIRD ERROR THAT DRK HOW TO FIX*/
            //WHY COLLAPSE THE CATCH STUFF? COMMENTING THIS FIXES IT!?!?!?!?
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}

