package com.example.demo.controller;

import com.example.demo.FilerSystem.NotesStorage;
import com.example.demo.model.Notebook;
import com.example.demo.view.NewNameView;
import javafx.stage.Stage;

/**

 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Author: Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521

 **/

public class NewNameController {
    private NotebookController nController; // instance of the notebook controller
    private Notebook nModel; // instance of the notebook model
    private NewNameView nView; // instance of the view for renaming


    /**
     * Constructor for the rename feature. will create a view that uses the changeName function
     * @param controller = notebook controller
     * @param model = notebook model
     * @param stage = new stage to display the view
     */
    public NewNameController(NotebookController controller, Notebook model, Stage stage){
        nModel = model;
        nController = controller;
        nView = new NewNameView(stage, this::changeName);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    /**
     * Function to change the name of the page based on the user input
     */
    public void changeName(){
        String title = nView.getTitle();
        nController.getNoteView().getCurrentPage().setTitle(title);
        NotesStorage.SaveNotes(nModel);
        nController.runUpdate();
    }
}
