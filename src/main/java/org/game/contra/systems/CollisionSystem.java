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
    
    private void checkFloorCollisions() {
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
        float playerRight = playerX + playerWidth;
        
        // Get player's vertical velocity
        float verticalVelocity = (float) playerTransform.getVelocityY();
        boolean wasOnGround = player.isOnGround();
        boolean isOnGround = false;
        
        // Debug info
        System.out.println("\n--- Collision Debug ---");
        System.out.println("Player position: (" + playerX + ", " + playerY + ")");
        System.out.println("Player feet Y: " + playerFeetY);
        System.out.println("Velocity Y: " + verticalVelocity);
        
        // Store the best floor (highest one we can land on)
        Floor bestFloor = null;
        float highestFloorY = Float.NEGATIVE_INFINITY;
        
        // First pass: find the highest floor we're standing on
        for (int i = 0; i < floors.size(); i++) {
            Floor floor = floors.get(i);
            float floorTop = floor.getY();
            float floorLeft = floor.getX();
            float floorRight = floorLeft + floor.getWidth();
            
            // Debug floor info
            System.out.println("\nFloor " + i + ":");
            System.out.println("  Position: (" + floorLeft + ", " + floorTop + ") to (" + floorRight + ", " + floorTop + ")");
            
            // Check if player is within the floor's X bounds
            boolean withinXBounds = playerRight > floorLeft && playerLeft < floorRight;
            
            // Check if player is above the floor and close enough to land on it
            // In this coordinate system, higher Y values are lower on the screen
            boolean isAboveFloor = playerFeetY >= floorTop;  // Changed from <= to >=
            boolean isCloseEnough = playerFeetY + verticalVelocity <= floorTop + 20;  // Changed from >= to <=
            boolean canLand = isAboveFloor && isCloseEnough;
            
            System.out.println("  withinXBounds: " + withinXBounds);
            System.out.println("  isAboveFloor: " + isAboveFloor + " (playerFeetY: " + playerFeetY + ", floorTop: " + floorTop + ")");
            System.out.println("  isCloseEnough: " + isCloseEnough + " (nextY: " + (playerFeetY + verticalVelocity) + ")");
            
            if (withinXBounds && canLand && (bestFloor == null || floorTop < highestFloorY)) {
                highestFloorY = floorTop;
                bestFloor = floor;
                System.out.println("  ^ This is the best floor so far");
            }
        }
        
        // If we found a floor to land on
        if (bestFloor != null) {
            System.out.println("\nBest floor found at Y: " + highestFloorY);
            
            // Only snap to floor if we're falling or on the floor
            if (verticalVelocity >= 0) {
                // Calculate the correction needed to land on the floor
                // In this coordinate system, we need to position the player above the floor
                float targetY = highestFloorY - playerHeight;
                
                // Apply the correction to the player's position
                playerTransform.setY(targetY);
                
                // Reset vertical velocity if we're landing
                if (verticalVelocity > 0) {
                    playerTransform.setVelocityY(0);
                }
                
                isOnGround = true;
                System.out.println("Player landed on floor!");
            }
        } else {
            System.out.println("No suitable floor found!");
        }
        
        // Update player's onGround state
        player.setOnGround(isOnGround);
        System.out.println("isOnGround: " + isOnGround);
        
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