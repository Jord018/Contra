// src/main/java/org/game/contra/systems/PhysicsSystem.java
package org.game.contra.systems;

import org.game.contra.components.TransformComponent;
import org.game.contra.components.PhysicsComponent;
import org.game.contra.entity.Entity;
import org.game.contra.entity.Bullet;
import org.game.contra.entity.Player;

import java.util.List;

public class PhysicsSystem {
    public void update(List<Entity> entities, boolean left, boolean right, boolean jump) {
        for (Entity entity : entities) {
            // Update bullet position if it's a bullet
            if (entity instanceof Bullet) {
                updateBullet((Bullet) entity);
                continue;
            }

            // Skip entities without required components
            if (!entity.hasComponent(TransformComponent.class) ||
                !entity.hasComponent(PhysicsComponent.class)) {
                continue;
            }

            TransformComponent transform = entity.getComponent(TransformComponent.class);
            PhysicsComponent physics = entity.getComponent(PhysicsComponent.class);

            // Apply gravity if not on ground
            if (entity instanceof Player) {
                Player player = (Player) entity;
                if (!player.isOnGround()) {
                    physics.velocityY = Math.min(physics.velocityY + physics.gravity, 15);
                }
            } else if (!physics.onGround) {
                physics.velocityY = Math.min(physics.velocityY + physics.gravity, 15);
            }

            // Handle horizontal movement
            if (left) {
                physics.velocityX = -physics.moveSpeed;
                transform.setFacingRight(false);
            } else if (right) {
                physics.velocityX = physics.moveSpeed;
                transform.setFacingRight(true);
            } else {
                // Apply friction when not moving
                physics.velocityX *= 0.8;
                if (Math.abs(physics.velocityX) < 0.1) physics.velocityX = 0;
            }

            // In PhysicsSystem.java, modify the update method:
            if (jump) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    if (player.isOnGround()) {
                        physics.velocityY = physics.jumpStrength;
                        player.setOnGround(false);
                    }
                } else if (physics.onGround) {
                    physics.velocityY = physics.jumpStrength;
                    physics.onGround = false;
                }
            }

            // Update position
            transform.setX(transform.getX() + physics.velocityX);
            transform.setY(transform.getY() + physics.velocityY);

            // Screen boundaries - only for player
            if (entity instanceof org.game.contra.entity.Player) {
                if (transform.getX() < 0) {
                    transform.setX(0);
                    physics.velocityX = 0;
                }
                if (transform.getX() > 800 - transform.getWidth()) {
                    transform.setX(800 - transform.getWidth());
                    physics.velocityX = 0;
                }
            }
        }
    }
    
    private void updateBullet(Bullet bullet) {
        // Bullets update their own position in their update method
        bullet.update();
    }
}