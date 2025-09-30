package org.game.contra.entity;

public class Bullet extends Entity{
    private double x, y;
    private double speedX, speedY;
    private boolean fromPlayer;
    private boolean active = true;
    private static final double BULLET_SIZE = 8;
    
    public Bullet(double x, double y, double speedX, double speedY, boolean fromPlayer) {
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.fromPlayer = fromPlayer;
    }
    
    public void update() {
        x += speedX;
        y += speedY;
        
        // Deactivate if off screen
        if (x < -50 || x > 850 || y < -50 || y > 650) {
            active = false;
        }
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    // Getters
    public double getX() { return x; }
    public double getY() { return y; }
    public double getSize() { return BULLET_SIZE; }
    public boolean isFromPlayer() { return fromPlayer; }
    
    // Check collision with a rectangle
    public boolean collidesWith(double x, double y, double width, double height) {
        return this.x + BULLET_SIZE > x && 
               this.x < x + width &&
               this.y + BULLET_SIZE > y && 
               this.y < y + height;
    }
}
