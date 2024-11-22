package com.example.demo.model;

import org.fxmisc.richtext.InlineCssTextArea;

public class Page {

    private String title;
    private InlineCssTextArea contents;
    public Page(String pageTitle){
        contents = new InlineCssTextArea();
        title = pageTitle;
    }

}
