package org.game.contra.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.math.Matrix4;


public class GameView implements Disposable {
    private final GameModel model;
    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;
    private Texture background;
    private Box2DDebugRenderer debugRenderer;
    private boolean debug = true; // Toggle debug rendering
    private float screenWidth;
    private float screenHeight;

    private Matrix4 debugMatrix;

    public GameView(GameModel model) {
        this.model = model;
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();

        // Load background image
        background = new Texture(Gdx.files.internal("Stage/NES - Contra - Stages - Stage 1 (1).png"));
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
// In the GameView constructor, add this line after initializing debugMatrix:
        debugRenderer = new Box2DDebugRenderer();

        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight = Gdx.graphics.getHeight();
        debugMatrix = new Matrix4();
        debugMatrix.setToOrtho2D(0, 0, screenWidth / GameModel.PPM, screenHeight / GameModel.PPM);
    }
    public void render() {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update screen dimensions
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        // Begin drawing
        batch.begin();

        // Draw background to fill the entire screen
        if (background != null) {
            batch.draw(background, 0, 0, screenWidth, screenHeight);
        }

        // Draw game objects
        model.getPlayer().draw(batch);

        // End drawing
        batch.end();
        //debug render
        if (debug && debugRenderer != null) {
            debugRenderer.render(model.getWorld(), debugMatrix);
            // Debug position output
            Vector2 playerPos = model.getPlayer().getBody().getPosition();
            Gdx.app.log("Player Position", String.format("X: %.2f, Y: %.2f", playerPos.x, playerPos.y));
        }
    }

    public void resize(int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;
        debugMatrix.setToOrtho2D(0, 0, screenWidth / GameModel.PPM, screenHeight / GameModel.PPM);
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        if (background != null) {
            background.dispose();
        }
        if (debugRenderer != null) {
            debugRenderer.dispose();
        }
    }

}