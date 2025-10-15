package org.game.contra.entities;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

public class Bullet {

    private final Point2D position;
    private final Point2D velocity;
    private final Rectangle2D bounds;

    public Bullet(double x, double y, double targetX, double targetY) {
        this.position = new Point2D(x, y);
        double angle = Math.atan2(targetY - y, targetX - x);
        this.velocity = new Point2D(Math.cos(angle) * 10, Math.sin(angle) * 10);
        this.bounds = new Rectangle2D(x, y, 10, 10);
    }

    public void update(double deltaTime) {
        position.add(velocity.getX() * deltaTime, velocity.getY() * deltaTime);
    }

    public Point2D getPosition() {
        return position;
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(position.getX(), position.getY(), bounds.getWidth(), bounds.getHeight());
    }
}