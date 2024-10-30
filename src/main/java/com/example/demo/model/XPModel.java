package com.example.demo.model;

public class XPModel {
    private double currentXP;
    private double maxXP;

    public XPModel(double maxXP) {
        this.maxXP = maxXP;
        this.currentXP = 0; //Starting at 0 XP
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

    public void addXP(double xp) {
        currentXP = Math.min(currentXP + xp, maxXP);
    }

    public double getProgress() {
        return currentXP / maxXP;
    }
}
