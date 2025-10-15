package org.game.contra;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Contra");
        SceneManager sceneManager = new SceneManager(WIDTH, HEIGHT);
        Scene scene = sceneManager.createGameScene();
        primaryStage.setScene(scene);
        GraphicsContext gc = ((Canvas) scene.getRoot().getChildrenUnmodifiable().get(0)).getGraphicsContext2D();
        primaryStage.show();

        GameManager gameManager = new GameManager();
        new org.game.contra.controller.InputHandler(scene, gameManager.getModel().getPlayer());

        AnimationTimer gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }

                double deltaTime = (now - lastUpdate) / 1_000_000_000.0;
                lastUpdate = now;

                gameManager.update(deltaTime);
                gameManager.render(gc);
            }
        };
        gameLoop.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}