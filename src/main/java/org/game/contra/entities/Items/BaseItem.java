package org.game.contra.entities.Items;

import javafx.geometry.Rectangle2D;
import org.game.contra.entities.Player;

public abstract class BaseItem implements Item {

    private final Rectangle2D bounds;

    public BaseItem(double x, double y, double width, double height) {
        this.bounds = new Rectangle2D(x, y, width, height);
    }

    @Override
    public Rectangle2D getBounds() {
        return bounds;
    }

    @Override
    public abstract void apply(Player player);
}