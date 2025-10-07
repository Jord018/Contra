package org.game.contra.core;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import org.game.contra.entities.Player;
import org.game.contra.utils.Contact;
import org.game.contra.utils.WorldCreator;

public class GameModel {
    private World world;
    private Player player;
    private WorldCreator worldCreator;
    private Vector2 gravity;
    private boolean gameOver;
    private int score;
    public static final float PPM = 32; // Pixels Per Meter
    public GameModel() {
        this.gravity = new Vector2(0, -9.8f);
        this.world = new World(gravity, true);
        this.gameOver = false;
        this.score = 0;
        
        // Set up contact listener
        this.world.setContactListener(new Contact());
        
        // Initialize the world creator
        this.worldCreator = new WorldCreator(world);
        
        // Initialize the player at position (10, 10) in the world (more centered in the viewport)
        this.player = new Player(world, 5, 10);

    }

    public void update(float delta) {
        if (player != null && !gameOver) {
            // Step the physics simulation
            world.step(1/60f, 6, 2);

            // Update player
            player.update(delta);


            // Clear applied forces
            player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x * 0.9f, player.getBody().getLinearVelocity().y);
        }
    }

    // Getters and setters
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Vector2 getGravity() {
        return gravity;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public World getWorld() {
        return world;
    }
    
    public void dispose() {
        if (world != null) {
            world.dispose();
        }
        if (player != null) {
            player.dispose();
        }
    }

    public void addScore(int points) {
        this.score += points;
    }
}