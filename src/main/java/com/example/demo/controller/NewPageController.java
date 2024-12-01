package com.example.demo.controller;

import com.example.demo.FilerSystem.NotesStorage;
import com.example.demo.HelloApplication;
import com.example.demo.model.Notebook;
import com.example.demo.model.Page;
import com.example.demo.view.NewPageView;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**

 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Author: Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521

 **/

public class NewPageController {
    private NotebookController nController; // notebook controller
    private Notebook nModel; // notebook model
    private NewPageView nView; // view for the new page screen

    /**
     * Constructor to set up the notebook new page menu
     * @param controller = notebook controller
     * @param model = notebook model
     * @param stage = new stage to run the view, will run function addpage
     */
    public NewPageController(NotebookController controller, Notebook model, Stage stage){
        nModel = model;
        nController = controller;
        nView = new NewPageView(stage, this::addPage);
        stage.initStyle(StageStyle.UTILITY);
        stage.setFullScreen(false);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    /**
     * function to add a new page to the notebook
     */
    public void addPage(){
        String title = nView.getTitle();
        boolean found = false;
        // check if the page already exists
        for (Page page: nModel.getNotes()){
            if (page.getTitle().equals(title)){
                found = true;
                break;
            }
        }

        // if the page exists, alert the user to choose something else and pop up a new instance of newpage controller
        if (found){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Name already exists, please use another one.");
            alert.showAndWait();
            new NewPageController(nController, nModel, new Stage());
        } else { // otherwise continue with making the page
            Page newPage = new Page(title);
            nModel.addPage(newPage);
            NotesStorage.SaveNotes(nModel);
            nController.runUpdate();
            HelloApplication.getStage().setAlwaysOnTop(true);
        }
    }
}


