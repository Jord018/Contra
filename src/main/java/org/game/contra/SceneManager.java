package org.game.contra;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class SceneManager {

    private final int width;
    private final int height;

    public SceneManager(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Scene createGameScene() {
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Pane root = new Pane(canvas);
        return new Scene(root, width, height);
    }
}