/**
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 */

package com.example.demo.notes;

import com.example.demo.model.Notebook;
import com.example.demo.model.Page;
import javafx.scene.control.*;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * The View class of the text editor (MVC Model).
 * Creates stuff to put on display (menus, tool bars, buttons)
 */
public class NoteView  {
    private NoteController noteController;
    private TextField tagInputField = new TextField();
    private Button addTagButton = new Button("Add Tag");
    private VBox tagContainer = new VBox();
    private TextField searchField = new TextField();
    private Button searchButton = new Button("Search");

    private Notebook notebook;

    /**
     * Constructor class for the NoteView object.
     * @param controller the note controller to set actions for some parts of the view
     */
    public NoteView(NoteController controller) {
        noteController = controller;
        controller.noteModel.getTextArea().setPrefWidth(800);
        controller.noteModel.getTextArea().setPrefHeight(800);

    }

    /**
     * Menu bar that saves/opens files into the computer's file system
     * @param stage the application stage
     * @return the menu bar with Open/Save functions
     */
    public MenuBar createMenuBar(Stage stage) {
        Menu fileMenu = new Menu("File"); //dropdown
        MenuItem openItem = new MenuItem("Open");
        MenuItem saveItem = new MenuItem("Save");
        fileMenu.getItems().addAll(openItem, saveItem);

        // Set up the buttons
        openItem.setOnAction(actionEvent -> noteController.openFile(stage));
        saveItem.setOnAction(actionEvent -> noteController.saveFile(stage));

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);

