package com.example.demo.controller;


import com.example.demo.FilerSystem.NotesStorage;

import com.example.demo.model.Notebook;
import com.example.demo.model.Page;
import com.example.demo.model.XPModel;

import com.example.demo.view.NotebookScreenView;
import com.example.demo.view.TagManagementView;
import javafx.scene.control.Button;

import com.example.demo.model.XPManager;

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
    private XPModel xpmodel;

    public NotebookController(Notebook nModel, NotebookScreenView nView) {
        noteView = nView;
        noteModel = nModel;
        this.xpmodel = XPManager.getXPModel();

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

        /*CHANGES BY NATHAN, ADDING THINGS TO MANAGE TAGS*/
        noteView.setManageTagsButtonAction(this::openTagManagementWindow);

    }
    /**CHANGES BY NATHAN, ADDING THINGS TO MANAGE TAGS*/
    /*Methods for Adding and Removing Tags from a Notebook*/
    public void addTagToNoteBook(String tag){
        if (tag != null && !tag.isBlank()){
            noteModel.addTag(tag);
            NotesStorage.SaveNotes(noteModel);
            runUpdate();
        }
    }

    public void removeTagFromNoteBook(String tag){
        noteModel.removeTag(tag);
        NotesStorage.SaveNotes(noteModel);
        runUpdate();
    }

    public void saveNotebookAfterTagsUpdated(){
        NotesStorage.SaveNotes(noteModel);
        noteView.updateDisplayedTags();
    }

    private void openTagManagementWindow(){
        Stage tagManagementStage = new Stage();
        TagManagementView tagManagementView = new TagManagementView(noteModel);

        tagManagementView.setOnTagsUpdated(this::saveNotebookAfterTagsUpdated);

        tagManagementStage.setTitle("Manage Tags");
        tagManagementStage.setScene(new javafx.scene.Scene(tagManagementView, 400, 300));
        tagManagementStage.show();
    }

    public void runUpdate(){
        noteView.runScreenUpdate();
    }

    public NotebookScreenView getNoteView(){
        return this.noteView;
    }
}
