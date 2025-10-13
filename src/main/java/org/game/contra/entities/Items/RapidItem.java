package org.game.contra.entities.Items;

import org.game.contra.entities.Player;

public class RapidItem extends AbstractItem {

    public RapidItem() {
        this.texture = new com.badlogic.gdx.graphics.Texture("items/rapid.png");
    }

    @Override
    public void applyEffect(Player player) {
        player.increaseFireRate();
        System.out.println("Rapid Fire activated!");
    }

    @Override
    public String getName() {
        return "Rapid Fire";
    }
}
