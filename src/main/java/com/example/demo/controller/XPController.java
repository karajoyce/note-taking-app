package com.example.demo.controller;

import com.example.demo.model.XPModel;
import javafx.scene.media.AudioClip;
import com.example.demo.view.FlashcardScreenView;
import com.example.demo.view.XPView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Objects;
import java.util.Random;
import javafx.scene.paint.Color;

public class XPController {
    private XPModel model;
    private XPView view;
    private Timeline xpTimeline;
    private Random random = new Random();

    private AudioClip lvlUpSound;
    public XPController(XPModel model, XPView view) {
        this.model = model;
        this.view = view;
        // Ensure the path to the sound file is correct
        try {
            // Audio Clip came by floraphonic from Pixabay
            //Sound Effect by <a href="https://pixabay.com/users/floraphonic-38928062/?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=189853">floraphonic</a> from <a href="https://pixabay.com//?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=189853">Pixabay</a>
            lvlUpSound = new AudioClip(getClass().getResource("/level-up.mp3").toExternalForm());
            Objects.requireNonNull(lvlUpSound, "Sound file not found");
        } catch (NullPointerException e) {
            System.out.println("Error: Sound file not found. Please check the path.");
        }

        setupXPTimer();
    }

    private void setupXPTimer() {
        xpTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> addXPOverTime(10)));
        xpTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void startXPTimer() {
        xpTimeline.play();
    }

    public void stopXPTimer() {
        xpTimeline.stop();
    }

    public void addXPOverTime(double xpAmount) {
        int prevLvl = model.getLevel();
        model.addXP(xpAmount);

        //Detecting levelup
        if (model.getLevel() > prevLvl ) {
            handleLevelUp();
        }

        updateView();
    }

    private void handleLevelUp() {
        int prevLvl = model.getLevel();
        if (model.getLevel() ==  1 && prevLvl == 1) {
            return;
        }
        Color newColor = newRandomColour();
        view.changeXPBarColor(newColor);
        lvlUpSound.play();
    }

    private Color newRandomColour() {
        return Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    private void updateView(){
        view.updateXPview(model.getCurrentXP(), model.getMaxXP(), model.getLevel());
    }


}
