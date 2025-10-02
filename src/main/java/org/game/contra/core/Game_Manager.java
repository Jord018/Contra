// src/main/java/org/game/contra/core/Game_Manager.java
package org.game.contra.core;

import org.game.contra.components.TransformComponent;
import org.game.contra.entity.*;
import org.game.contra.systems.PhysicsSystem;
import org.game.contra.systems.CollisionSystem;
import javafx.scene.Scene;
import java.util.ArrayList;
import java.util.List;

public class Game_Manager {
    private final List<Entity> entities = new ArrayList<>();
    private final PhysicsSystem physicsSystem = new PhysicsSystem();

    private boolean leftPressed, rightPressed, jumpPressed, attackPressed;
    private long lastShotTime = 0;
    private static final long SHOT_DELAY_MS = 200; // 200ms between shots
    private CollisionSystem collisionSystem;

    public Game_Manager(Scene scene,CollisionSystem collisionSystem) {
        this.collisionSystem = collisionSystem;
    }


    public void update() {
        long currentTime = System.currentTimeMillis();
        
        // Handle shooting with cooldown
        if (attackPressed && (currentTime - lastShotTime) > SHOT_DELAY_MS) {
            Player player = getPlayer();
            if (player != null) {
                // Make sure the shooting component has the player's transform
                TransformComponent playerTransform = player.getComponent(TransformComponent.class);
                if (playerTransform != null) {
                    player.getShooting().setTransform(playerTransform);
                    Bullet bullet = player.getShooting().shoot();
                    if (bullet != null) {
                        entities.add(bullet);
                        collisionSystem.addBullet(bullet);
                        lastShotTime = currentTime;
                    }
                }
            }
        }

        // Update physics first
        physicsSystem.update(entities, leftPressed, rightPressed, jumpPressed);

        // Update collision system (this will handle all collision checks)
        collisionSystem.update();

        // Clean up inactive bullets
        entities.removeIf(entity -> entity instanceof Bullet && !((Bullet) entity).isActive());
        
        // Reset input flags after processing
        jumpPressed = false;
    }
    
    private Player getPlayer() {
        for (Entity entity : entities) {
            if (entity instanceof Player) {
                return (Player) entity;
            }
        }
        return null;
    }

    public CollisionSystem getCollisionSystem() {
        return collisionSystem;
    }

    public void setLeftPressed(boolean pressed) { leftPressed = pressed; }
    public void setRightPressed(boolean pressed) { rightPressed = pressed; }
    public void setJumpPressed(boolean pressed) { jumpPressed = pressed; }
    public void setAttackPressed(boolean pressed) { attackPressed = pressed; }

    public List<Entity> getEntities() { return entities; }
    
    public void addEntity(Entity entity) {
        entities.add(entity);
        
        // If it's a floor, add it to the collision system
        if (entity instanceof Floor) {
            collisionSystem.addFloor((Floor) entity);
        }
        // Add other entity type checks here if needed
        if(entity instanceof Player) {
            collisionSystem.setPlayer((Player) entity);
        }
        if(entity instanceof Boss) {
            collisionSystem.setBoss((Boss) entity);
        }
    }
}