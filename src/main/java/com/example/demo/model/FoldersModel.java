package com.example.demo.model;

import com.example.demo.FilerSystem.FolderStorage;
//import com.example.demo.FilerSystem.NotesStorage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoldersModel {
    private List<String> folders; // List of folder names
    private Map<String, Notebook> folderNotebooks; // Map of folder names to notebooks

    public FoldersModel() {
        folders = new ArrayList<>();
        folderNotebooks = new HashMap<>();

        // Load folder names from storage
        folders.addAll(FolderStorage.GenerateNotebookTitles());

        // Load notebooks for each folder
        for (String folderName : folders) {
            Notebook notebook = FolderStorage.LoadNotes(folderName);
            if (notebook != null) {
                folderNotebooks.put(folderName, notebook);
            }
        }

        // Initialize with some folders and their notebooks
        /*
        addFolder("Math");
        addFolder("Science");
        addFolder("Physics");

         */
    }

    public Map<String, Notebook> getFolderNotebooks() {
        return folderNotebooks;
    }

    public List<String> getFolders() {
        return folders;
    }

    public Notebook getNotebook(String folderName) {
        if (!folderNotebooks.containsKey(folderName)) {
            // Attempt to load from persistent storage
            Notebook loadedNotebook = FolderStorage.LoadNotes(folderName);
            if (loadedNotebook != null) {
                folderNotebooks.put(folderName, loadedNotebook);
            }
        }
        return folderNotebooks.get(folderName);
    }

    public void addFolder(String folderName) {
        if (!folderNotebooks.containsKey(folderName)) {
            folders.add(folderName); // Add to the list of folder names
            folderNotebooks.put(folderName, new Notebook(folderName)); // Map folder name to a new notebook
        }
    }

    public void removeFolder(String folderName) {
        if (folders.remove(folderName)) {
            System.out.println("Folder removed from model: " + folderName);
        } else {
            System.err.println("Failed to remove folder from model: " + folderName);
        }
        folderNotebooks.remove(folderName); // Remove the associated notebook
    }
}
