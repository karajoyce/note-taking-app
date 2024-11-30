package com.example.demo.controller;

/*CHANGES MADE BY NATHAN, CHANGING CODE BELOW TO FIX WARNING*/
//TOOK OUT UNSUSED IMPORT STATEMENTS
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
    /*CHANGES MADE BY NATHAN, CHANGING CODE BELOW TO FIX WARNING*/
    //CHANGED THINGS TO FINAL
    private final NotebookController nController;
    private final Notebook nModel;
    private final NewNameView nView;

    public NewNameController(NotebookController controller, Notebook model, Stage stage){
        nModel = model;
        nController = controller;
        nView = new NewNameView(stage, this::changeName);
        stage.setAlwaysOnTop(true);
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
