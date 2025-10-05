package org.game.contra.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import org.game.contra.core.GameModel;
import org.game.contra.core.GameView;
import org.game.contra.entities.Player;

public class GameController {
    private GameModel model;
    private GameView view;
    
    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
    }
    
    public void update(float delta) {
        handleInput();
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
        if(Gdx.input.isButtonJustPressed(Input.Keys.ENTER)){
            System.out.println("SHOOT key pressed");
            player.shoot();
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
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            System.out.println("SHOOT key pressed");
            player.shoot();
            anyKeyPressed = true;
        }

        if (!anyKeyPressed) {

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
