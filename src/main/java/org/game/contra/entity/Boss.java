package org.game.contra.entity;

import org.game.contra.components.HealthComponent;
import org.game.contra.components.PhysicsComponent;
import org.game.contra.components.ShootingComponent;
import org.game.contra.components.TransformComponent;

import java.util.Random;

public class Boss extends Entity{
    private double x, y;
    private double width = 120, height = 120;
    private double speed = 2;
    private int health = 100;
    private double BULLET_SPEED = 5;
    private boolean alive = true;
    private Random random = new Random();
    private int shootCooldown = 0;
    private static final int MAX_SHOOT_COOLDOWN = 60; // Frames between shots
    
    public Boss(double startX, double startY) {
        addComponent(new TransformComponent(startX, startY, width, height));
        addComponent(new PhysicsComponent(speed, 0,0));
        addComponent(new HealthComponent(health));
        addComponent(new ShootingComponent(BULLET_SPEED));
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
    public PhysicsComponent getPhysics() { return getComponent(PhysicsComponent.class); }
    public HealthComponent getHealth() { return getComponent(HealthComponent.class); }
    public TransformComponent getTransform() { return getComponent(TransformComponent.class); }
    public ShootingComponent getShooting() { return getComponent(ShootingComponent.class); }
    public boolean isAlive() { return alive; }
}
