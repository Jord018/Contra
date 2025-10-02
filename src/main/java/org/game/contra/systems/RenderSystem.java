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
        // Skip rendering if entity is a bullet that's not active
        if (entity instanceof Bullet && !((Bullet) entity).isActive()) {
            return;
        }

        TransformComponent transform = entity.getComponent(TransformComponent.class);
        if (transform == null) return;

        double x = transform.getX();
        double y = transform.getY();
        double width = transform.getWidth();
        double height = transform.getHeight();

        // Save the current fill color and stroke
        Color originalFill = (Color) gc.getFill();
        Color originalStroke = (Color) gc.getStroke();
        double originalLineWidth = gc.getLineWidth();

        // Set color and render based on entity type
        if (entity instanceof Player) {
            // Draw player as a blue rectangle
            gc.setFill(Color.BLUE);
            gc.fillRect(x, y, width, height);
            // Add a face or other distinguishing feature
            gc.setFill(Color.WHITE);
            gc.fillOval(x + 5, y + 10, 10, 10);
            gc.fillOval(x + width - 15, y + 10, 10, 10);
        } else if (entity instanceof Boss) {
            // Draw boss as a red rectangle with a different shape
            gc.setFill(Color.RED);
            gc.fillRect(x, y, width, height);
        } else if (entity instanceof Bullet) {
            // Draw bullet as a yellow circle with an outline
            gc.setFill(Color.YELLOW);
            gc.setStroke(Color.ORANGERED);
            gc.setLineWidth(1);
            double radius = Math.min(width, height) / 2;
            gc.fillOval(x, y, radius * 2, radius * 2);
            gc.strokeOval(x, y, radius * 2, radius * 2);
        } else if (entity instanceof Floor floor) {
            // Draw floor as a gray rectangle with a simple pattern
            gc.setFill(Color.GRAY);
            gc.fillRect(floor.getX(), floor.getY(), floor.getWidth(), floor.getHeight());
            
            // Add some simple floor pattern
            gc.setFill(Color.DARKGRAY);
            for (int i = 0; i < floor.getWidth(); i += 20) {
                gc.fillRect(floor.getX() + i, floor.getY(), 10, 5);
            }
        }

        // Restore the original graphics context state
        gc.setFill(originalFill);
        gc.setStroke(originalStroke);
        gc.setLineWidth(originalLineWidth);
    }

    public void clear() {
        // Clear the canvas
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }
}
