package org.game.contra.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.game.contra.RunGunGame;
import org.game.contra.entities.Boss;
import org.game.contra.entities.Bullet;


public class GameView implements Disposable {
    private final GameModel model;
    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;
    private Texture background;
    private Box2DDebugRenderer debugRenderer;
    private boolean debug = true; // Toggle debug rendering

    public StretchViewport getViewport() {
        return viewport;
    }

    private StretchViewport viewport;



    public GameView(GameModel model,String path) {
        this.model = model;
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        
        // Create viewport with world units
        this.viewport = new StretchViewport(RunGunGame.V_WIDTH, RunGunGame.V_HEIGHT);
        
        // Set camera position to center of the world
        viewport.getCamera().position.set(RunGunGame.V_WIDTH/2f, RunGunGame.V_HEIGHT/2f, 0);
        viewport.getCamera().update();
        
        // Load background image
        try {
            background = new Texture(Gdx.files.internal(path));
            background.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        } catch (Exception e) {
            Gdx.app.error("GameView", "Could not load background image", e);
        }

        // Initialize debug renderer
        debugRenderer = new Box2DDebugRenderer();
    }
    public void render() {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update viewport and set projection matrix
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        
        // Begin drawing
        batch.begin();

        // Draw background to fill the entire screen
        if (background != null) {
            batch.draw(background,
                0, 0,                        // x, y position
                RunGunGame.V_WIDTH,          // width in world units
                RunGunGame.V_HEIGHT,         // height in world units
                0, 0,                       // srcX, srcY
                background.getWidth(),       // srcWidth
                background.getHeight(),      // srcHeight
                false,                      // flipX
                false);                     // flipY
        }
        batch.end();
        // Draw game objects
        if (model.getPlayer() != null) {
            if (model.getPlayer().isAlive()) {
                model.getPlayer().draw(batch);
            }

            // Draw all bosses and their bullets
            for (Boss boss : model.getBosses()) {
                if (boss.isAlive()) {
                    boss.draw(batch);

                    // Draw boss bullets
                    batch.begin();
                    for (Bullet bullet : boss.getBullets()) {
                        if (bullet.isActive()) {
                            bullet.render(batch);
                        }
                    }
                    batch.end();
                }
            }

            // Draw player bullets
            batch.begin();
            for (Bullet bullet : model.getPlayer().getBullets()) {
                if (bullet.isActive()) {
                    bullet.render(batch);
                }
            }
            batch.end();
            
            // Debug info
            if (!model.getBosses().isEmpty()) {
                Boss firstBoss = model.getBosses().get(0);
                Gdx.app.log("Boss", "Position: " + firstBoss.getPosition() + 
                                  ", Alive: " + firstBoss.isAlive() + 
                                  ", Bullets: " + firstBoss.getBullets().size());
            }
        }
        //debug render
        if (debug) {
            debugRenderer.render(model.getWorld(), viewport.getCamera().combined);
            // Debug position output
            Vector2 playerPos = model.getPlayer().getBody().getPosition();
            Vector3 camPos = viewport.getCamera().position;

            //Gdx.app.log("Player Position", String.format("X: %.2f, Y: %.2f", playerPos.x, playerPos.y));
            //System.out.println(model.getPlayer().getPosfoot());
        }
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        viewport.getCamera().position.set(RunGunGame.V_WIDTH/2f, RunGunGame.V_HEIGHT/2f, 0);
        viewport.getCamera().update();
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