package org.game.contra.physics;

import javafx.geometry.Rectangle2D;

public class Platform {

    private final Rectangle2D bounds;

    public Platform(double x, double y, double width, double height) {
        this.bounds = new Rectangle2D(x, y, width, height);
    }

    public Rectangle2D getBounds() {
        return bounds;
    }
}