package com.example.demo.controller;

import com.example.demo.FilerSystem.FlashcardStorage;
import com.example.demo.model.Notebook;
import com.example.demo.model.Page;
import com.example.demo.view.EditCardView;
import com.example.demo.view.NewPageView;
import com.example.demo.view.NotebookScreenView;
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
    private NotebookController nController;
    private Notebook nModel;
    private NewPageView nView;

    public NewPageController(NotebookController controller, Notebook model, Stage stage){
        nModel = model;
        nController = controller;
        nView = new NewPageView(stage, this::addPage);
        stage.initStyle(StageStyle.UTILITY);
        stage.setFullScreen(false);
        stage.setResizable(false);
        stage.show();
    }

    public void addPage(){
        String title = nView.getTitle();
        Page newPage = new Page(title);
        nModel.addPage(newPage);


        nController.runUpdate();
//        FlashcardStorage.SaveNotes(nModel.getNotes());
    }
}
