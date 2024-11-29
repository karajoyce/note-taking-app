package com.example.demo.model;

import java.io.Serializable;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.time.LocalDateTime;
/**

 CMPT 370, T05, Team 4, Prof. Jon Lovering
 Author: Kara Leier, kjl061, 11293306
 Nathan Balilis, ncb421, 11295020
 Trushank Lakdawala, nus429, 11350445
 Jinny Kim, yek738, 11304174
 Sara Shakeel, gvk731, 11367521

 **/

public class Notebook implements Serializable {
    private ArrayList<Page> notes;
    private String title;
    /* Changes By Nathan, adding fields
    * one being an ArrayList Checking Tags
    * and the other grabbing the time a Notebook is created*/
    private ArrayList<String> tags;
    private LocalDateTime creationDate;

    public Notebook(String name) {
        title = name;
        notes = new ArrayList<>();
        /*Changes by Nathan, initializing tags and
        * the creation date*/
        tags = new ArrayList<>();
        creationDate = LocalDateTime.now();
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

    /**Changes By Nathan, getter and setter methods for tags, and creation Date*/
    /**SETTERS AND GETTERS AND OTHER METHODS FOR TAGS*/
    /*Getter for tags, pretty obv*/
    public ArrayList<String> getTags() {
        return tags;
    }

    /*Setter for tags(this overwrites the current tags tho*/
    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    /*Method for adding a SINGLE tag*/
    public void addTag(String tag){
        if(!tags.contains(tag)){
            tags.add(tag);
        }
        //Print statement for debugging
        else{
            System.out.println("The tag "+tag+" is already found in tags");
        }
    }
    /*Method for Removing a single Tag*/
    public void removeTag(String tag){
        tags.remove(tag);
    }

    /**SETTER AND GETTTER METHODS FOR CREATION DATE*/
    public LocalDateTime getCreationDate(){
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate){
        this.creationDate = creationDate;
    }
}
