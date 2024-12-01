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

    /**
     * set up the page
     * @param pageTitle
     */
    public Page(String pageTitle){
        title = pageTitle;
        contents = new InlineCssTextArea();

        contents.setWrapText(true);
        // Continue listening to wrap text if text gets too long
        contents.widthProperty().addListener((obs, oldVal, newVal) -> {
            double newWidth = newVal.doubleValue();
            contents.setParagraphGraphicFactory(line -> {
                contents.setWrapText(true);
                return null;
            });
        });


    }

    /**
     * change contents of the text area
     * @param contents new text area
     */
    public void setContents(InlineCssTextArea contents) {
        this.contents = contents;
    }

    /**
     * @return the contents of the text area
     */
    public InlineCssTextArea getContents(){
        return contents;
    }

    /**
     * @return the title of the page
     */
    public String getTitle(){
        return title;
    }

    /**
     * set the title of the page
     * @param name new title
     */
    public void setTitle(String name){
        title = name;
    }



}

