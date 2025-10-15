package org.game.contra.entities.Shooting;

import org.jbox2d.dynamics.World;
import org.game.contra.entities.Boss;
import org.game.contra.entities.Bullet;

import java.util.List;

public interface ShootingStrategy {
    void shoot(Boss boss, World world, List<Bullet> bullets);
}