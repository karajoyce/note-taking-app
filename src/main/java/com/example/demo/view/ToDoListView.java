package com.example.demo.view;

import com.example.demo.model.Task;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import com.example.demo.model.TaskItem;
import javafx.stage.Screen;

import java.time.LocalDate;
import java.time.ZoneId;
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

        // List View
        taskListView = new ListView<>();

        // Add Task Button
        addTaskButton = new Button("Add Task");
        HBox buttonContainer = new HBox(addTaskButton);
        buttonContainer.setAlignment(Pos.CENTER);

        // Set up the main VBox layout
        this.setSpacing(10); // Adjust spacing as needed
        this.setAlignment(Pos.TOP_CENTER); // Align items to the center of the VBox
        this.getChildren().addAll(taskListView, buttonContainer); // Add the list and centered button

    }

    /**
     * Updates the task list view with the provided list of tasks.
     *
     * @param tasks An ArrayList of Task objects to display in the task list view.
     */
    public void setTaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
        taskListView.getItems().clear();
        for (Task task : tasks) {
            TaskItem taskItem = new TaskItem(task, e -> {
                // Handle delete action using the task object
                deleteTask(task);
            });

            // Customize each item's HBox style
            HBox itemView = taskItem.getView();

            // Check if the task's due date has passed
            LocalDate dueDate = LocalDate.ofEpochDay(task.getTaskDueDate() / (1000 * 60 * 60 * 24));
            LocalDate today = LocalDate.now(ZoneId.systemDefault());

            if (dueDate.isBefore(today)) {
                // Set red background for overdue tasks
                itemView.setStyle("-fx-background-color: rgba(255, 0, 0, 0.5); -fx-padding: 10; -fx-background-radius: 5;");
            } else {
                // Set a default background for non-overdue tasks
                itemView.setStyle("-fx-background-color: lightblue; -fx-padding: 10; -fx-background-radius: 5;");
            }


            //taskListView.getItems().add(taskItem.getView());
            taskListView.getItems().add(itemView);// Add the HBox view to the list

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

    public VBox getToDoListView() {
        return this;
    }

}
