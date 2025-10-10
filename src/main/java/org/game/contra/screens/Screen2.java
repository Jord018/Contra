package org.game.contra.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.game.contra.RunGunGame;
import org.game.contra.controller.GameController;
import org.game.contra.core.GameModel;
import org.game.contra.core.GameView;
import org.game.contra.entities.Player;

public class Screen2 implements Screen {
    private final RunGunGame game;
    private GameModel model;
    private GameView view;
    private GameController controller;

    public Screen2(RunGunGame game) {
        this.game = game;
        Vector2 gravity = new Vector2(0, -9.8f);
        World world = new World(gravity, true);

        // Model
        model = new GameModel(new Player(world, 5, 2), gravity, world, "Screen2");

        // View
        view = new GameView(model, "Stage/NES - Contra - Stages - Stage 8.png");

        // Controller
        controller = new GameController(model, view, view.getViewport());
    }

    private void update(float delta) {
        controller.update(delta);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(null); //
    }

    @Override
    public void render(float delta) {
        update(delta);
        view.render();
    }

    @Override
    public void resize(int width, int height) {
        if (view != null) view.resize(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        if (view != null) view.dispose();
        if (model != null) model.dispose();
    }
}
