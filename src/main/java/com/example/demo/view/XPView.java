package com.example.demo.view;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class XPView extends VBox {
    private ProgressBar xpProgressBar;
    private Label xpLabel;

    public XPView() {
        xpProgressBar = new ProgressBar(0);
        xpProgressBar.setPrefWidth(1000);

        xpLabel = new Label("XP: 0 / 100 | Level : 1");

        this.getChildren().addAll(xpProgressBar, xpLabel);
    }

    public void updateXPview(double currentXP, double maxXP, int level) {
        double progress = currentXP / maxXP;
        xpProgressBar.setProgress(progress);

        // Ensure this update runs on the JavaFX Application Thread
        Platform.runLater(() -> {
            xpProgressBar.setProgress(progress);
            xpLabel.setText("XP: " + (int)currentXP + " / " + (int)maxXP + " | Level: " + level);
        });
    }

    public ProgressBar getXpProgressBar() {
        return xpProgressBar;
    }

    public void changeXPBarColor(Color color) {
        xpProgressBar.setStyle("-fx-accent: " + toHexString(color) + ";");
    }

    private String toHexString(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}