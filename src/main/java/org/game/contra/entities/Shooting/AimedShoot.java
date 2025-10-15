package org.game.contra.entities.Shooting;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.game.contra.entities.Boss;
import org.game.contra.entities.Bullet;
import org.game.contra.entities.Bullet.BulletOwner;
import org.game.contra.entities.Player;

import java.util.List;

public class AimedShoot implements ShootingStrategy {
    private final Player target;

    public AimedShoot(Player target) {
        this.target = target;
    }

    @Override
    public void shoot(Boss boss, World world, List<Bullet> bullets) {
        if (target == null) return;

        Vec2 bossPos = new Vec2(boss.getPosition().x, boss.getPosition().y + boss.getHeight() / 2);
        Vec2 targetPos = target.getPosition();
        Vec2 dir = targetPos.sub(bossPos);
        dir.normalize();

        Bullet bullet = new Bullet(dir, 12, 10f, BulletOwner.BOSS);
        bullet.setPosition(bossPos);
        bullet.createBody(world);
        bullets.add(bullet);
    }
}