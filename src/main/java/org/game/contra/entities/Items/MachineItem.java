package org.game.contra.entities.Items;

import org.game.contra.entities.Player;
import org.game.contra.entities.Weapons.WeaponType;

public class MachineItem extends AbstractItem {

    public MachineItem() {
        this.texture = new com.badlogic.gdx.graphics.Texture("items/machine.png");
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
