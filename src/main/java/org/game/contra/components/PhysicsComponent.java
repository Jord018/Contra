// src/main/java/org/game/contra/components/PhysicsComponent.java
package org.game.contra.components;

public class PhysicsComponent {
    public double velocityX, velocityY;
    public boolean onGround;
    public final double moveSpeed;
    public final double jumpStrength;
    public final double gravity;

    public PhysicsComponent(double moveSpeed, double jumpStrength, double gravity) {
        this.moveSpeed = moveSpeed;
        this.jumpStrength = jumpStrength;
        this.gravity = gravity;
        this.onGround = false;
    }
}