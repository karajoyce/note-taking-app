/**
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 */

package com.example.demo.notes;

import javafx.scene.control.*;

import javafx.stage.Stage;


/**
 * The View class of the text editor (MVC Model).
 * Creates stuff to put on display (menus, tool bars, buttons)
 */
public class NoteView  {
    private NoteController noteController;

    public NoteView(NoteController controller) {
        noteController = controller;

        controller.noteModel.getTextArea().setPrefWidth(800);
        controller.noteModel.getTextArea().setPrefHeight(800);

    }

    protected MenuBar createMenuBar(Stage stage) {
        Menu fileMenu = new Menu("File");
        MenuItem openItem = new MenuItem("Open");
        MenuItem saveItem = new MenuItem("Save");
        fileMenu.getItems().addAll(openItem, saveItem);

        openItem.setOnAction(actionEvent -> noteController.openFile(stage));
        saveItem.setOnAction(actionEvent -> noteController.saveFile(stage));

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);

        return menuBar;
    }

    protected ToolBar createToolBar(Stage stage) {

        /* Font size */
        ComboBox<String> fontSizeMenu = new ComboBox<>();
        fontSizeMenu.getItems().addAll("10px", "11px", "12px", "14px", "18px", "24px", "30px", "36px", "48px");
        fontSizeMenu.setValue(noteController.noteModel.getFontsize());

        fontSizeMenu.setOnAction(actionEvent -> noteController.changeFontSize(fontSizeMenu.getValue()));

        /* Fonts */

        ComboBox<String>fontMenu = new ComboBox<>();
        fontMenu.getItems().addAll("Arial", "Times New Roman", "Courier New", "Comic Sans MS", "Verdana");
        fontMenu.setValue(noteController.noteModel.getFontType());
        fontMenu.setOnAction(actionEvent -> noteController.changeFontType(fontMenu.getValue()));

        /* Text alignment */
        Button alignLeftButton = new Button("Left");
        alignLeftButton.setOnAction(actionEvent -> noteController.setTextAlignment("left"));

        Button alignCenterButton = new Button("Center");
        alignCenterButton.setOnAction(actionEvent -> noteController.setTextAlignment("center"));

        Button alignRightButton = new Button("Right");
        alignRightButton.setOnAction(actionEvent -> noteController.setTextAlignment("right"));

        /* TEST: Toggle auto flashcards */
        Button autoFlashcardButton = new Button("Auto Flashcard Toggle");
        autoFlashcardButton.setOnAction(actionEvent -> noteController.toggleAutoFlashcard());

        ToolBar toolBar = new ToolBar(getToggleBoldButton(), getToggleItalicButton(), getToggleUnderlineButton(),
                getToggleStrikeThroughButton(),
                new Label("Font:"), fontMenu, new Label("Font size:"), fontSizeMenu,
                alignLeftButton, alignCenterButton, alignRightButton,
                autoFlashcardButton);

        return toolBar;
    }

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

        /* NOTE: for some reason strikethrough won't appear on the button */
        toggleStrikethroughButton.setStyle("-fx-strikethrough: true; -fx-font-family: Arial;");
        toggleStrikethroughButton.setOnAction(actionEvent -> noteController.toggleStrikethrough());

        return toggleStrikethroughButton;
    }


}
