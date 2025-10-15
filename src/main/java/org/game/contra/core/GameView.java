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

        // Draw the player
        gc.setFill(Color.BLUE);
        gc.fillRect(model.getPlayer().getBounds().getMinX(), model.getPlayer().getBounds().getMinY(), model.getPlayer().getBounds().getWidth(), model.getPlayer().getBounds().getHeight());

        // Draw the platforms
        gc.setFill(Color.GREEN);
        for (org.game.contra.physics.Platform platform : model.getPlatforms()) {
            gc.fillRect(platform.getBounds().getMinX(), platform.getBounds().getMinY(), platform.getBounds().getWidth(), platform.getBounds().getHeight());
        }

        // Draw the bosses
        gc.setFill(Color.RED);
        for (org.game.contra.entities.Boss boss : model.getBosses()) {
            gc.fillRect(boss.getBounds().getMinX(), boss.getBounds().getMinY(), boss.getBounds().getWidth(), boss.getBounds().getHeight());
        }
    }
}