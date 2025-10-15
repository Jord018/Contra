package org.game.contra.entities.Items;

import org.game.contra.entities.Player;

public class MachineItem extends BaseItem {

    public MachineItem(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public void apply(Player player) {
        // Give the player the machine gun
    }
}