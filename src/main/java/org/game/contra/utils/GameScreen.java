package org.game.contra.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import org.game.contra.RunGunGame;
import org.game.contra.screens.Screen1;
import org.game.contra.screens.Screen2;

public class GameScreen implements Screen {
    private final RunGunGame game;

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    private Screen currentScreen;

    public GameScreen(RunGunGame game) {
        this.game = game;
        setScreen(new Screen1(game));
    }

    public void setScreen(Screen newScreen) {
        if (currentScreen != null) {
            Gdx.app.log("GameScreen", "Switching from: " + currentScreen.getClass().getSimpleName());
            currentScreen.hide();
            currentScreen.dispose();
        }
        currentScreen = newScreen;
        Gdx.app.log("GameScreen", "Current screen set to: " + currentScreen.getClass().getSimpleName());
        game.setScreen(currentScreen);
    }

    @Override
    public void show() {
        if (currentScreen != null) {
            Gdx.app.log("GameScreen", "Showing screen: " + currentScreen.getClass().getSimpleName());
            currentScreen.show();
        }
    }

    @Override
    public void render(float delta) {
        if (currentScreen != null) currentScreen.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        if (currentScreen != null) currentScreen.resize(width, height);
    }

    @Override
    public void pause() {
        if (currentScreen != null) currentScreen.pause();
    }

    @Override
    public void resume() {
        if (currentScreen != null) currentScreen.resume();
    }

    @Override
    public void hide() {
        if (currentScreen != null) currentScreen.hide();
    }

    @Override
    public void dispose() {
        if (currentScreen != null) currentScreen.dispose();
    }
}
