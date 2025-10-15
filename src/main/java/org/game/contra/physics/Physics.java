package org.game.contra.physics;

import org.game.contra.entities.Player;

import java.util.List;

public class Physics {

    private static final double GRAVITY = 9.8;

    public void update(Player player, List<Platform> platforms, double deltaTime) {
        // Apply gravity
        player.setVelocity(new javafx.geometry.Point2D(player.getVelocity().getX(), player.getVelocity().getY() + GRAVITY * deltaTime));

        // Update player position
        player.setPosition(new javafx.geometry.Point2D(player.getPosition().getX() + player.getVelocity().getX() * deltaTime, player.getPosition().getY() + player.getVelocity().getY() * deltaTime));

        // Check for collisions with platforms
        player.setGrounded(false);
        for (Platform platform : platforms) {
            if (player.getBounds().intersects(platform.getBounds())) {
                player.setGrounded(true);
                player.setVelocity(new javafx.geometry.Point2D(player.getVelocity().getX(), 0));
                player.setPosition(new javafx.geometry.Point2D(player.getPosition().getX(), platform.getBounds().getMinY() - player.getBounds().getHeight()));
            }
        }
    }
}