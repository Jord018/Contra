package org.game.contra.entities;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.game.contra.entities.Weapons.WeaponType;

import java.util.ArrayList;

public class Player {
    private Body body;
    private Vec2 position;
    private boolean facingRight = true;
    private boolean isJumping = false;
    private float width = 1.0f;
    private float height = 1.0f;
    private float jumpCooldown = 0.1f;
    private float jumpTimer = 0;
    private ArrayList<Bullet> bullets;
    private float shootCooldown = 0.25f;
    private float shootTimer = 0;
    public boolean onGround;
    private boolean isFallingThrough = false;
    public static WeaponType currentWeapon = WeaponType.NORMAL;
    private boolean alive = true;
    private int health = 3;
    private World world;
    private boolean needsRespawn = false;

    public Player(World world, float x, float y) {
        this.world = world;
        position = new Vec2(x, y);
        this.bullets = new ArrayList<>();
        createBody(world);
    }

    private void createBody(World world) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyType.DYNAMIC;
        body = world.createBody(bdef);
        body.setUserData(this);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        // Collision bits will need to be re-implemented if needed
        body.createFixture(fdef).setUserData("body");

        shape.setAsBox(width / 2 - 0.1f, 0.1f, new Vec2(0, -height / 2), 0);
        fdef.shape = shape;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("foot");
    }

    public void update(float delta) {
        if (body == null) return;
        position.set(body.getPosition());

        if (jumpTimer > 0) {
            jumpTimer -= delta;
        }

        if (shootTimer > 0) {
            shootTimer -= delta;
        }

        for (Bullet bullet : bullets) {
            bullet.update(delta);
        }

        boolean wasJumping = isJumping;
        isJumping = Math.abs(body.getLinearVelocity().y) > 0.1f;

        if (wasJumping && !isJumping) {
            jumpTimer = jumpCooldown;
        }
    }

    public void moveLeft() {
        body.applyForceToCenter(new Vec2(-150f, 0));
        facingRight = false;
    }

    public void moveRight() {
        body.applyForceToCenter(new Vec2(150f, 0));
        facingRight = true;
    }

    public void jump() {
        if (jumpTimer <= 0 && Math.abs(body.getLinearVelocity().y) < 0.1f && onGround) {
            body.applyLinearImpulse(new Vec2(0, 9f), body.getWorldCenter());
            isJumping = true;
            jumpTimer = jumpCooldown;
            onGround = false;
        } else {
            onGround = true;
        }
    }

    public void moveDown() {
        if (isFallingThrough) return;
        isFallingThrough = true;
        onGround = false;
        body.applyLinearImpulse(new Vec2(0, -0.2f), body.getWorldCenter());
    }

    public void shoot(Object viewport) { // Viewport is an Object for now
        if (shootTimer > 0) return;

        Vec2 direction = new Vec2(facingRight ? 1 : -1, 0);
        Bullet bullet = new Bullet(direction, 10, 30, Bullet.BulletOwner.PLAYER);
        bullet.setPosition(new Vec2(position.x + width / 2, position.y + height / 2));
        bullet.createBody(world);
        bullets.add(bullet);

        shootTimer = shootCooldown;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            alive = false;
            needsRespawn = true;
        }
    }

    public void respawn() {
        position.set(5, 2);
        alive = true;
        isJumping = false;
        onGround = false;
        if (body != null) {
            world.destroyBody(body);
        }
        createBody(world);
        needsRespawn = false;
    }

    public boolean needsRespawn() {
        return needsRespawn;
    }

    public Body getBody() {
        return body;
    }

    public boolean isAlive() {
        return alive;
    }

    public Vec2 getPosition() {
        return position;
    }

    public void setWeaponType(WeaponType type) {
        currentWeapon = type;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
}