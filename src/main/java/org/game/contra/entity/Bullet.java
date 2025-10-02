package org.game.contra.entity;

import org.game.contra.components.TransformComponent;

public class Bullet extends Entity {
    private boolean fromPlayer;
    private boolean active = true;
    private static final double BULLET_SIZE = 8;
    private static final double BULLET_SPEED = 10.0;
    
    public Bullet(double x, double y, double speedX, double speedY, boolean fromPlayer) {
        this.fromPlayer = fromPlayer;
        
        // Create and add transform component
        TransformComponent transform = new TransformComponent(x, y, BULLET_SIZE, BULLET_SIZE);
        transform.setVelocityX(speedX);
        transform.setVelocityY(speedY);
        addComponent(transform);
    }
    
    public void update() {
        TransformComponent transform = getComponent(TransformComponent.class);
        if (transform != null) {
            // Update position based on velocity
            transform.setX(transform.getX() + transform.getVelocityX());
            transform.setY(transform.getY() + transform.getVelocityY());
            
            // Deactivate if off screen
            if (transform.getX() < -50 || transform.getX() > 850 || 
                transform.getY() < -50 || transform.getY() > 650) {
                active = false;
            }
        }
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    // Getters
    public double getX() { 
        TransformComponent transform = getComponent(TransformComponent.class);
        return transform != null ? transform.getX() : 0; 
    }
    
    public double getY() { 
        TransformComponent transform = getComponent(TransformComponent.class);
        return transform != null ? transform.getY() : 0;
    }
    
    public double getWidth() { return BULLET_SIZE; }
    public double getHeight() { return BULLET_SIZE; }
    
    public boolean isFromPlayer() { 
        return fromPlayer; 
    }
    
    public TransformComponent getTransform() {
        return getComponent(TransformComponent.class);
    }
}
