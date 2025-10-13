package org.game.contra.entities.Items;

import org.game.contra.entities.Player;
import org.game.contra.entities.Weapons.WeaponType;

public class LaserItem extends AbstractItem {

    public LaserItem() {
        this.texture = new com.badlogic.gdx.graphics.Texture("items/laser.png");
    }

    @Override
    public void applyEffect(Player player) {
        player.setWeaponType(WeaponType.LASER);
        System.out.println("Laser Gun obtained!");
    }

    @Override
    public String getName() {
        return "Laser Gun";
    }
}
