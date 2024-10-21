package model;

/**
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 **/

import controller.ToDoListController;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainUI extends Application {
    @Override
    public void start(Stage primaryStage) {
        ToDoList toDoList = new ToDoList();
        new ToDoListController(toDoList, primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
