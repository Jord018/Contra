package org.game.contra.entity;

import java.util.Random;

public class Boss extends Entity{
    private double x, y;
    private double width = 120, height = 120;
    private double speed = 2;
    private int health = 100;
    private boolean alive = true;
    private Random random = new Random();
    private int shootCooldown = 0;
    private static final int MAX_SHOOT_COOLDOWN = 60; // Frames between shots
    
    public Boss(double startX, double startY) {
        this.x = startX;
        this.y = startY;
    }
    
    public void update() {
        // Simple patrol behavior
        x += speed;
        if (x < 400 || x > 700) {
            speed *= -1; // Reverse direction at boundaries
        }
        
        // Update shoot cooldown
        if (shootCooldown > 0) {
            shootCooldown--;
        }
    }
    
    public Bullet tryShoot(double targetX, double targetY) {
        if (shootCooldown <= 0 && random.nextDouble() < 0.02) { // 2% chance to shoot each frame when cooldown is ready
            shootCooldown = MAX_SHOOT_COOLDOWN;
            
            // Calculate direction to player
            double dx = targetX - (x + width/2);
            double dy = targetY - (y + height/2);
            double length = Math.sqrt(dx*dx + dy*dy);
            
            // Normalize and scale speed
            double speed = 4.0;
            dx = (dx / length) * speed;
            dy = (dy / length) * speed;
            
            return new Bullet(x + width/2, y + height/2, dx, dy, false);
        }
        return null;
    }
    
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            health = 0;
            alive = false;
        }
    }
    

    
    // Getters
    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public int getHealth() { return health; }
    public boolean isAlive() { return alive; }
}
