package com.example.demo.model;

import java.io.Serializable;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Locale;

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
    /**CHANGES BY NATHAN FOR LAST ACCESSED FOLDER*/
    private LocalDateTime lastAccessed;

    /**
     * set up the notebook
     * @param name the title of the notebook
     */
    public Notebook(String name) {
        title = name;
        notes = new ArrayList<>();
        /*Changes by Nathan, initializing tags and
        * the creation date*/
        tags = new ArrayList<>();
        creationDate = LocalDateTime.now();
        lastAccessed = LocalDateTime.now();
    }

    /**
     * get the list of pages
     * @return the list of pages
     */
    public ArrayList<Page> getNotes(){
        return notes;
    }

    /**
     * @return the title
     */
    public String getTitle(){
        return title;
    }

    /**
     * re-title the notebook
     * @param name new title
     */
    public void setTitle(String name){
        title = name;
    }

    /**
     * add a new page to the notebook
     * @param newPage new page to be added
     */
    public void addPage(Page newPage){
        notes.add(newPage);
        updateLastAccessed();
    }

    /**
     * remove a page from the notebook
     * @param oldPage  page to be removed
     */
    public void removePage(Page oldPage){
        this.notes.remove(oldPage);
        updateLastAccessed();
    }

    /**
     * @return the number of pages in the notebook
     */
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
            updateLastAccessed();
        }
        //Print statement for debugging
        else{
            System.out.println("The tag "+tag+" is already found in tags");
        }
    }
    /*Method for Removing a single Tag*/
    public void removeTag(String tag){
        tags.remove(tag);
        updateLastAccessed();
    }

    /**SETTER AND GETTTER METHODS FOR CREATION DATE*/
    public LocalDateTime getCreationDate(){
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate){
        this.creationDate = creationDate;
    }
    // Getter and Setter for Last Accessed
    public LocalDateTime getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(LocalDateTime lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    // Method to update last accessed time to now
    public void updateLastAccessed() {
        this.lastAccessed = LocalDateTime.now();
    }
}
