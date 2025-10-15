package org.game.contra;

import javafx.scene.canvas.GraphicsContext;
import org.game.contra.core.GameModel;
import org.game.contra.core.GameView;

public class GameManager {

    private final GameModel model;
    private final GameView view;

    public GameManager() {
        this.model = new GameModel("Screen1");
        this.view = new GameView(model);
    }

    public GameModel getModel() {
        return model;
    }

    public void update(double deltaTime) {
        model.update((float) deltaTime);
    }

    public void render(GraphicsContext gc) {
        view.render(gc);
    }
}