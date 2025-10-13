package org.game.contra.core;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import org.game.contra.entities.Boss;
import org.game.contra.entities.Bullet;
import org.game.contra.entities.Player;
import org.game.contra.entities.Items.AbstractItem;
import org.game.contra.entities.Items.Items;
import org.game.contra.utils.Contact;
import org.game.contra.utils.ItemFactory;
import org.game.contra.utils.WorldCreator;
import org.game.contra.entities.Items.ItemsType.ItemType;

import java.util.ArrayList;
import java.util.Iterator;

public class GameModel {
    private World world;
    private Player player;
    private ArrayList<Boss> bosses = new ArrayList<>();
    private ArrayList<Items> items = new ArrayList<>();
    private WorldCreator worldCreator;
    private Vector2 gravity;
    private boolean gameOver;
    private int score;

    public GameModel(Player player, Vector2 gravity, World world, String screenName) {
        this.gravity = gravity;
        this.world = world;
        this.gameOver = false;
        this.score = 0;

        // Set up contact listener
        this.world.setContactListener(new Contact());
        // Initialize the world creator
        this.worldCreator = new WorldCreator(world, screenName);

        // Initialize the player
        this.player = player;
        
        // Initialize bosses based on screen
        switch (screenName) {
            case "Screen1":
                // Add multiple bosses with different positions and sizes
                bosses.add(new Boss(world, 12.45f, 3.75f, 0.5f, 0.5f,"Bullet/Bullet.png",new org.game.contra.entities.Shooting.StraightShoot()));
                bosses.add(new Boss(world, 11.50f, 3.75f, 0.5f, 0.5f,"Bullet/Bullet.png",new org.game.contra.entities.Shooting.StraightShoot()));
                bosses.add(new Boss(world, 11.75f, 1.85f, 1.0f, 1.0f,"Bullet/Bullet.png",new org.game.contra.entities.Shooting.StraightShoot()));
                
                // Spawn items in Screen1
                spawnItem(ItemType.SPREAD, 8.0f, 2.5f);
                break;
            case "Screen2":
                // Add bosses for Screen2
                bosses.add(new Boss(world, 9.0f, 5.5f, 3.75f, 5.5f,"Bullet/Boss.png",new org.game.contra.entities.Shooting.AimedShoot(player)));
                break;
            case "Screen3":
                // Add bosses for Screen3

                break;
            default:
                // No bosses for other screens
                break;
        }
    }

    public void update(float delta) {
        if (player != null && !gameOver) {
            // 1. Physics step
            world.step(1/60f, 6, 2);
            
            // 2. Update entities
            player.update(delta);
            
            // Update all bosses
            for (Boss boss : bosses) {
                boss.update(delta);
            }
            
            // 3. Handle deferred operations AFTER physics step
            
            // 3a. Handle player respawn if needed
            if (player.needsRespawn()) {
                player.respawn();
            }
            
            // 3b. Remove dead player (check after respawn)
            if (!player.isAlive()) {
                player.destroy();

                //gameOver = true;
            }
            
            // 3c. Remove dead bosses (deferred destruction)
            Iterator<Boss> bossIter = bosses.iterator();
            while (bossIter.hasNext()) {
                Boss boss = bossIter.next();
                if (!boss.isAlive() || boss.needsDestruction()) {
                    boss.destroy();
                    bossIter.remove();
                }
            }

            // 4. Add new player bullets after physics step
            ArrayList<Bullet> bulletsToAdd = new ArrayList<>(player.getBulletsToAdd());
            for (Bullet b : bulletsToAdd) {
                b.createBody(world);
                player.getBullets().add(b);
            }
            player.getBulletsToAdd().clear();

            // 5. Clean up player bullets that are marked for destruction or inactive
            Iterator<Bullet> bulletIter = player.getBullets().iterator();
            while (bulletIter.hasNext()) {
                Bullet b = bulletIter.next();
                if (b.shouldBeDestroyed() || !b.isActive()) {
                    b.destroy();
                    bulletIter.remove();
                }
            }
            
            // 6. Update and clean up boss bullets
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
            
            // 7. Clean up items marked for destruction
            Iterator<Items> itemIter = items.iterator();
            while (itemIter.hasNext()) {
                Items item = itemIter.next();
                if (item instanceof AbstractItem) {
                    AbstractItem abstractItem = (AbstractItem) item;
                    if (abstractItem.shouldBeDestroyed()) {
                        if (abstractItem.getBody() != null) {
                            world.destroyBody(abstractItem.getBody());
                        }
                        itemIter.remove();
                    }
                }
            }
            
            // 8. Apply damping to player's horizontal velocity
            if(player.getBody() != null) {
                Body pBody = player.getBody();
                pBody.setLinearVelocity(pBody.getLinearVelocity().x * 0.9f, pBody.getLinearVelocity().y);
            }
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
    
    public ArrayList<Items> getItems() {
        return items;
    }
    
    public void spawnItem(ItemType type, float x, float y) {
        Items item = ItemFactory.createItem(type);
        item.spawn(world, x, y);
        items.add(item);
    }
    
    public void spawnRandomItem(float x, float y) {
        Items item = ItemFactory.createRandomItem();
        item.spawn(world, x, y);
        items.add(item);
    }
}