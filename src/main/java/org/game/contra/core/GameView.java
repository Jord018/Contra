package org.game.contra.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import org.game.contra.RunGunGame;
import org.game.contra.entities.Player;

public class GameView implements Disposable {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private GameModel model;
    private Box2DDebugRenderer debugRenderer;
    private boolean debug = true; // Toggle debug rendering

    public GameView(GameModel model) {
        this.model = model;
        init();
    }

    private ShapeRenderer shapeRenderer;
    
    private void init() {
        // Set up camera with a fixed viewport size in meters (smaller values = more zoomed in)
        float viewportWidth = 20f; // 15 meters wide
        float viewportHeight = 10f; // 10 meters tall
        
        camera = new OrthographicCamera(viewportWidth, viewportHeight);
        camera.update();

        // Set up sprite batch
        batch = new SpriteBatch();
        
        // Set up shape renderer for debug drawing
        shapeRenderer = new ShapeRenderer();
        
        // Set up debug renderer
        debugRenderer = new Box2DDebugRenderer();
        debugRenderer.setDrawBodies(true);
        debugRenderer.setDrawJoints(true);
        debugRenderer.setDrawAABBs(true);
        
        // Log initial camera settings
        System.out.println("Camera viewport set to: " + viewportWidth + "x" + viewportHeight + " meters");
    }

    public void update(float delta) {
        // Update camera position to follow player
        if (model.getPlayer() != null) {
            // Center camera on player
            float playerX = model.getPlayer().getPosition().x + model.getPlayer().getWidth() / 2;
            float playerY = model.getPlayer().getPosition().y + model.getPlayer().getHeight() / 2;
            
            System.out.println("Player position: (" + playerX + ", " + playerY + ")");
            
            camera.position.set(
                playerX,
                playerY,
                0
            );
        }
        camera.update();
        
        // Log camera position
        System.out.println("Camera position: (" + camera.position.x + ", " + camera.position.y + ")");
        System.out.println("Viewport: (" + camera.viewportWidth + "x" + camera.viewportHeight + ")");
    }

    public void render() {
        // Clear screen
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.8f, 1); // Light blue sky
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw ground
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        // Draw a checkerboard ground for better depth perception
                shapeRenderer.setColor(0.1f, 0.5f, 0.2f, 1);
                shapeRenderer.rect(9, -50, 50, 1);

        shapeRenderer.end();

        // Set up sprite batch with camera's combined projection matrix
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Draw game objects here
        Player player = model.getPlayer();
        if (player != null) {
            player.draw(batch);
        }

        // Draw HUD, score, etc.
        // batch.draw(...)

        batch.end();
        
        // Debug render the Box2D world
        if (debug && model.getWorld() != null) {
            debugRenderer.render(model.getWorld(), camera.combined);
        }
    }

    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
        }
        if (debugRenderer != null) {
            debugRenderer.dispose();
        }
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }
}