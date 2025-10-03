package org.game.contra.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import org.game.contra.RunGunGame;
import org.game.contra.controller.GameController;
import org.game.contra.core.GameModel;
import org.game.contra.core.GameView;

public class GameScreen implements Screen {
    private final RunGunGame game;
    private GameModel model;
    private GameView view;
    private GameController controller;

    public GameScreen(RunGunGame game) {
        this.game = game;

        // Initialize model
        model = new GameModel();

        // Initialize view
        view = new GameView(model);
        // Initialize controller
        controller = new GameController(model, view);
    }

    public void update(float delta) {
        // Update game state through controller
        controller.update(delta);
    }

    @Override
    public void show() {
        // Set this screen to handle input
        Gdx.input.setInputProcessor(new com.badlogic.gdx.InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                // This will make sure keyJustPressed events work
                return true;
            }
            
            @Override
            public boolean keyUp(int keycode) {
                return true;
            }
        });
        
        // Enable continuous polling of the keyboard
        Gdx.input.setCatchKey(Input.Keys.LEFT, true);
        Gdx.input.setCatchKey(Input.Keys.RIGHT, true);
        Gdx.input.setCatchKey(Input.Keys.UP, true);
        Gdx.input.setCatchKey(Input.Keys.DOWN, true);
        Gdx.input.setCatchKey(Input.Keys.SPACE, true);
        Gdx.input.setCatchKey(Input.Keys.ENTER, true);
        Gdx.input.setCatchKey(Input.Keys.A, true);
        Gdx.input.setCatchKey(Input.Keys.D, true);
        Gdx.input.setCatchKey(Input.Keys.W, true);
    }

    @Override
    public void render(float delta) {
        // Update game state
        update(delta);
        
        // Update view (including camera position)
        view.update(delta);

        // Render the game through the view
        view.render();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
    public void hide() {
    }

    @Override
    public void dispose() {
        if (view != null) {
            view.dispose();
        }
        if (model != null) {
            model.dispose();
        }
    }
}