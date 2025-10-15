package org.game.contra;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.game.contra.controller.GameController;
import org.game.contra.core.GameModel;
import org.game.contra.core.GameView;
import org.game.contra.entities.Player;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class Main extends Application {

    private GameModel model;
    private GameView view;
    private GameController controller;
    private long lastUpdate = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Run and Gun Game");

        // Create a Canvas for rendering
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Create a layout pane and add the canvas to it
        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        // Create the scene
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        // Initialize MVC components
        Vec2 gravity = new Vec2(0, -9.8f);
        World world = new World(gravity);
        model = new GameModel(new Player(world, 5, 2), gravity, world, "Screen1");
        view = new GameView(model, "Stage/NES - Contra - Stages - Stage 1 (1).png");
        controller = new GameController(model, view, null); // Viewport is null for now

        // Handle keyboard input
        scene.setOnKeyPressed(controller::handleKeyPressed);
        scene.setOnKeyReleased(controller::handleKeyReleased);

        // Main game loop
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }
                double delta = (now - lastUpdate) / 1_000_000_000.0;
                lastUpdate = now;

                controller.update((float) delta);
                view.render(gc);
            }
        }.start();

        primaryStage.show();
    }
}