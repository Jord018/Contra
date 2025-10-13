package org.game.contra.entities.Items;

import org.game.contra.entities.Player;
import org.game.contra.entities.Weapons.WeaponType;

public class FireItem extends AbstractItem {

    public FireItem() {
        this.texture = new com.badlogic.gdx.graphics.Texture("items/fire.png");
    }

    @Override
    public void applyEffect(Player player) {
        player.setWeaponType(WeaponType.FIRE);
        System.out.println("Fire Gun obtained!");
    }

    @Override
    public String getName() {
        return "Fire Gun";
    }
}
