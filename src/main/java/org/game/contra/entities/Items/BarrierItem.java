package org.game.contra.entities.Items;

import org.game.contra.entities.Player;

public class BarrierItem extends AbstractItem {

    public BarrierItem() {
        this.texture = new com.badlogic.gdx.graphics.Texture("items/barrier.png");
    }

    @Override
    public void applyEffect(Player player) {
        player.setInvincible(true, 10f); // อมตะ 10 วิ
        System.out.println("Barrier activated!");
    }

    @Override
    public String getName() {
        return "Barrier";
    }
}
