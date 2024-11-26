package com.example.demo.controller;

import com.example.demo.model.Notebook;
import com.example.demo.model.Page;
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

public class NotebookController {

    private Notebook noteModel;
    private NotebookScreenView noteView;

    public NotebookController(Notebook nModel, NotebookScreenView nView) {
        noteView = nView;
        noteModel = nModel;

        nView.setAddPage(e -> {
            System.out.println("here");
            new NewPageController(this, noteModel, new Stage());
        });
    }

    public void runUpdate(){
        noteView.runScreenUpdate();
    }
}
