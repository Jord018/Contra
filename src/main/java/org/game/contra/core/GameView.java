package org.game.contra.core;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class GameView {

    private final GameModel model;
    private final Image background;

    public GameView(GameModel model) {
        this.model = model;
        this.background = new Image(getClass().getResourceAsStream(model.getBackgroundPath()));
    }

    public void render(GraphicsContext gc) {
        // Clear the screen
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        // Draw the background
        gc.drawImage(background, 0, 0);
    }
}