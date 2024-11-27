package com.example.demo.model;

import org.fxmisc.richtext.InlineCssTextArea;

import java.io.Serializable;
import java.util.ArrayList;

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

    private ArrayList<Page> linkedPages;
    public Page(String pageTitle){
        title = pageTitle;
        contents = new InlineCssTextArea();
        linkedPages = new ArrayList<>();
    }

    public void setContents(InlineCssTextArea contents) {
        this.contents = contents;
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
    public ArrayList<Page> getLinkedPages(){
        return linkedPages;
    }
    public void addLink(Page page){
        if(!linkedPages.contains(page)){
            linkedPages.add(page);
        }
    }
    public void removeLink(Page page){
        if(linkedPages.contains(page)){
            linkedPages.remove(page);
        }
    }

}

