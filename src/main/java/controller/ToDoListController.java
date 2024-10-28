package controller;

/**
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 **/

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ToDoList;
import view.ToDoListView;

/**
 * Controls the main To-Do List application, managing the interaction
 * between the ToDoList model and the ToDoListView.
 */
public class ToDoListController  {

    private ToDoList toDoList; // The model representing the list of tasks
    private ToDoListView toDoListView; // The view for displaying the to-do list

    /**
     * Constructs a ToDoListController with the specified ToDoList and primary stage.
     *
     * @param toDoList The ToDoList instance that holds the tasks.
     * @param primaryStage The primary stage for the application where the to-do list view will be displayed.
     */
    public ToDoListController(ToDoList toDoList, ToDoListView toDoListView) {
        this.toDoList = toDoList;
        this.toDoListView = toDoListView;
        updateTaskListView(); // Populate the view with current tasks

        // Set the action for the "Add Task" button
        toDoListView.setAddTaskButtonAction(e -> {
            new TaskCreationController(toDoList, this, new Stage()); // Open task creation window
            updateTaskListView(); // Refresh the list after adding
        });
    }

    /**
     * Updates the task list view with the current list of tasks from the model.
     */
    public void updateTaskListView() {
        toDoListView.setTaskList(toDoList.getTasks());
    }
}
