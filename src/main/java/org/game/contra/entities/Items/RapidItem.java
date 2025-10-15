package org.game.contra.entities.Items;

import org.game.contra.entities.Player;

public class RapidItem extends BaseItem {

    public RapidItem(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public void apply(Player player) {
        // Increase the player's firing rate
    }
}