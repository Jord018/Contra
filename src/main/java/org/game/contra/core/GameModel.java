package org.game.contra.core;

import org.game.contra.entities.Boss;
import org.game.contra.entities.Items.Item;
import org.game.contra.entities.Player;
import org.game.contra.physics.Physics;
import org.game.contra.physics.Platform;

import java.util.ArrayList;
import java.util.List;

public class GameModel {

    private final Player player;
    private final List<Boss> bosses;
    private final List<Item> items;
    private final List<Platform> platforms;
    private final Physics physics;
    private final String backgroundPath;

    public GameModel(String screenName) {
        this.player = new Player(100, 100);
        this.bosses = new ArrayList<>();
        this.items = new ArrayList<>();
        this.platforms = new ArrayList<>();
        this.physics = new Physics();

        // Initialize game objects based on screen name
        switch (screenName) {
            case "Screen1":
                this.backgroundPath = "/assets/Stage/NES - Contra - Stages - Stage 1 (1).png";
                // Add platforms for Screen1
                platforms.add(new Platform(0, 500, 800, 100));
                break;
            case "Screen2":
                this.backgroundPath = "/assets/Stage/NES - Contra - Stages - Stage 8.png";
                // Add platforms for Screen2
                platforms.add(new Platform(0, 500, 400, 100));
                platforms.add(new Platform(500, 500, 300, 100));
                break;
            default:
                this.backgroundPath = "";
                break;
        }
    }

    public void update(float deltaTime) {
        physics.update(player, platforms, deltaTime);
    }

    public Player getPlayer() {
        return player;
    }

    public List<Boss> getBosses() {
        return bosses;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public String getBackgroundPath() {
        return backgroundPath;
    }
}