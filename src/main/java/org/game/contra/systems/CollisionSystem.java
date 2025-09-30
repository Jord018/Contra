package org.game.contra.systems;

import org.game.contra.entity.*;
import org.game.contra.components.TransformComponent;
import javafx.scene.Scene;
import java.util.*;

public class CollisionSystem {
    private List<Entity> entities;
    private List<Bullet> bullets;
    private List<Floor> floors;
    private Player player;
    private Boss boss;
    private Scene scene;

    public CollisionSystem(List<Entity> entities, Scene scene) {
        this.entities = entities != null ? entities : new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.floors = new ArrayList<>();
        this.scene = scene;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    public void addEntity(Entity entity) {
        if (entity != null) {
            entities.add(entity);
        }
    }

    public void addBullet(Bullet bullet) {
        if (bullet != null) {
            bullets.add(bullet);
        }
    }
    
    public void addFloor(Floor floor) {
        if (floor != null) {
            floors.add(floor);
        }
    }


    public void update() {
        if (player == null) return;

        // Remove inactive bullets
        bullets.removeIf(bullet -> !bullet.isActive());

        // Get player transform
        TransformComponent playerTransform = player.getComponent(TransformComponent.class);
        if (playerTransform == null) return;

        // Check bullet collisions
        for (Bullet bullet : new ArrayList<>(bullets)) {
            if (!bullet.isActive()) continue;
            
            TransformComponent bulletTransform = bullet.getComponent(TransformComponent.class);
            if (bulletTransform == null) continue;

            // Check bullet collision with player (if bullet is from enemy)
            if (!bullet.isFromPlayer() && checkCollision(bulletTransform, playerTransform)) {
                player.takeDamage(10);
                bullet.setActive(false);
                continue;
            }

            // Check bullet collision with boss (if bullet is from player)
            if (boss != null && bullet.isFromPlayer() && boss.isAlive()) {
                TransformComponent bossTransform = boss.getComponent(TransformComponent.class);
                if (bossTransform != null && checkCollision(bulletTransform, bossTransform)) {
                    boss.takeDamage(10);
                    bullet.setActive(false);
                    continue;
                }
            }

            // Check bullet collision with other entities
            for (Entity entity : entities) {
                if (entity != player && entity != boss) {
                    TransformComponent entityTransform = entity.getComponent(TransformComponent.class);
                    if (entityTransform != null && checkCollision(bulletTransform, entityTransform)) {
                        bullet.setActive(false);
                        // Handle entity-specific collision logic here
                        break;
                    }
                }
            }
        }

        // Check player collision with boss
        if (boss != null && boss.isAlive()) {
            TransformComponent bossTransform = boss.getComponent(TransformComponent.class);
            if (bossTransform != null && checkCollision(playerTransform, bossTransform)) {
                player.takeDamage(5);
            }
        }

        // Check player collision with other entities
        for (Entity entity : entities) {
            if (entity != player && entity != boss) {
                TransformComponent entityTransform = entity.getComponent(TransformComponent.class);
                if (entityTransform != null && checkCollision(playerTransform, entityTransform)) {
                    player.takeDamage(5);
                    break;
                }
            }
        }
        
        // Check floor collisions
        checkFloorCollisions();
        // Check floor collision with player
        
    }

    private boolean checkCollision(TransformComponent a, TransformComponent b) {
        return a.getX() < b.getX() + b.getWidth() &&
               a.getX() + a.getWidth() > b.getX() &&
               a.getY() < b.getY() + b.getHeight() &&
               a.getY() + a.getHeight() > b.getY();
    }
    
    public void checkFloorCollisions() {
        if (player == null) return;
        
        TransformComponent playerTransform = player.getComponent(TransformComponent.class);
        if (playerTransform == null) return;
        
        // Player's position and dimensions
        float playerX = (float) playerTransform.getX();
        float playerY = (float) playerTransform.getY();
        float playerWidth = (float) playerTransform.getWidth();
        float playerHeight = (float) playerTransform.getHeight();
        float playerFeetY = playerY + playerHeight;
        float playerLeft = playerX;
        float playerRight = playerLeft + playerWidth;
        
        // Get player's vertical velocity
        float verticalVelocity = (float) playerTransform.getVelocityY();
        boolean wasOnGround = player.isOnGround();
        boolean isOnGround = false;
        
        for (Floor floor : floors) {
            float floorTop = floor.getY();
            float floorLeft = floor.getX();
            float floorRight = floorLeft + floor.getWidth();
            
            // Check if player's feet are colliding with the top of the floor
            boolean feetColliding = (playerFeetY + verticalVelocity) >= floorTop &&  // Player is above or at floor level
                                 playerY < floorTop &&                              // Player is above floor (not inside)
                                 playerRight > floorLeft &&                         // Player's right side is right of floor's left
                                 playerLeft < floorRight;                           // Player's left side is left of floor's right
            
            if (feetColliding) {
                // Snap player to the top of the floor
                playerTransform.setY(floorTop - playerHeight);
                
                // Stop downward movement
                if (verticalVelocity > 0) {
                    playerTransform.setVelocityY(0);
                }
                
                isOnGround = true;
                break;
            }
        }
        
        // Update player's onGround state
        player.setOnGround(isOnGround);
        
        // If we just landed, trigger landing logic

    }

    public void clear() {
        entities.clear();
        bullets.clear();
        floors.clear();
        player = null;
        boss = null;
    }

    public List<Floor> getFloors() {
        return floors;
    }
}