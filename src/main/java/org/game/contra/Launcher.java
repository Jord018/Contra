package org.game.contra;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.game.contra.core.GameLoop;
import org.game.contra.core.Game_Manager;
import org.game.contra.entity.Floor;
import org.game.contra.systems.CollisionSystem;
import org.game.contra.systems.RenderSystem;
import org.game.contra.core.input;

import java.util.ArrayList;

public class Launcher extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Create the root pane and scene first
        Pane root = new Pane();
        Scene gameScene = new Scene(root, 800, 600);

        // Create canvas
        Canvas canvas = new Canvas(800, 600);
        root.getChildren().add(canvas);

        // Create game components
        Game_Manager gameManager = new Game_Manager(gameScene);
        input inputHandler = new input();
        // Create render system with the canvas and collision system
        RenderSystem renderSystem = new RenderSystem(canvas.getGraphicsContext2D(), gameManager.getCollisionSystem());

        // Create game loop with the scene and systems
        GameLoop gameLoop = new GameLoop(gameScene, gameManager, inputHandler, renderSystem);

        // Set up the stage
        primaryStage.setTitle("Contra Game");
        primaryStage.setScene(gameScene);
        primaryStage.show();

        // Start the game loop after the stage is shown
        gameLoop.start();
    }
}