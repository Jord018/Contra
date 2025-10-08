package org.game.contra.core;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import org.game.contra.entities.Boss;
import org.game.contra.entities.Bullet;
import org.game.contra.entities.Player;
import org.game.contra.utils.Contact;
import org.game.contra.utils.WorldCreator;

import java.util.ArrayList;
import java.util.Iterator;

public class GameModel {
    private World world;
    private Player player;
    private ArrayList<Boss> bosses = new ArrayList<>();
    private WorldCreator worldCreator;
    private Vector2 gravity;
    private boolean gameOver;
    private int score;
    public GameModel() {
        this.gravity = new Vector2(0, -9.8f);
        this.world = new World(gravity, true);
        this.gameOver = false;
        this.score = 0;

        // Set up contact listener
        this.world.setContactListener(new Contact());

        // Initialize the world creator
        this.worldCreator = new WorldCreator(world);

        // Initialize the player
        this.player = new Player(world, 5, 2);

        // Add multiple bosses with different positions and sizes
        bosses.add(new Boss(world, 12.45f, 3.75f, 0.5f, 0.5f));
        bosses.add(new Boss(world, 11.50f, 3.75f, 0.5f, 0.5f));
        bosses.add(new Boss(world, 11.75f, 1.85f, 1.0f, 1.0f));
    }

    public void update(float delta) {
        if (player != null && !gameOver) {
            // 1. Physics step
            world.step(1/60f, 6, 2);

            // 2. Update entities
            player.update(delta);

            // Update all bosses
            Iterator<Boss> bossIter = bosses.iterator();
            while (bossIter.hasNext()) {
                Boss boss = bossIter.next();
                boss.update(delta);

                // Remove dead bosses
                if (!boss.isAlive()) {
                    boss.destroy();
                    bossIter.remove();
                }
            }

            // 3. Add new player bullets after physics step
            ArrayList<Bullet> bulletsToAdd = new ArrayList<>(player.getBulletsToAdd());
            for (Bullet b : bulletsToAdd) {
                b.createBody(world);
                player.getBullets().add(b);
            }
            player.getBulletsToAdd().clear();

            // 4. Clean up player bullets that are marked for destruction or inactive
            Iterator<Bullet> bulletIter = player.getBullets().iterator();
            while (bulletIter.hasNext()) {
                Bullet b = bulletIter.next();
                if (b.shouldBeDestroyed() || !b.isActive()) {
                    b.destroy();
                    bulletIter.remove();
                }
            }
            
            // 5. Update and clean up boss bullets
            for (Boss boss : bosses) {
                // Update boss bullets
                Iterator<Bullet> bossBulletIter = boss.getBullets().iterator();
                while (bossBulletIter.hasNext()) {
                    Bullet bullet = bossBulletIter.next();
                    // Update bullet position
                    if (bullet.getBody() != null) {
                        bullet.update(delta);
                    }
                    
                    // Remove bullets that are out of bounds or inactive
                    if (bullet.shouldBeDestroyed() || !bullet.isActive() || 
                        bullet.getPosition().x < 0 || bullet.getPosition().x > 100) {
                        if (bullet.getBody() != null) {
                            world.destroyBody(bullet.getBody());
                        }
                        bossBulletIter.remove();
                    }
                }
            }

            // 5. Apply damping to player's horizontal velocity
            Body pBody = player.getBody();
            pBody.setLinearVelocity(pBody.getLinearVelocity().x * 0.9f, pBody.getLinearVelocity().y);
        }
    }


    // Getters and setters
    public Player getPlayer() {
        return player;
    }
    public Boss getBoss() {
        if (bosses.isEmpty()) {
            return null;
        }
        // Return the first alive boss, or null if none are alive
        for (Boss boss : bosses) {
            if (boss.isAlive()) {
                return boss;
            }
        }
        return null; // No alive bosses
    }
    public ArrayList<Boss> getBosses() {
        return bosses;
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