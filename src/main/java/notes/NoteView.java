/**
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 */

package notes;

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
        Button toggleBoldButton = new Button("B");
        toggleBoldButton.setStyle("-fx-font-weight: bold;");
        toggleBoldButton.setOnAction(actionEvent -> noteController.toggleBold());

        Button toggleItalicButton = new Button("I");
        toggleItalicButton.setStyle("-fx-font-style: italic;");
        toggleItalicButton.setOnAction(actionEvent -> noteController.toggleItalic());

        Button toggleUnderlineButton = new Button("U");
        toggleUnderlineButton.setStyle("-fx-font-underline: true;");
        toggleUnderlineButton.setOnAction(actionEvent -> noteController.toggleUnderline());

        ToolBar toolBar = new ToolBar(toggleBoldButton, toggleItalicButton, toggleUnderlineButton);
        return toolBar;
    }


}
