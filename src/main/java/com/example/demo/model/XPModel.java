package com.example.demo.model;

public class XPModel {
    private double currentXP;
    private double maxXP;
    private int level;

    public XPModel(double maxXP) {
        this.maxXP = maxXP;
        this.currentXP = 0; //Starting at 0 XP
        this.level = 1;
    }

    public double getCurrentXP() {
        return currentXP;
    }

    public void setCurrentXP(double currentXP) {
        this.currentXP = currentXP;
    }

    public double getMaxXP() {
        return maxXP;
    }

    public int getLevel() {
        return level;
    }

    public void addXP(double xp) {
        currentXP += xp;
        if (isMaxXPReached()) {
            levelUp();
        }
    }
    private void levelUp() {
        level++;
        currentXP = 0;
        maxXP *= 1.1;
    }

    public boolean isMaxXPReached(){
        return currentXP >= maxXP;
    }

    public double getProgress() {
        return currentXP / maxXP;
    }
}