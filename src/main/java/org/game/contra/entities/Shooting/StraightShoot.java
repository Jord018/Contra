package org.game.contra.entities.Shooting;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.game.contra.entities.Boss;
import org.game.contra.entities.Bullet;
import org.game.contra.entities.Bullet.BulletOwner;

import java.util.List;

public class StraightShoot implements ShootingStrategy {
    @Override
    public void shoot(Boss boss, World world, List<Bullet> bullets) {
        Vec2 bulletPos = new Vec2(boss.getPosition().x - 0.15f, boss.getPosition().y + boss.getHeight() / 2);
        Bullet bullet = new Bullet(new Vec2(-1, 0), 10, 10f, BulletOwner.BOSS);
        bullet.setPosition(bulletPos);
        bullet.createBody(world);
        bullets.add(bullet);
    }
}