package org.game.contra.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.game.contra.RunGunGame;

public class DesktopLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Run and Gun Game");
        config.setWindowedMode(800, 600);
        config.setForegroundFPS(60);
        config.useVsync(true);
        new Lwjgl3Application(new RunGunGame(), config);
    }
}
