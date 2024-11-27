package com.example.demo.model;

import com.example.demo.FilerSystem.XPStorage;

public class XPManager {
    private static XPModel xpModel;

    public static XPModel getXPModel() {
        if (xpModel == null) {
            xpModel = XPStorage.LoadXPBar();
            if (xpModel == null) {
                xpModel = new XPModel(100);
            }
        }
        return xpModel;
    }
}
