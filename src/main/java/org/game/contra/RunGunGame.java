package org.game.contra;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.game.contra.screens.GameScreen;

public class RunGunGame extends Game {
    public static final int V_WIDTH = 800;
    public static final int V_HEIGHT = 600;
    public static final float PPM = 100f; // Pixels Per Meter
    
    // Box2D Collision Bits
    public static final short NOTHING_BIT = 0;
    public static final short GROUND_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short OBJECT_BIT = 4;
    public static final short ENEMY_BIT = 8;
    public static final short BULLET_BIT = 16;
    
    public SpriteBatch batch;
    
    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new GameScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
