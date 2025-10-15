package org.game.contra.entities.Items;

import org.game.contra.entities.Player;
import org.game.contra.entities.Weapons.WeaponType;

public class SpreadItem extends AbstractItem {

    public SpreadItem() {
        // Texture loading will be handled by the view
    }

    @Override
    public void applyEffect(Player player) {
        player.setWeaponType(WeaponType.SPREAD);
        System.out.println("Spread Gun obtained!");
    }

    @Override
    public String getName() {
        return "Spread Gun";
    }
}