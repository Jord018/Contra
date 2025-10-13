package org.game.contra;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.game.contra.utils.GameScreen;

public class RunGunGame extends Game {
    public static final float V_HEIGHT  = 9f;
    public static final float V_WIDTH = 16f;
    public static final float PPM = 100f; // Pixels Per Meter
    public FitViewport viewport;
    
    // Box2D Collision Bits
    public static final short NOTHING_BIT = 0;
    public static final short GROUND_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short OBJECT_BIT = 4;
    public static final short PLATFORM_BIT = 8;
    public static final short ENEMY_BIT = 16;
    public static final short ENEMY_BULLET_BIT = 32;
    public static final short BULLET_BIT = 64;
    public static final short ITEM_BIT = 128;
    
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
