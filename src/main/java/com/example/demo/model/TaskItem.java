package com.example.demo.model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


/*
 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521
 */


/**
 * Represents a single task item in a to-do list application.
 * Each task item includes a checkbox to indicate completion and
 * a label displaying the task's description and due date.
 */
public class TaskItem {

    private CheckBox checkBox; // Checkbox for task completion
    private Label label; // Label to display task description and due date
    private ImageView deleteIcon; // ImageView for the trash icon
    private ImageView reminderIcon; // ImageView for the reminder (bell) icon
    private Task task; // Store reference to Task
    private XPModel xpModel;
    private static final String TRASH_ICON_PATH = "deleteIcon.png"; // Update with the correct path
    private static final String BELL_ICON_PATH = "bellIcon.png"; // Path for bell icon




    /**
     * Constructs a TaskItem with the specified task.
     *
     * @param task The Task object containing the task's details.
     */
    public TaskItem(Task task, EventHandler<ActionEvent> deleteHandler) {
        this.task = task;
        checkBox = new CheckBox();
        checkBox.setSelected(task.isCompleted());

        // Add the custom CSS style class to the CheckBox
        checkBox.getStyleClass().add("checkbox");

        label = new Label(task.getTaskDescription() + " (Due: " + formatDueDate(task.getTaskDueDate()) + ")");

        // Add the custom CSS style class to the Label
        label.getStyleClass().add("label");

        // Set action for the checkbox to toggle task completion
        checkBox.setOnAction(e -> {
            task.toggleCompleted(); // Toggle task completion status
            //Add implementation that gives more xp anytime this happens
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

        // Create reminder icon if the task is due soon
        reminderIcon = createReminderIcon();

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
        HBox hBox = new HBox(checkBox, label);
        hBox.setSpacing(10);
        hBox.setUserData(this); // Set the TaskItem as user data for later reference

        // Set the background color of the HBox to pink
        hBox.setStyle("-fx-background-color: pink; -fx-padding: 10; -fx-background-radius: 5;");

        // Allow the label to grow and take up remaining space, pushing delete icon to the right
        HBox.setHgrow(label, Priority.ALWAYS);

        // Create a Region to push the delete icon to the far right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Add the spacer to the HBox
        hBox.getChildren().addAll(spacer);

        // Check if reminderIcon is not null before adding
        if (reminderIcon != null) {
            hBox.getChildren().add(reminderIcon);
        }

        // Add the delete icon last
        hBox.getChildren().add(deleteIcon);

        return hBox;
    }

    public Task getTask() {
        return task; // Provide access to the Task object
    }

    /**
     * Creates the reminder icon if the task is due within 5 days.
     *
     * @return An ImageView for the reminder icon, or null if not due soon.
     */
    private ImageView createReminderIcon() {
        long dueDateMillis = task.getTaskDueDate();
        LocalDate dueDate = LocalDate.ofEpochDay(dueDateMillis / (1000 * 60 * 60 * 24));
        long daysUntilDue = ChronoUnit.DAYS.between(LocalDate.now(), dueDate);

        if (daysUntilDue >= 0 && daysUntilDue <= 5) {
            InputStream bellStream = getClass().getClassLoader().getResourceAsStream(BELL_ICON_PATH);
            if (bellStream != null) {
                ImageView bellIcon = new ImageView(new Image(bellStream));
                bellIcon.setFitHeight(20);
                bellIcon.setFitWidth(20);
                return bellIcon; // Return the bell icon if the task is due soon
            } else {
                System.err.println("Error: Image resource for bell icon not found!");
            }
        }
        return null; // Return null if no reminder icon is needed
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
