package org.game.contra.controller;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.game.contra.core.GameModel;
import org.game.contra.core.GameView;
import org.game.contra.entities.Player;
import org.jbox2d.common.Vec2;

import java.util.HashSet;
import java.util.Set;

public class GameController {
    private final GameModel model;
    private final GameView view; // Not used for now, but might be needed later
    private final Set<KeyCode> activeKeys = new HashSet<>();

    public GameController(GameModel model, GameView view, Object viewport) { // Viewport is an Object for now
        this.model = model;
        this.view = view;
    }

    public void handleKeyPressed(KeyEvent event) {
        activeKeys.add(event.getCode());
    }

    public void handleKeyReleased(KeyEvent event) {
        activeKeys.remove(event.getCode());
    }

    public void update(float delta) {
        handleInput();
        model.update(delta);
    }

    private void handleInput() {
        Player player = model.getPlayer();
        if (player == null || player.getBody() == null) {
            return;
        }

        // Reset horizontal velocity
        player.getBody().setLinearVelocity(new Vec2(player.getBody().getLinearVelocity().x, player.getBody().getLinearVelocity().y));


        if (activeKeys.contains(KeyCode.RIGHT) || activeKeys.contains(KeyCode.D)) {
            player.moveRight();
        } else if (activeKeys.contains(KeyCode.LEFT) || activeKeys.contains(KeyCode.A)) {
            player.moveLeft();
        } else {
            // Stop horizontal movement if no movement keys are pressed
            player.getBody().setLinearVelocity(new Vec2(0, player.getBody().getLinearVelocity().y));
        }

        if (activeKeys.contains(KeyCode.S)) {
            player.moveDown();
        }

        if (activeKeys.contains(KeyCode.UP) || activeKeys.contains(KeyCode.W) || activeKeys.contains(KeyCode.SPACE)) {
            player.jump();
        }

        if (activeKeys.contains(KeyCode.F)) { // Assuming 'F' is for shooting
            player.shoot(null); // Viewport is null for now
        }
    }
}