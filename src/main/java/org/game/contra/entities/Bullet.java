package org.game.contra.entities;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

public class Bullet {
    public enum BulletOwner {
        PLAYER,
        BOSS
    }

    private float damage;
    private boolean active = true;
    private float speed;
    private Body body;
    private World world;
    private Vec2 velocity;
    private boolean shouldBeDestroyed = false;
    private BulletOwner owner;

    public Bullet(Vec2 velocity, float damage, float speed, BulletOwner owner) {
        this.damage = damage;
        this.speed = speed;
        this.velocity = new Vec2(velocity.x, velocity.y);
        this.velocity.normalize();
        this.velocity.mulLocal(speed);
        this.owner = owner;
    }

    public void createBody(World world) {
        if (body != null) return;

        this.world = world;
        BodyDef bdef = new BodyDef();
        bdef.type = BodyType.DYNAMIC;
        bdef.position.set(new Vec2(-100, -100)); // Start off-screen
        bdef.bullet = true;
        bdef.fixedRotation = true;

        body = world.createBody(bdef);
        body.setUserData(this);

        CircleShape shape = new CircleShape();
        shape.m_radius = 0.1f;
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.isSensor = true;

        // Collision bits will need to be re-implemented if needed

        body.createFixture(fdef).setUserData(this);
        body.setLinearVelocity(velocity);
    }

    public void setPosition(Vec2 position) {
        if (body != null) {
            body.setTransform(position, 0);
        }
    }

    public void setDirection(Vec2 dir) {
        this.velocity = new Vec2(dir.x, dir.y);
        this.velocity.normalize();
        this.velocity.mulLocal(speed);
        if (body != null) {
            body.setLinearVelocity(this.velocity);
        }
    }

    public void update(float delta) {
        if (!active || body == null) return;

        Vec2 position = body.getPosition();
        // Off-screen check will be handled differently in JavaFX
        // For now, we'll just deactivate if it goes too far
        if (position.x < -10 || position.x > 100 || position.y < -10 || position.y > 100) {
            active = false;
        }
    }

    public boolean isActive() {
        return active;
    }

    public Vec2 getPosition() {
        return body != null ? body.getPosition() : new Vec2();
    }

    public void destroy() {
        active = false;
        if (body != null) {
            world.destroyBody(body);
            body = null;
        }
    }

    public void markForDestruction() {
        shouldBeDestroyed = true;
    }

    public boolean shouldBeDestroyed() {
        return shouldBeDestroyed;
    }

    public BulletOwner getOwner() {
        return owner;
    }

    public Body getBody() {
        return body;
    }
}