package org.game.contra.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.game.contra.core.GameModel;
import org.game.contra.core.GameView;
import org.game.contra.entities.Boss;
import org.game.contra.entities.Player;

import java.util.ArrayList;

public class GameController {
    private GameModel model;
    private GameView view;
    private StretchViewport viewport;
    
    public GameController(GameModel model, GameView view, StretchViewport viewport) {
        this.model = model;
        this.view = view;
        this.viewport = viewport;
    }
    
    public void update(float delta) {
        handleInput();
        handleBossInput();
        model.update(delta);

    }
    
    private void handleInput() {
        Player player = model.getPlayer();
        boolean anyKeyPressed = false;
        
        // Reset velocity
        player.getBody().setLinearVelocity(0, player.getBody().getLinearVelocity().y);
        
        // Handle movement
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            System.out.println("RIGHT key pressed");
            player.moveRight();
            anyKeyPressed = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            System.out.println("LEFT key pressed");
            player.moveLeft();
            anyKeyPressed = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            System.out.println("DOWN key pressed");
            player.moveDown();
            anyKeyPressed = true;
        }
        
        // Handle jumping
        if ((Gdx.input.isKeyJustPressed(Input.Keys.UP) || 
             Gdx.input.isKeyJustPressed(Input.Keys.W) || 
             Gdx.input.isKeyJustPressed(Input.Keys.SPACE))) {
            System.out.println("JUMP key pressed");
            player.jump();
            anyKeyPressed = true;
        }
        
        // Handle shooting
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            System.out.println("SHOOT key pressed");
            player.shoot(viewport);
            anyKeyPressed = true;
        }
        if (!anyKeyPressed) {

        }
    }

    private float bossShootCooldown = 0f;
    private final float BOSS_SHOOT_INTERVAL = 2.0f; // Shoot every 2 seconds

    private int currentBossIndex = 0; // Start from index 0 (first boss)

    private void handleBossInput() {
        // Update cooldown
        if (bossShootCooldown > 0) {
            bossShootCooldown -= Gdx.graphics.getDeltaTime();
            return; // Not time to shoot yet
        }

        ArrayList<Boss> aliveBosses = new ArrayList<>();
        for (Boss boss : model.getBosses()) {
            if (boss.isAlive()) {
                aliveBosses.add(boss);
            }
        }

        if (!aliveBosses.isEmpty() && aliveBosses.size() > 1) { // Need at least 2 bosses for sequencing
            // Sequential boss selection (excluding index 0)
            Boss selectedBoss = aliveBosses.get(currentBossIndex);

            // Update boss and make it shoot
            selectedBoss.update(Gdx.graphics.getDeltaTime());
            selectedBoss.shoot();

            // Move to next boss in sequence (0 -> 1 -> 0 -> 1 -> ...)
            currentBossIndex = 1 - currentBossIndex; // Toggle between 0 and 1

            // Reset cooldown for next shot
            bossShootCooldown = BOSS_SHOOT_INTERVAL;

            Gdx.app.log("BossController", "Boss " + (currentBossIndex - 1) + " shooting, next shot in " + BOSS_SHOOT_INTERVAL + " seconds");
        }
    }

    public void resize(int width, int height) {
        view.resize(width, height);
    }

    public void dispose() {
        model.dispose();
        view.dispose();
    }
}
