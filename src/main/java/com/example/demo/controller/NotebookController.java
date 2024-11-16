package com.example.demo.controller;

import com.example.demo.model.Notebook;
import com.example.demo.view.NotebookScreenView;

public class NotebookController {

    private Notebook noteModel;
    private NotebookScreenView noteView;

    public NotebookController(Notebook nModel, NotebookScreenView nView) {
        noteView = nView;
        noteModel = nModel;
    }
}
