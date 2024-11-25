package com.example.demo.view;

import com.example.demo.model.Task;
import com.example.demo.model.XPModel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import com.example.demo.model.TaskItem;
import javafx.stage.Screen;

import java.util.ArrayList;

/**
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 **/

public class ToDoListView extends VBox {

    private ListView<HBox> taskListView;
    private Button addTaskButton;
    private ArrayList<Task> tasks;
    public GridPane grid;// Maintain the list of tasks



    /**
     * Constructs a ToDoListView that sets up the user interface
     * for the to-do list application.
     *.
     */
    public ToDoListView() {

        tasks = new ArrayList<>(); // Initialize the tasks list


        // Layout
        grid = new GridPane();
        grid.setPadding(new javafx.geometry.Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);
        grid.setMinHeight((Screen.getPrimary().getBounds().getMaxY()-100)*0.5);
        grid.setMinWidth((Screen.getPrimary().getBounds().getMaxY()-100)*0.25);

        // List View
        taskListView = new ListView<>();

        grid.getColumnConstraints().add(new ColumnConstraints(25));
        grid.getColumnConstraints().add(new ColumnConstraints(50));
        grid.getColumnConstraints().add(new ColumnConstraints(150));
        grid.getColumnConstraints().add(new ColumnConstraints(50));
        grid.getColumnConstraints().add(new ColumnConstraints(25));
        grid.getRowConstraints().add(new RowConstraints(325));
        grid.getRowConstraints().add(new RowConstraints(50));

        GridPane.setConstraints(taskListView, 0, 0);
        taskListView.setMinHeight(325);
        GridPane.setColumnSpan(taskListView, 5);

        // Add Task Button
        addTaskButton = new Button("Add Task");
        addTaskButton.getStyleClass().add("xpbar");
        addTaskButton.setAlignment(Pos.CENTER);
        GridPane.setConstraints(addTaskButton, 2, 1);

        grid.setAlignment(Pos.CENTER);

        // Set up the layout
        grid.getChildren().addAll(taskListView, addTaskButton);

    }

    /**
     * Updates the task list view with the provided list of tasks.
     *
     * @param tasks An ArrayList of Task objects to display in the task list view.
     */
    public void setTaskList(ArrayList<Task> tasks, XPModel xpModel) {
        this.tasks = tasks;
        taskListView.getItems().clear();
        for (Task task : tasks) {
            TaskItem taskItem = new TaskItem(task, xpModel, e -> {
                // Handle delete action using the task object
                deleteTask(task);
            });

            // Customize each item's HBox style
            HBox itemView = taskItem.getView();
            itemView.setStyle("-fx-background-color: lightblue; -fx-padding: 10; -fx-background-radius: 5;");


            taskListView.getItems().add(taskItem.getView()); // Add the HBox view to the list

        }
    }

    /**
     * Clears all tasks from the task list view.
     */
    public void clearTaskList() {
        taskListView.getItems().clear();
    }

    /**
     * Sets the action to be performed when the "Add Task" button is clicked.
     *
     * @param handler An EventHandler that defines the action to be executed on button click.
     */
    public void setAddTaskButtonAction(javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        addTaskButton.setOnAction(handler);
    }

    /**
     * Retrieves the ListView of tasks.
     *
     * @return The ListView<Task> instance representing the task list.
     */
    public ListView<HBox> getTaskListView() {
        return taskListView;
    }

    /**
     * Deletes the specified task from the task list view.
     *
     * @param task The task to be deleted.
     */
    private void deleteTask(Task task) {
        // Remove the task from the underlying list
        tasks.remove(task);

        // Find the corresponding TaskItem view and remove it from the taskListView
        for (HBox hBox : taskListView.getItems()) {
            TaskItem item = (TaskItem) hBox.getUserData(); // to set user data in TaskItem
            if (item != null && item.getTask().equals(task)) {
                taskListView.getItems().remove(hBox);
                break;
            }
        }
    }

    public GridPane getToDoListView() {
        return grid;
    }

}
