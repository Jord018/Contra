package org.game.contra.entities;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import org.game.contra.entities.Shooting.ShootingStrategy;

public class Boss {

    private final Point2D position;
    private final Point2D velocity;
    private final Rectangle2D bounds;
    private final ShootingStrategy shootingStrategy;

    public Boss(double x, double y, double width, double height, ShootingStrategy shootingStrategy) {
        this.position = new Point2D(x, y);
        this.velocity = new Point2D(0, 0);
        this.bounds = new Rectangle2D(x, y, width, height);
        this.shootingStrategy = shootingStrategy;
    }

    public void update(double deltaTime) {
        // Update boss position
        position.add(velocity.getX() * deltaTime, velocity.getY() * deltaTime);
    }

    public Point2D getPosition() {
        return position;
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(position.getX(), position.getY(), bounds.getWidth(), bounds.getHeight());
    }
}