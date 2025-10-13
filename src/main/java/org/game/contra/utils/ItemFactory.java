package org.game.contra.utils;

import org.game.contra.entities.Items.*;
import org.game.contra.entities.Items.ItemsType.ItemType;
import java.util.Random;

public class ItemFactory {

    private static final Random random = new Random();

    public static Items createItem(ItemType type) {
        switch (type) {
            case SPREAD:
                return new SpreadItem();
            case MACHINE:
                return new MachineItem();
            case LASER:
                return new LaserItem();
            case FIRE:
                return new FireItem();
            case RAPID:
                return new RapidItem();
            case BARRIER:
                return new BarrierItem();
            default:
                throw new IllegalArgumentException("Unknown ItemType: " + type);
        }
    }

    public static Items createRandomItem() {
        ItemType[] values = ItemType.values();
        int index = random.nextInt(values.length);
        return createItem(values[index]);
    }
}
