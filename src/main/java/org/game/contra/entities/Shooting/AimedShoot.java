package org.game.contra.entities.Shooting;

import org.game.contra.entities.Bullet;
import org.game.contra.entities.Player;

import java.util.List;

public class AimedShoot implements ShootingStrategy {

    private final Player player;

    public AimedShoot(Player player) {
        this.player = player;
    }

    @Override
    public void shoot(List<Bullet> bullets, double x, double y, double targetX, double targetY) {
        double angle = Math.atan2(player.getPosition().getY() - y, player.getPosition().getX() - x);
        double newTargetX = x + Math.cos(angle);
        double newTargetY = y + Math.sin(angle);
        bullets.add(new Bullet(x, y, newTargetX, newTargetY));
    }
}