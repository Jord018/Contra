// src/main/java/org/game/contra/systems/PhysicsSystem.java
package org.game.contra.systems;

import org.game.contra.components.TransformComponent;
import org.game.contra.components.PhysicsComponent;
import org.game.contra.entity.Entity;
import java.util.List;

public class PhysicsSystem {
    public void update(List<Entity> entities, boolean left, boolean right, boolean jump) {
        for (Entity entity : entities) {
            if (!entity.hasComponent(TransformComponent.class) ||
                    !entity.hasComponent(PhysicsComponent.class)) {
                continue;
            }

            TransformComponent transform = entity.getComponent(TransformComponent.class);
            PhysicsComponent physics = entity.getComponent(PhysicsComponent.class);

            // Apply gravity
            physics.velocityY += physics.gravity;

            // Handle horizontal movement
            if (left) {
                physics.velocityX = -physics.moveSpeed;
                transform.setFacingRight(false);
            } else if (right) {
                physics.velocityX = physics.moveSpeed;
                transform.setFacingRight(true);
            } else {
                physics.velocityX = 0;
            }

            // Handle jumping
            if (jump && physics.onGround) {
                physics.velocityY = physics.jumpStrength;
                physics.onGround = false;
            }

            // Update position
            transform.setX(transform.getX() + physics.velocityX);
            transform.setY(transform.getY() + physics.velocityY);

            // Simple ground collision
            if (transform.getY() > 540) { // Ground at y=540
                transform.setY(540);
                physics.velocityY = 0;
                physics.onGround = true;
            }

            // Screen boundaries
            if (transform.getX() < 0) transform.setX(0);
            if (transform.getX() > 800 - transform.getWidth()) {
                transform.setX(800 - transform.getWidth());
            }
        }
    }
}