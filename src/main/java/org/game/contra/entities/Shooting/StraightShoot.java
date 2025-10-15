package org.game.contra.entities.Shooting;

import org.game.contra.entities.Bullet;
import java.util.List;

public class StraightShoot implements ShootingStrategy {

    @Override
    public void shoot(List<Bullet> bullets, double x, double y, double targetX, double targetY) {
        bullets.add(new Bullet(x, y, targetX, targetY));
    }
}