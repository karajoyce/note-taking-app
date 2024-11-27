package com.example.demo.controller;

import com.example.demo.FilerSystem.FlashcardStorage;
import com.example.demo.FilerSystem.NotesStorage;
import com.example.demo.model.Notebook;
import com.example.demo.model.Page;
import com.example.demo.view.EditCardView;
import com.example.demo.view.NewNameView;
import com.example.demo.view.NewPageView;
import com.example.demo.view.NotebookScreenView;
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
    private NotebookController nController;
    private Notebook nModel;
    private NewNameView nView;

    public NewNameController(NotebookController controller, Notebook model, Stage stage){
        nModel = model;
        nController = controller;
        nView = new NewNameView(stage, this::changeName);
        stage.show();
    }

    public void changeName(){
        String title = nView.getTitle();
        System.out.println(title);
        nController.getNoteView().getCurrentPage().setTitle(title);
        NotesStorage.SaveNotes(nModel);
        nController.runUpdate();
    }
}
