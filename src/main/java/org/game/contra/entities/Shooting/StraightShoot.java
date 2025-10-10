package org.game.contra.entities.Shooting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.graphics.Texture;
import org.game.contra.entities.Boss;
import org.game.contra.entities.Bullet;
import org.game.contra.entities.Bullet.BulletOwner;

import java.util.List;

public class StraightShoot implements ShootingStrategy {
    @Override
    public void shoot(Boss boss, World world, Texture bulletTexture, List<Bullet> bullets) {
        Gdx.app.log("Boss", "Straight shoot!");

        Vector2 bulletPos = new Vector2(boss.getPosition().x - 0.15f, boss.getPosition().y + boss.getHeight() / 2);
        Bullet bullet = new Bullet(Vector2.Zero, 10, 10f, bulletTexture, BulletOwner.BOSS);
        bullet.setPosition(bulletPos);
        bullet.setDirection(new Vector2(-1f, 0f)); // ยิงตรงไปซ้าย
        bullet.createBody(world);
        bullets.add(bullet);
    }
}
