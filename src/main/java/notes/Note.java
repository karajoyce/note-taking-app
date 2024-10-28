/**
CMPT 370, T05, Team 4, Prof. Jon Lovering
Kara Leier, kjl061, 11293306
Nathan Balilis, ncb421, 11295020
Trushank Lakdawala, nus429, 11350445
Jinny Kim, yek738, 11304174
Sara Shakeel, gvk731, 11367521
 */

package notes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Note extends Application {

    private CodeArea codeArea = new CodeArea();

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void openFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Text File");
        File file = fileChooser.showOpenDialog(stage);

        try {
            if (file != null) {
                String content = Files.readString(Path.of(file.getPath()));
                codeArea.replaceText(content);
            }
        } catch (IOException e) {
                showError("Error opening file", e.getMessage());
        }
    }

    private void saveFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Text File");
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try {
                Files.writeString(Path.of(file.getPath()), codeArea.getText(), StandardOpenOption.CREATE);
            } catch (IOException e) {
                showError("Error saving file", e.getMessage());
            }
        }
    }

    private MenuBar createMenuBar(Stage stage) {
        Menu fileMenu = new Menu("File");
        MenuItem openItem = new MenuItem("Open");
        MenuItem saveItem = new MenuItem("Save");
        fileMenu.getItems().addAll(openItem, saveItem);

        openItem.setOnAction(actionEvent -> openFile(stage));
        saveItem.setOnAction(actionEvent -> saveFile(stage));

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);

        return menuBar;
    }

    @Override
    public void start(Stage stage) throws Exception {

        codeArea.setPrefHeight(800);
        codeArea.setPrefWidth(600);

        MenuBar menuBar = createMenuBar(stage);

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(new ScrollPane(codeArea));

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Test");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}