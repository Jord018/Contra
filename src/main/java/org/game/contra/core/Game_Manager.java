// src/main/java/org/game/contra/core/Game_Manager.java
package org.game.contra.core;

import org.game.contra.entity.Entity;
import org.game.contra.entity.Floor;
import org.game.contra.entity.Player;
import org.game.contra.systems.PhysicsSystem;
import org.game.contra.systems.CollisionSystem;
import javafx.scene.Scene;
import java.util.ArrayList;
import java.util.List;

public class Game_Manager {
    private final List<Entity> entities = new ArrayList<>();
    private final PhysicsSystem physicsSystem = new PhysicsSystem();
    private final CollisionSystem collisionSystem;
    private boolean leftPressed, rightPressed, jumpPressed;

    public Game_Manager(Scene scene) {
        // Initialize collision system with the scene
        this.collisionSystem = new CollisionSystem(new ArrayList<>(), scene);

        // Create player, add to entities and collision system
        Player player = new Player(100, 100);
        entities.add(player);
        collisionSystem.setPlayer(player);

        // Create floor and add to collision system
        Floor ground = new Floor(0, 500, 800, 50);
        ground.setCollisionBox(0, 500, 800, 20);
        collisionSystem.addFloor(ground);
    }


    public void update() {
        // Update physics first
        physicsSystem.update(entities, leftPressed, rightPressed, jumpPressed);

        // Update collision system (this will handle all collision checks)
        collisionSystem.update();

        // Reset jump after processing
        jumpPressed = false;
    }

    public CollisionSystem getCollisionSystem() {
        return collisionSystem;
    }

    public void setLeftPressed(boolean pressed) { leftPressed = pressed; }
    public void setRightPressed(boolean pressed) { rightPressed = pressed; }
    public void setJumpPressed(boolean pressed) { jumpPressed = pressed; }

    public List<Entity> getEntities() { return entities; }
}