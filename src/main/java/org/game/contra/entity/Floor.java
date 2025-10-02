package org.game.contra.entity;

import org.game.contra.components.TransformComponent;
import org.game.contra.systems.CollisionSystem;

public class Floor extends Entity {
    private int x;
    private int y;
    private int width;
    private int height;
    private int collisionX;
    private int collisionY;
    private int collisionWidth;
    private int collisionHeight;
    
    public Floor(int x, int y, int width, int height) {
        // Add TransformComponent for rendering
        addComponent(new TransformComponent(x, y, width, height));
        
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        // Default collision box to match floor dimensions
        this.collisionX = x;
        this.collisionY = y;
        this.collisionWidth = width;
        this.collisionHeight = height;
    }

    public void setCollisionBox(int x, int y, int width, int height) {
        this.collisionX = x;
        this.collisionY = y;
        this.collisionWidth = width;
        this.collisionHeight = height;
    }

    // Getters for collision box
    public int getCollisionX() {
        return collisionX;
    }

    public int getCollisionY() {
        return collisionY;
    }

    public int getCollisionWidth() {
        return collisionWidth;
    }

    public int getCollisionHeight() {
        return collisionHeight;
    }

    // Getters for floor dimensions
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // Check if a point is within the collision box
    public boolean contains(int x, int y) {
        return x >= collisionX && x <= collisionX + collisionWidth &&
               y >= collisionY && y <= collisionY + collisionHeight;
    }
    public static Floor createFloor(int x, int y, int width, int height, CollisionSystem collisionSystem) {
        Floor floor = new Floor(x, y, width, height);
        floor.setCollisionBox(x, y, width, height);
        collisionSystem.addFloor(floor);
        return floor;
    }
}
