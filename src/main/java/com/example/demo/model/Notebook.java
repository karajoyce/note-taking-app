package com.example.demo.model;

import java.util.ArrayList;

/**

 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Author: Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521

 **/

public class Notebook {
    private ArrayList<Page> notes;
    private String title;

    public Notebook(String name) {
        title = name;
        notes = new ArrayList<>();
    }

    public ArrayList<Page> getNotes(){
        return notes;
    }
    public String getTitle(){
        return title;
    }

    public void setTitle(String name){
        title = name;
    }

    public void addPage(Page newPage){
        notes.add(newPage);
    }

    public void removePage(Page oldPage){
        this.notes.remove(oldPage);
    }

    public int getSize(){
        return notes.size();
    }

}


