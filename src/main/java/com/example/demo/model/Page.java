package com.example.demo.model;

import org.fxmisc.richtext.InlineCssTextArea;

import java.io.Serializable;

/**

 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Author: Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521

 **/

public class Page implements Serializable {

    private String title;
    private InlineCssTextArea contents;
    public Page(String pageTitle){
        contents = new InlineCssTextArea();
        title = pageTitle;
    }

    public void setContents(InlineCssTextArea contentArea){
        contents = contentArea;
    }
    public InlineCssTextArea getContents(){
        return contents;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String name){
        title = name;
    }

}
