package org.game.contra.systems;

import org.game.contra.components.TransformComponent;
import org.game.contra.entity.*;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RenderSystem {
    private final GraphicsContext gc;
    private final CollisionSystem collisionSystem;

    public RenderSystem(GraphicsContext gc, CollisionSystem collisionSystem) {
        this.gc = gc;
        this.collisionSystem = collisionSystem;
    }

    public void render(Entity entity) {
        TransformComponent transform = entity.getComponent(TransformComponent.class);
        if (transform == null) return;

        double x = transform.getX();
        double y = transform.getY();
        double width = transform.getWidth();
        double height = transform.getHeight();

        // Save the current fill color
        Color originalColor = (Color) gc.getFill();

        // Set color based on entity type
        if (entity instanceof Player) {
            gc.setFill(Color.BLUE);
            gc.fillRect(x, y, width, height);
        } else if (entity instanceof Boss) {
            gc.setFill(Color.RED);
            gc.fillRect(x, y, width, height);
        } else if (entity instanceof Bullet) {
            gc.setFill(Color.YELLOW);
            double radius = Math.min(width, height) / 2;
            gc.fillOval(x + width/2 - radius, y + height/2 - radius, 
                       radius * 2, radius * 2);
        }
        //set color for floor
        // Render all floors from collision system
        if (collisionSystem != null) {
            for (Floor floor : collisionSystem.getFloors()) {
            gc.setFill(Color.GRAY);  // or any color/texture
            gc.fillRect(floor.getX(), floor.getY(),
                    floor.getWidth(), floor.getHeight());
            }
        }
        // Restore the original fill color
        gc.setFill(originalColor);
    }

    public void clear() {
        // Clear the canvas
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }
}
