package com.example.demo.controller;

import com.example.demo.model.Card;
import com.example.demo.model.Notebook;
import com.example.demo.view.NotebookScreenView;
import javafx.stage.Stage;

public class NotebookController {

    private Notebook noteModel;
    private NotebookScreenView noteView;

    public NotebookController(Notebook nModel, NotebookScreenView nView) {
        noteView = nView;

        nView.setAddPage(e -> {

        });
    }
}