        return menuBar;
    }

    /**
     * Tool bar for all the rich text editing functions
     * @return
     */
    public ToolBar createToolBar() {
    /*
        Button toggleHyperLink = new Button("HL");
        toggleHyperLink.getStyleClass().add("hyperLinkButton");
        toggleHyperLink.setStyle("-fx-font-weight: bold;");;
        toggleHyperLink.setOnAction( actionEvent -> {

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Create Hyperlink");
            dialog.setHeaderText("Enter Target Page");
            dialog.setContentText("Target Page:");

            Optional<String> result = dialog.showAndWait();

            result.ifPresent(pos -> {
                Page target = Page.getPageFromTitle(pos);
                if(!pos.isEmpty() && target.getTitle().equals(pos)){


                    noteController.createHyperLink(target);

                }else{

                    System.err.println("Target Page Title does not exist");
                }
            });
        });
*/
        /* Font style formatting buttons */
        Button toggleBoldButton = new Button("B");
        toggleBoldButton.getStyleClass().add("textEditorButton");
        toggleBoldButton.setStyle("-fx-font-weight: bold;");
        toggleBoldButton.setOnAction(actionEvent -> noteController.toggleBold());

        Button toggleItalicButton = new Button("I");
        toggleItalicButton.getStyleClass().add("textEditorButton");
        toggleItalicButton.setStyle("-fx-font-style: italic; -fx-font-family: Arial, Helvetica, sans-serif;");
        toggleItalicButton.setOnAction(actionEvent -> noteController.toggleItalic());

        Button toggleUnderlineButton = new Button("U");
        toggleUnderlineButton.getStyleClass().add("textEditorButton");
        toggleUnderlineButton.setStyle("-fx-underline: true;");
        toggleUnderlineButton.setOnAction(actionEvent -> noteController.toggleUnderline());

        Button toggleStrikethroughButton = new Button("S");
        toggleStrikethroughButton.getStyleClass().add("textEditorButton");
        toggleStrikethroughButton.setStyle("-fx-strikethrough: true; -fx-font-family: Arial;"); /* For some reason strikethrough doesn't show up on the button */
        toggleStrikethroughButton.setOnAction(actionEvent -> noteController.toggleStrikethrough());


        /* Font size */
        ComboBox<String> fontSizeMenu = new ComboBox<>();
        fontSizeMenu.getStyleClass().add("fontmenu");
        fontSizeMenu.getItems().addAll("10px", "11px", "12px", "14px", "18px", "24px", "30px", "36px", "48px");
        fontSizeMenu.setValue(noteController.noteModel.getFontsize());

        fontSizeMenu.setOnAction(actionEvent -> noteController.changeFontSize(fontSizeMenu.getValue()));

        /* Fonts */

        ComboBox<String>fontMenu = new ComboBox<>();
        fontMenu.getStyleClass().add("fontmenu");
        fontMenu.getItems().addAll("Arial", "Times New Roman", "Courier New", "Comic Sans MS", "Verdana");
        fontMenu.setValue(noteController.noteModel.getFontType());
        fontMenu.setOnAction(actionEvent -> noteController.changeFontType(fontMenu.getValue()));

        /* Text alignment
         * Replace the text to images? */
        Button alignLeftButton = new Button("Left");
        alignLeftButton.getStyleClass().add("textEditorButton");
        alignLeftButton.setOnAction(actionEvent -> noteController.setTextAlignment("left"));

        Button alignCenterButton = new Button("Center");
        alignCenterButton.getStyleClass().add("textEditorButton");
        alignCenterButton.setOnAction(actionEvent -> noteController.setTextAlignment("center"));

        Button alignRightButton = new Button("Right");
        alignRightButton.getStyleClass().add("textEditorButton");
        alignRightButton.setOnAction(actionEvent -> noteController.setTextAlignment("right"));

        /* Toggle auto flashcards */
        Button autoFlashcardButton = new Button("Auto Flashcards: OFF");
        autoFlashcardButton.setOnAction(actionEvent -> {
            noteController.noteModel.toggleAutoFlashcard();
            if (noteController.noteModel.isAutoFlashcardEnabled()) {
                autoFlashcardButton.setText("Auto Flashcards: ON");
            } else {
                autoFlashcardButton.setText("Auto Flashcards: OFF");
            }
        });

        ToolBar toolBar = new ToolBar(getToggleBoldButton(), getToggleItalicButton(), getToggleUnderlineButton(),
                getToggleStrikeThroughButton(),
                new Label("Font:"), fontMenu, new Label("Font size:"), fontSizeMenu,
                alignLeftButton, alignCenterButton, alignRightButton,
                autoFlashcardButton);

        return toolBar;
    }

    private void initializeTagsSection(){
        HBox tagInputSection = new HBox(tagInputField, addTagButton);
        tagContainer.getChildren().add(tagInputSection);

        addTagButton.setOnAction(actionEvent -> {
           String tag = tagInputField.getText();
           if (!tag.isEmpty()) {
               noteController.addTagNote(tag);
               tagInputField.clear();
           }
        });

    }

    public void displayTags(Set<String> tags){
        tagContainer.getChildren().clear();
        initializeTagsSection();
        for (String tag: tags){
            Label tagLabel = new Label(tag);
            Button removeButton = new Button("Remove");
            removeButton.setOnAction(e -> noteController.removeTagFromNote(tag));

            HBox tagItem = new HBox(tagLabel, removeButton);
            tagContainer.getChildren().add(tagItem);
        }
    }

    private void initializeSearchSection(){
        HBox searchSection = new HBox(searchField,searchButton);

        searchButton.setOnAction(actionEvent -> {
            String keyword = searchField.getText();
            if(!keyword.isEmpty()){
                boolean found = noteController.searchNoteByKeyword(keyword);
                if (found) {
                    System.out.println("keyword found");
                }
                else{
                    System.out.println("keyword not found");
                }
            }
        });
    }
    /** Getter functions for the style/formatting buttons */
    protected Button getToggleBoldButton() {
        Button toggleBoldButton = new Button("B");
        toggleBoldButton.setStyle("-fx-font-weight: bold;");
        toggleBoldButton.setOnAction(actionEvent -> noteController.toggleBold());

        return toggleBoldButton;
    }

    protected Button getToggleItalicButton() {
        Button toggleItalicButton = new Button("I");
        toggleItalicButton.setStyle("-fx-font-style: italic; -fx-font-family: Arial, Helvetica, sans-serif;");
        toggleItalicButton.setOnAction(actionEvent -> noteController.toggleItalic());

        return toggleItalicButton;
    }

    protected Button getToggleUnderlineButton() {
        Button toggleUnderlineButton = new Button("U");
        toggleUnderlineButton.setStyle("-fx-underline: true;");
        toggleUnderlineButton.setOnAction(actionEvent -> noteController.toggleUnderline());

        return toggleUnderlineButton;
    }

    protected Button getToggleStrikeThroughButton() {
        Button toggleStrikethroughButton = new Button("S");

        /* NOTE: the strikethrough won't appear on the button, but it works for everything else. */
        toggleStrikethroughButton.setStyle("-fx-strikethrough: true; -fx-font-family: Arial;");
        toggleStrikethroughButton.setOnAction(actionEvent -> noteController.toggleStrikethrough());

        return toggleStrikethroughButton;
    }



}
