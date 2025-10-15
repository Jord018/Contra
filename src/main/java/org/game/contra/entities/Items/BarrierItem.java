package org.game.contra.entities.Items;

import org.game.contra.entities.Player;

public class BarrierItem extends BaseItem {

    public BarrierItem(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public void apply(Player player) {
        // Apply barrier effect to the player
    }
}