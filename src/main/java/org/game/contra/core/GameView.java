package org.game.contra.core;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.game.contra.entities.Player;

import java.io.InputStream;

public class GameView {
    private final GameModel model;
    private Image background;

    public GameView(GameModel model, String imagePath) {
        this.model = model;
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(imagePath);
            if (is == null) {
                throw new IllegalArgumentException("File not found: " + imagePath);
            }
            background = new Image(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void render(GraphicsContext gc) {
        // Clear the screen
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        // Draw background
        if (background != null) {
            gc.drawImage(background, 0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        }

        // Draw player
        Player player = model.getPlayer();
        if (player != null && player.isAlive()) {
            // This is a placeholder for player rendering.
            // In a real game, you would draw a sprite here.
            gc.setFill(Color.BLUE);
            gc.fillRect(player.getBody().getPosition().x * 50, //
                    gc.getCanvas().getHeight() - player.getBody().getPosition().y * 50, 20, 40);
        }

        // In a real game, you'd also render enemies, bullets, items, etc.
    }
}