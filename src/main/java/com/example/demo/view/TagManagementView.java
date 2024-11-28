package com.example.demo.view;

import com.example.demo.model.Notebook;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TagManagementView extends VBox {

    private Notebook notebook; // Current notebook being managed
    private VBox displayedTags = new VBox(); // Display area for tags
    private TextField tagInputField = new TextField(); // Input for new tags
    private Button addTagButton = new Button("Add Tag"); // Button to add new tags

    // Listener for when tags are updated
    private Runnable onTagsUpdated;

    public TagManagementView(Notebook notebook) {
        this.notebook = notebook;

        setSpacing(10);
        setAlignment(Pos.CENTER);

        // Configure UI components
        initializeUI();

        // Populate initial tags
        updateDisplayedTags();
    }

    private void initializeUI() {
        tagInputField.setPromptText("Enter a tag...");
        addTagButton.setOnAction(e -> {
            String newTag = tagInputField.getText();
            if (!newTag.isBlank()) {
                notebook.addTag(newTag); // Add tag to notebook
                tagInputField.clear();
                updateDisplayedTags(); // Refresh display
                notifyTagsUpdated(); // Notify the controller
            }
        });

        getChildren().addAll(tagInputField, addTagButton, displayedTags);
    }

    private void updateDisplayedTags() {
        displayedTags.getChildren().clear(); // Clear existing tags

        for (String tag : notebook.getTags()) {
            HBox tagRow = new HBox();
            Label tagLabel = new Label(tag);
            Button removeButton = new Button("Remove");

            removeButton.setOnAction(e -> {
                notebook.removeTag(tag); // Remove tag from notebook
                updateDisplayedTags(); // Refresh display
                notifyTagsUpdated(); // Notify the controller
            });

            tagRow.getChildren().addAll(tagLabel, removeButton);
            tagRow.setSpacing(10);
            displayedTags.getChildren().add(tagRow);
        }
    }

    // Notify the controller when tags are updated
    private void notifyTagsUpdated() {
        if (onTagsUpdated != null) {
            onTagsUpdated.run(); // Trigger the listener
        }
    }

    // Set the listener for tags being updated
    public void setOnTagsUpdated(Runnable listener) {
        this.onTagsUpdated = listener;
    }
}

