package org.game.contra.entities.Shooting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.graphics.Texture;
import org.game.contra.entities.Boss;
import org.game.contra.entities.Bullet;
import org.game.contra.entities.Player;
import org.game.contra.entities.Shooting.ShootingStrategy;

import java.util.List;

public class AimedShoot implements ShootingStrategy {
    private final Player target;

    public AimedShoot(Player target) {
        this.target = target;
    }

    @Override
    public void shoot(Boss boss, World world, Texture bulletTexture, List<Bullet> bullets) {
        if (target == null) return;

        Vector2 bossPos = new Vector2(boss.getPosition().x, boss.getPosition().y + boss.getHeight() / 2);
        Vector2 dir = target.getPosition().cpy().sub(bossPos); // หาผู้เล่น
        dir.nor();

        Gdx.app.log("Boss", "Aimed shoot towards player!");

        Bullet bullet = new Bullet(Vector2.Zero, 12, 10f, bulletTexture);
        bullet.setPosition(bossPos);
        bullet.setDirection(dir);
        bullet.createBody(world);
        bullets.add(bullet);
    }
}
