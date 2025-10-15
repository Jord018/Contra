package org.game.contra.entities.Items;

import org.game.contra.entities.Player;
import org.game.contra.entities.Weapons.WeaponType;

public class LaserItem extends AbstractItem {

    public LaserItem() {
        // Texture loading will be handled by the view
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