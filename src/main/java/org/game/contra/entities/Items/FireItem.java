package org.game.contra.entities.Items;

import org.game.contra.entities.Player;

public class FireItem extends BaseItem {

    public FireItem(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public void apply(Player player) {
        // Give the player the fire gun
    }
}