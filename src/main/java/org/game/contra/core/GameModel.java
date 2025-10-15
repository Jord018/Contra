package org.game.contra.core;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.game.contra.entities.Boss;
import org.game.contra.entities.Bullet;
import org.game.contra.entities.Player;
import org.game.contra.entities.Items.AbstractItem;
import org.game.contra.entities.Items.Items;
import org.game.contra.utils.ItemFactory;
import org.game.contra.utils.WorldCreator;
import org.game.contra.entities.Items.ItemsType.ItemType;
import org.game.contra.entities.Shooting.AimedShoot;
import org.game.contra.entities.Shooting.StraightShoot;


import java.util.ArrayList;
import java.util.Iterator;

public class GameModel {
    private World world;
    private Player player;
    private ArrayList<Boss> bosses = new ArrayList<>();
    private ArrayList<Items> items = new ArrayList<>();
    private WorldCreator worldCreator;
    private Vec2 gravity;
    private boolean gameOver;
    private int score;

    public GameModel(Player player, Vec2 gravity, World world, String screenName) {
        this.gravity = gravity;
        this.world = world;
        this.gameOver = false;
        this.score = 0;

        // Contact listener needs to be re-implemented for JBox2D
        // this.world.setContactListener(new Contact());
        this.worldCreator = new WorldCreator(world, screenName);
        this.player = player;

        switch (screenName) {
            case "Screen1":
                bosses.add(new Boss(world, 12.45f, 3.75f, 0.5f, 0.5f, new StraightShoot()));
                bosses.add(new Boss(world, 11.50f, 3.75f, 0.5f, 0.5f, new StraightShoot()));
                bosses.add(new Boss(world, 11.75f, 1.85f, 1.0f, 1.0f, new StraightShoot()));
                spawnItem(ItemType.SPREAD, 8.0f, 2.5f);
                break;
            case "Screen2":
                bosses.add(new Boss(world, 9.0f, 5.5f, 3.75f, 5.5f, new AimedShoot(player)));
                break;
            default:
                break;
        }
    }

    public void update(float delta) {
        if (player != null && !gameOver) {
            world.step(1 / 60f, 6, 2);
            player.update(delta);

            for (Boss boss : bosses) {
                boss.update(delta);
            }

            if (player.needsRespawn()) {
                player.respawn();
            }

            if (!player.isAlive()) {
                // player.destroy();
                // gameOver = true;
            }

            Iterator<Boss> bossIter = bosses.iterator();
            while (bossIter.hasNext()) {
                Boss boss = bossIter.next();
                if (boss.needsDestruction()) {
                    boss.destroy();
                    bossIter.remove();
                }
            }

            // Bullet handling needs to be refactored
        }
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Boss> getBosses() {
        return bosses;
    }

    public World getWorld() {
        return world;
    }

    public ArrayList<Items> getItems() {
        return items;
    }

    public void spawnItem(ItemType type, float x, float y) {
        Items item = ItemFactory.createItem(type);
        item.spawn(world, x, y);
        items.add(item);
    }

    public void dispose() {
        // World disposal is not needed in JBox2D
    }
}