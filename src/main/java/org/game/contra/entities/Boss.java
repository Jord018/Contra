package org.game.contra.entities;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.game.contra.entities.Shooting.ShootingStrategy;

import java.util.ArrayList;

public class Boss {
    private Body body;
    private Vec2 position;
    private float width;
    private float height;
    private ArrayList<Bullet> bullets;
    private float shootCooldown = 0f;
    private float shootTimer = 0;
    private float health = 100;
    private boolean dead = false;
    private World world;
    private ShootingStrategy shootingStrategy;
    private boolean needsDestruction = false;

    public Boss(World world, float x, float y, float width, float height, ShootingStrategy strategy) {
        this.world = world;
        this.position = new Vec2(x, y);
        this.width = width;
        this.height = height;
        this.shootingStrategy = strategy;
        this.bullets = new ArrayList<>();
        createBody(world);
    }

    private void createBody(World world) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position.x + width / 2, position.y + height / 2);
        bdef.type = BodyType.STATIC;
        body = world.createBody(bdef);
        body.setUserData(this);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        // Collision bits will need to be re-implemented if needed
        body.createFixture(fdef).setUserData(this);
    }

    public void update(float delta) {
        if (body == null) return;
        position.set(body.getPosition().x - width / 2, body.getPosition().y - height / 2);

        if (shootTimer > 0) {
            shootTimer -= delta;
        }

        if (dead) {
            needsDestruction = true;
        }
    }

    public void shoot() {
        if (shootTimer > 0) return;
        if (shootingStrategy != null && world != null) {
            shootingStrategy.shoot(this, world, bullets);
            shootTimer = shootCooldown;
        }
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            health = 0;
            dead = true;
            needsDestruction = true;
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void destroy() {
        if (body != null) {
            world.destroyBody(body);
            body = null;
        }
        needsDestruction = false;
    }

    public boolean needsDestruction() {
        return needsDestruction;
    }

    public Body getBody() {
        return body;
    }

    public Vec2 getPosition() {
        return position;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public float getHeight() {
        return height;
    }
}