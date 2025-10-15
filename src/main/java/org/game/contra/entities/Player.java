package org.game.contra.entities;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

public class Player {

    private Point2D position;
    private Point2D velocity;
    private final Rectangle2D bounds;
    private boolean grounded;

    public Player(double x, double y) {
        this.position = new Point2D(x, y);
        this.velocity = new Point2D(0, 0);
        this.bounds = new Rectangle2D(x, y, 50, 50);
        this.grounded = false;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(position.getX(), position.getY(), bounds.getWidth(), bounds.getHeight());
    }

    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }
}