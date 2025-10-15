package org.game.contra.entities.Items;

import org.game.contra.entities.Player;

public class BarrierItem extends AbstractItem {

    public BarrierItem() {
        // Texture loading will be handled by the view
    }

    @Override
    public void applyEffect(Player player) {
        // player.setInvincible(true, 5f);
        System.out.println("Barrier obtained!");
    }

    @Override
    public String getName() {
        return "Barrier";
    }
}