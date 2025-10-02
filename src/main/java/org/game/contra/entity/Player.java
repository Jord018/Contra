// src/main/java/org/game/contra/entity/Player.java
package org.game.contra.entity;

import org.game.contra.components.TransformComponent;
import org.game.contra.components.PhysicsComponent;
import org.game.contra.components.HealthComponent;
import org.game.contra.components.ShootingComponent;
import org.game.contra.entity.Entity;

public class Player extends Entity {
    private static final double WIDTH = 40;
    private static final double HEIGHT = 60;
    private static final double MOVE_SPEED = 5;
    private static final double JUMP_STRENGTH = -12;
    private static final double GRAVITY = 0.6;
    private static final double BULLET_SPEED = 10;
    private static final int MAX_HEALTH = 100;
    private boolean onGround = false;

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public Player(double startX, double startY) {
        // Add components
        addComponent(new TransformComponent(startX, startY, WIDTH, HEIGHT));
        addComponent(new PhysicsComponent(MOVE_SPEED, JUMP_STRENGTH, GRAVITY));
        addComponent(new HealthComponent(MAX_HEALTH));
        addComponent(new ShootingComponent(BULLET_SPEED));
    }



    public PhysicsComponent getPhysics() {
        return getComponent(PhysicsComponent.class);
    }

    public HealthComponent getHealth() {
        return getComponent(HealthComponent.class);
    }

    public ShootingComponent getShooting() {
        return getComponent(ShootingComponent.class);
    }

    public void takeDamage(int damage) {
        HealthComponent health = getHealth();
        if (health != null) {
            health.takeDamage(damage);
        }
    }
}