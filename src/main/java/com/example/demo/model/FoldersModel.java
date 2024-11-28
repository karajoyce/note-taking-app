package com.example.demo.model;

//import com.example.demo.FilerSystem.FolderStorage;
import com.example.demo.FilerSystem.NotesStorage;
//import com.example.demo.FilerSystem.NotesStorage;
import java.util.ArrayList;
/*CHANGES BY NATHAN ADDING THIS THING*/
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoldersModel {
    private List<String> folders; // List of folder names
    private Map<String, Notebook> folderNotebooks; // Map of folder names to notebooks
    /**CHANGES BY NATHAN ADDING METADATA*/
    private Map<String, FolderMetaData> folderMetadata;

    public FoldersModel() {
        folders = new ArrayList<>();
        folderNotebooks = new HashMap<>();
        /**CHANGES BY NATHAN FOR FOLDER METADATA*/
        folderMetadata = new HashMap<>();

        // Load folder names from storage
        folders.addAll(NotesStorage.GenerateNotebookTitles());

        // Load notebooks for each folder
        for (String folderName : folders) {
            Notebook notebook = NotesStorage.LoadNotes(folderName);
            if (notebook != null) {
                folderNotebooks.put(folderName, notebook);
                /**CHANGES BY NATHAN FOLDER METADATA*/
                folderMetadata.put(folderName, new FolderMetaData(LocalDateTime.now(), new ArrayList<>()));
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
            Notebook loadedNotebook = NotesStorage.LoadNotes(folderName);
            if (loadedNotebook != null) {
                folderNotebooks.put(folderName, loadedNotebook);
                /**CHANGES BY NATHAN UPDATING FOLDER META DATA*/
                folderMetadata.put(folderName, new FolderMetaData(LocalDateTime.now(), new ArrayList<>()));
            }
        }
        return folderNotebooks.get(folderName);
    }

    /**CHANGES BY NATHAN METHODS FOR FOLDER META DATA*/
    public FolderMetaData getFolderMetadata(String folderName) {
        return folderMetadata.get(folderName);
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

    /**CHANGES BY NATHAN INNER CLASS FOR STORING FOLDER METADATA*/
    public static class FolderMetaData {
        private LocalDateTime creationDate;
        private List<String> tags;

        public FolderMetaData(LocalDateTime creationDate, List<String> tags) {
            this.creationDate = creationDate;
            this.tags = tags;
        }

        public LocalDateTime getCreationDate() {
            return creationDate;
        }

        public List<String> getTags(){
            return tags;
        }

        public void addTag(String tag) {
            if (!tags.contains(tag)) {
                tags.add(tag);
            }
        }

        public void removeTag(String tag) {
            tags.remove(tag);
        }
    }
}
