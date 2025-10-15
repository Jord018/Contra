package org.game.contra.controller;

import javafx.scene.Scene;
import org.game.contra.entities.Player;

public class InputHandler {

    private final Player player;

    public InputHandler(Scene scene, Player player) {
        this.player = player;

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case A:
                case LEFT:
                    player.setVelocity(new javafx.geometry.Point2D(-5, player.getVelocity().getY()));
                    break;
                case D:
                case RIGHT:
                    player.setVelocity(new javafx.geometry.Point2D(5, player.getVelocity().getY()));
                    break;
                case W:
                case UP:
                case SPACE:
                    player.setVelocity(new javafx.geometry.Point2D(player.getVelocity().getX(), -10));
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case A:
                case LEFT:
                case D:
                case RIGHT:
                    player.setVelocity(new javafx.geometry.Point2D(0, player.getVelocity().getY()));
                    break;
            }
        });

        scene.setOnMousePressed(event -> {
            // Handle mouse press events
        });
    }
}