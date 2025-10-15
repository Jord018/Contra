package org.game.contra.entities.Items;

import org.game.contra.entities.Player;

public class RapidItem extends AbstractItem {

    public RapidItem() {
        // Texture loading will be handled by the view
    }

    @Override
    public void applyEffect(Player player) {
        // player.increaseFireRate();
        System.out.println("Rapid Fire obtained!");
    }

    @Override
    public String getName() {
        return "Rapid Fire";
    }
}