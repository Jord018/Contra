package org.game.contra.components;

import org.game.contra.entity.Bullet;

public class ShootingComponent {
    private TransformComponent transform;
    private final double bulletSpeed;

    public ShootingComponent(double bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    public void setTransform(TransformComponent transform) {
        this.transform = transform;
    }

    public Bullet shoot() {
        if (transform == null) return null;
        
        double bulletX = transform.isFacingRight() 
            ? transform.getX() + transform.getWidth() 
            : transform.getX();
            
        double bulletSpeedX = transform.isFacingRight() ? bulletSpeed : -bulletSpeed;
        return new Bullet(bulletX, transform.getY() + transform.getHeight()/2, bulletSpeedX, 0, true);
    }
}
