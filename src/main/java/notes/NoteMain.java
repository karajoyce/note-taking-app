package notes;

import javafx.application.Application;
import javafx.stage.Stage;

public class NoteMain extends Application {
    private NoteModel model;
    private NoteController controller;
    private NoteView view;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        model = new NoteModel();
        controller = new NoteController(model);
        view = new NoteView(model);


    }
}
