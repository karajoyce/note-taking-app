package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class FoldersModel {
    private List<String> folders;

    public FoldersModel() {
        folders = new ArrayList<>();
        // Add some initial folders for demonstration
        folders.add("Math");
        folders.add("Science");
        folders.add("Physics");
    }

    public List<String> getFolders() {
        return folders;
    }

    public void addFolder(String folderName) {
        if (!folders.contains(folderName)) {
            folders.add(folderName);
        }
    }

    public void removeFolder(String folderName) {
        folders.remove(folderName);
    }
}
