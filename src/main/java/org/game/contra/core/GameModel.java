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
    private Boss boss;
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
        this.player = new Player(world, 5, 2);
        this.boss = new Boss(world, 12, 2);

    }

    public void update(float delta) {
        if (player != null && !gameOver) {

            // 1️⃣ Physics step ก่อน update player
            world.step(1/60f, 6, 2);

            // 2️⃣ Update player
            player.update(delta);
            boss.update(delta);

            // 3️⃣ สร้าง Bullet หลัง physics step เสร็จ
            ArrayList<Bullet> bulletsToAdd = new ArrayList<>(player.getBulletsToAdd());
            for (Bullet b : bulletsToAdd) {
                b.createBody(world);  // ปลอดภัยเพราะ step เสร็จแล้ว
                player.getBullets().add(b);
            }
            player.getBulletsToAdd().clear();

            // 4️⃣ ลบ bullets ที่ไม่ active
            Iterator<Bullet> iter = player.getBullets().iterator();
            while (iter.hasNext()) {
                Bullet b = iter.next();
                if (!b.isActive()) {
                    b.destroy();   // safe
                    iter.remove();
                }
            }

            // 5️⃣ Clear applied horizontal velocity
            Body pBody = player.getBody();
            pBody.setLinearVelocity(pBody.getLinearVelocity().x * 0.9f, pBody.getLinearVelocity().y);
        }
    }


    // Getters and setters
    public Player getPlayer() {
        return player;
    }
    public Boss getBoss() {
        return boss;
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
        if (boss != null) {
            boss.dispose();
        }
    }

    public void addScore(int points) {
        this.score += points;
    }
}