package org.game.contra.entities.Shooting;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.game.contra.entities.Boss;
import org.game.contra.entities.Bullet;

import java.util.List;

public interface ShootingStrategy {
    void shoot(Boss boss, World world, Texture bulletTexture, List<Bullet> bullets);
}
