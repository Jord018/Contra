package org.game.contra.entities.Items;

import org.game.contra.entities.Player;
import org.game.contra.entities.Weapons.WeaponType;

public class MachineItem extends AbstractItem {

    public MachineItem() {
        // Texture loading will be handled by the view
    }

    @Override
    public void applyEffect(Player player) {
        player.setWeaponType(WeaponType.MACHINE);
        System.out.println("Machine Gun obtained!");
    }

    @Override
    public String getName() {
        return "Machine Gun";
    }
}