package org.game.contra.entities.Items;

import javafx.geometry.Rectangle2D;
import org.game.contra.entities.Player;

public interface Item {

    Rectangle2D getBounds();

    void apply(Player player);
}