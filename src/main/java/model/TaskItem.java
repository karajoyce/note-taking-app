package model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 **/


/**
 * Represents a single task item in a to-do list application.
 * Each task item includes a checkbox to indicate completion and
 * a label displaying the task's description and due date.
 */
public class TaskItem {

    private CheckBox checkBox; // Checkbox for task completion
    private Label label; // Label to display task description and due date
    private ImageView deleteIcon; // ImageView for the trash icon
    private Task task; // Store reference to Task

    private static final String TRASH_ICON_PATH = "deleteIcon.png"; // Update with the correct path




    /**
     * Constructs a TaskItem with the specified task.
     *
     * @param task The Task object containing the task's details.
     */
    public TaskItem(Task task, EventHandler<ActionEvent> deleteHandler) {
        this.task = task;
        checkBox = new CheckBox();
        checkBox.setSelected(task.isCompleted());
        label = new Label(task.getTaskDescription() + " (Due: " + formatDueDate(task.getTaskDueDate()) + ")");

        // Set action for the checkbox to toggle task completion
        checkBox.setOnAction(e -> {
            task.toggleCompleted(); // Toggle task completion status
            label.setText(task.getTaskDescription() + " (Due: " + formatDueDate(task.getTaskDueDate()) + ")");
        });

        // Create delete icon
        InputStream iconStream = getClass().getClassLoader().getResourceAsStream(TRASH_ICON_PATH);
        if (iconStream == null) {
            System.err.println("Error: Image resource not found!");
            deleteIcon = new ImageView(); // Handle missing icon scenario
        } else {
            deleteIcon = new ImageView(new Image(iconStream));
        }

        deleteIcon.setFitHeight(20); // Set the height of the icon
        deleteIcon.setFitWidth(20); // Set the width of the icon
        deleteIcon.setOnMouseClicked(e -> {
            deleteHandler.handle(new ActionEvent()); // Trigger delete action
        });

        // Set button ID or any additional properties to identify the task for deletion
        deleteIcon.setUserData(this);
    }

    /**
     * Returns the view of the task item as an HBox.
     * The HBox contains the checkbox and label for the task.
     *
     * @return An HBox containing the checkbox and label.
     */
    public HBox getView() {
        HBox hBox = new HBox(checkBox, label, deleteIcon);
        hBox.setSpacing(10);
        hBox.setUserData(this); // Set the TaskItem as user data for later reference
        return hBox;
    }

    public Task getTask() {
        return task; // Provide access to the Task object
    }

    /**
     * Formats the due date from milliseconds to a string in the format "dd/MM/yyyy".
     *
     * @param dueDateMillis The due date in milliseconds since epoch.
     * @return A formatted string representation of the due date.
     */
    private String formatDueDate(long dueDateMillis) {
        LocalDate dueDate = LocalDate.ofEpochDay(dueDateMillis / (1000 * 60 * 60 * 24));
        return dueDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
