package org.game.contra.core;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import org.game.contra.core.Game_Manager;
import org.game.contra.entity.Bullet;
import org.game.contra.entity.Entity;
import org.game.contra.systems.RenderSystem;

public class GameLoop extends AnimationTimer {
    private final Game_Manager gameManager;
    private final Scene scene;
    private final input inputHandler;
    private final RenderSystem renderSystem;
    private long lastUpdate = 0;
    private static final double NANOSECONDS_PER_SECOND = 1_000_000_000.0;
    private static final double TARGET_UPS = 60.0; // Updates per second
    private static final double TIME_BETWEEN_UPDATES = NANOSECONDS_PER_SECOND / TARGET_UPS;
    public GameLoop(Scene scene, Game_Manager gameManager, input inputHandler, RenderSystem renderSystem) {
        this.scene = scene;
        this.gameManager = gameManager;
        this.inputHandler = inputHandler;
        this.renderSystem = renderSystem;

        // Set up input handling
        setupInputHandling();
    }

    private void setupInputHandling() {
        scene.setOnKeyPressed(inputHandler::handleKeyPress);
        scene.setOnKeyReleased(inputHandler::handleKeyRelease);
    }

    @Override
    public void handle(long now) {
        // Calculate delta time in seconds
        if (lastUpdate == 0) {
            lastUpdate = now;
            return;
        }
        
        double deltaTime = (now - lastUpdate) / NANOSECONDS_PER_SECOND;
        lastUpdate = now;
        
        // Process input
        boolean left = inputHandler.isMoveLeft();
        boolean right = inputHandler.isMoveRight();
        boolean jump = inputHandler.isJump();
        boolean attack = inputHandler.isShoot();
        
        // Update game state with current input
        updateGameState(left, right, jump, attack);
        
        // Clear the screen
        renderSystem.clear();
        
        // Render all active entities
        for (Entity entity : gameManager.getEntities()) {
            // Skip rendering inactive bullets
            if (entity instanceof Bullet && !((Bullet) entity).isActive()) {
                continue;
            }
            renderSystem.render(entity);
        }
    }
    
    private void updateGameState(boolean left, boolean right, boolean jump, boolean attack) {
        // Update game logic here
        gameManager.setLeftPressed(left);
        gameManager.setRightPressed(right);
        gameManager.setJumpPressed(jump);
        gameManager.setAttackPressed(attack);
        // Update game state
        gameManager.update();
        
        // You can add more game state updates here

    }
    
    @Override
    public void start() {
        super.start();
        lastUpdate = 0;
    }
}
