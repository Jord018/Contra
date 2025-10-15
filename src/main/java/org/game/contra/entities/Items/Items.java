package org.game.contra.entities.Items;

import org.jbox2d.dynamics.World;
import org.game.contra.entities.Player;

public interface Items {
    void applyEffect(Player player);
    void spawn(World world, float x, float y);
    String getName();
    void markForDestruction();
}