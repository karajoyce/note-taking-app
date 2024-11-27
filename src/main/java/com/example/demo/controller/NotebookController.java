package com.example.demo.controller;

import com.example.demo.FilerSystem.FlashcardStorage;
import com.example.demo.FilerSystem.NotesStorage;
import com.example.demo.model.Card;
import com.example.demo.model.Deck;
import com.example.demo.model.Notebook;
import com.example.demo.model.Page;
import com.example.demo.view.NewPageView;
import com.example.demo.view.NotebookScreenView;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**

 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Author: Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521

 **/

public class NotebookController {

    private Notebook noteModel;
    private NotebookScreenView noteView;

    public NotebookController(Notebook nModel, NotebookScreenView nView) {
        noteView = nView;
        noteModel = nModel;

        nView.setAddPage(e -> {
            new NewPageController(this, noteModel, new Stage());
        });
        nView.setChangeButton(e -> {
            NotesStorage.SaveNotes(noteModel);
            String newPage = ((Button)e.getSource()).getText();
            for (Page page: noteModel.getNotes()){
                if (page.getTitle().equals(newPage)){
                    nView.setCurrentPage(page);
                }
            }
            runUpdate();
        });
        nView.setDeletePage(e -> {
            int count = nModel.getNotes().size();
            nModel.getNotes().remove(nView.getCurrentPage());
            if (count == 1){
                Page page = new Page("Blank Page");
                nView.setCurrentPage(page);
                nModel.getNotes().add(page);

            } else {
                nView.setCurrentPage(nModel.getNotes().get(0));
            }
            NotesStorage.SaveNotes(nModel);
            runUpdate();
        });
        nView.setRenamePage(e -> {
            new NewNameController(this, nModel,new Stage());
            runUpdate();
        });
    }

    public void runUpdate(){
        noteView.runScreenUpdate();
    }

    public NotebookScreenView getNoteView(){
        return this.noteView;
    }
}
