package com.example.demo.model;


import com.example.demo.FilerSystem.NotesStorage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 */


/**
 * Represents the model for managing folders and their associated notebooks.
 * This class provides methods to retrieve, add, and remove folders, as well as
 * access the notebooks contained within them. Data is loaded and saved through
 * persistent storage using `NotesStorage`.
 */
public class FoldersModel {
    private List<String> folders; // List of folder names
    private Map<String, Notebook> folderNotebooks; // Map of folder names to notebooks

    /**
     * Constructs a new `FoldersModel` and initializes folder data.
     * Folders and their associated notebooks are loaded from persistent storage.
     */
    public FoldersModel() {
        folders = new ArrayList<>();
        folderNotebooks = new HashMap<>();

        // Load folder names from storage
        folders.addAll(NotesStorage.GenerateNotebookTitles());

        // Load notebooks for each folder
        for (String folderName : folders) {
            Notebook notebook = NotesStorage.LoadNotes(folderName);
            if (notebook != null) {
                folderNotebooks.put(folderName, notebook);
            }
        }

    }

    /**
     * Retrieves the list of folder names.
     *
     * @return A `List` of folder names as strings.
     */
    public List<String> getFolders() {
        return folders;
    }

    /**
     * Retrieves the notebook associated with a specific folder.
     * If the notebook is not currently loaded, it attempts to load it from persistent storage.
     *
     * @param folderName The name of the folder.
     * @return The `Notebook` associated with the folder, or `null` if it cannot be found or loaded.
     */
    public Notebook getNotebook(String folderName) {
        if (!folderNotebooks.containsKey(folderName)) {
            // Attempt to load from persistent storage
            Notebook loadedNotebook = NotesStorage.LoadNotes(folderName);
            if (loadedNotebook != null) {
                folderNotebooks.put(folderName, loadedNotebook);
            }
        }
        return folderNotebooks.get(folderName);
    }

    /**
     * Adds a new folder to the model.
     * Creates a new notebook for the folder and maps it in the `folderNotebooks` collection.
     *
     * @param folderName The name of the new folder.
     */
    public void addFolder(String folderName) {
        if (!folderNotebooks.containsKey(folderName)) {
            folders.add(folderName); // Add to the list of folder names
            folderNotebooks.put(folderName, new Notebook(folderName)); // Map folder name to a new notebook
        }
    }

    /**
     * Removes a folder from the model.
     * Deletes the folder from the list and removes its associated notebook from the mapping.
     *
     * @param folderName The name of the folder to remove.
     */
    public void removeFolder(String folderName) {
        if (folders.remove(folderName)) {
            System.out.println("Folder removed from model: " + folderName);
        } else {
            System.err.println("Failed to remove folder from model: " + folderName);
        }
        folderNotebooks.remove(folderName); // Remove the associated notebook
    }
}
