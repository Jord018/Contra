package org.game.contra.screens;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import org.game.contra.core.Game_Manager;
import org.game.contra.core.input;
import org.game.contra.entity.Boss;
import org.game.contra.entity.Entity;
import org.game.contra.entity.Floor;
import org.game.contra.entity.Player;
import org.game.contra.systems.CollisionSystem;
import org.game.contra.systems.RenderSystem;
import org.game.contra.core.GameLoop;

import java.util.ArrayList;
import java.util.List;

import static org.game.contra.entity.Floor.createFloor;

public class ScreenOne {
    private final Scene scene;
    private final Game_Manager gameManager;
    private final CollisionSystem collisionSystem;

    public ScreenOne() {
        // Create the root pane and scene
        Pane root = new Pane();
        this.scene = new Scene(root, 800, 600);

        // Create canvas
        Canvas canvas = new Canvas(800, 600);
        root.getChildren().add(canvas);

        // Initialize collision system
        List<Entity> entities = new ArrayList<>();
        this.collisionSystem = new CollisionSystem(entities, scene);

        // Create game manager
        this.gameManager = new Game_Manager(scene, collisionSystem);
        
        // Setup the screen
        setup();
    }

    private void setup() {
        // Create multiple floors at different positions
        List<Floor> floors = new ArrayList<>();
        
        // Ground floor (main platform at the bottom)
        floors.add(createFloor(0, 500, 800, 100,collisionSystem));
        
        // Some elevated platforms
        floors.add(createFloor(100, 400, 200, 20,collisionSystem));
        floors.add(createFloor(400, 400, 200, 20,collisionSystem));
        floors.add(createFloor(250, 300, 100, 20,collisionSystem));
        floors.add(createFloor(450, 300, 100, 20,collisionSystem));

        // Add all floors to entities list
        gameManager.getEntities().addAll(floors);

        // Create player and add to collision system
        Player player = new Player(100, 30);
        collisionSystem.setPlayer(player);
        gameManager.addEntity(player);
        //Create Boss and add to collision system
        Boss boss = new Boss(600, 30);
        collisionSystem.setBoss(boss);
        gameManager.addEntity(boss);
        // Create render system
        Canvas canvas = (Canvas) scene.getRoot().getChildrenUnmodifiable().get(0);
        RenderSystem renderSystem = new RenderSystem(canvas.getGraphicsContext2D(), collisionSystem);
        
        // Create input handler
        input inputHandler = new input();
        
        // Create and start game loop
        GameLoop gameLoop = new GameLoop(scene, gameManager, inputHandler, renderSystem);
        gameLoop.start();
    }


    public Scene getScene() {
        return scene;
    }
}
