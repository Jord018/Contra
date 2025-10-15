package org.game.contra.entities.Shooting;

import org.game.contra.entities.Bullet;
import java.util.List;

public interface ShootingStrategy {
    void shoot(List<Bullet> bullets, double x, double y, double targetX, double targetY);
}