package com.example.demo.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DigitalTree {
    private ImageView treeImageview;
    private List<Image> treeStages;
    private int currentStage = 0;
    private XPModel xpModel;

    public DigitalTree() {
        treeImageview = new ImageView();
        treeStages = new ArrayList<>();
        treeImageview.setFitHeight(250);
        treeImageview.setFitWidth(250);
        xpModel = XPManager.getXPModel();

        //Getting every image into the list
        for (int i = 0; i <= 7; i++ ){
            try {
                Image treeStage = new Image(Objects.requireNonNull(
                        getClass().getResourceAsStream("/plantimg/p" + i + ".png"),
                        "Image p" + i + ".png not found"));
                treeStages.add(treeStage);
            } catch (NullPointerException e) {
                System.out.println("Error: Could not load p" + i + ".png. Please check the file path.");
            }
        }

        int temp = XPManager.getXPModel().getLevel();
        System.out.println(currentStage);
        if (temp == 1){
            currentStage = 0;
            treeImageview.setImage(treeStages.get(currentStage));
        }
        else if(temp % 5 ==0) {
            currentStage = temp - 1;
            if (!treeStages.isEmpty()) {
                treeImageview.setImage(treeStages.get(currentStage));
            }
        }
    }

    public ImageView getTreeImageview() {
        return treeImageview;
    }

    //Getting the tree to grow to the next stage
    public void Grow(){
        if(currentStage < treeStages.size() - 1){
            currentStage++;
            treeImageview.setImage(treeStages.get(currentStage));
        }
    }

    public void resetTree(){
        currentStage = 0;
        treeImageview.setImage(treeStages.get(currentStage));
    }
}

