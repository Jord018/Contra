package org.game.contra.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.game.contra.RunGunGame;

public class Bullet {
    public enum BulletOwner {
        PLAYER,
        BOSS
    }
    
    private float Damage;
    private boolean Active = true;
    private float Speed;
    private Texture texture;
    private boolean hasTexture = false;
    private Vector2 position;
    private Body body;
    private World world;
    private Vector2 velocity; // ใช้เก็บ direction * speed
    private float rotation;   // มุมหมุน (degree)
    private boolean shouldBeDestroyed = false;
    private ShapeRenderer shapeRenderer;
    private BulletOwner owner;

    public Bullet(Vector2 velocity,float damage, float speed, Texture texture, BulletOwner owner) {
        this.Damage = damage;
        this.Speed = speed;
        this.texture = texture;
        this.hasTexture = texture != null;
        this.velocity = velocity.cpy().nor().scl(speed);
        this.position = new Vector2();
        this.rotation = 0f;
        this.shapeRenderer = new ShapeRenderer();
        this.owner = owner;
    }

    public Body getBody() {
        return body;
    }

    public void createBody(World world) {
        if (body != null) return; // ถ้ามีแล้ว ไม่สร้างซ้ำ

        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(position);
        bdef.bullet = true;
        bdef.fixedRotation = true;

        body = world.createBody(bdef);
        body.setUserData(this);

        CircleShape shape = new CircleShape();
        shape.setRadius(0.1f);
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.isSensor = true;
        
        // Set collision bits based on bullet owner
        if (owner == BulletOwner.PLAYER) {
            fdef.filter.categoryBits = RunGunGame.BULLET_BIT;
            fdef.filter.maskBits = RunGunGame.ENEMY_BIT | RunGunGame.GROUND_BIT;
        } else if (owner == BulletOwner.BOSS) {
            fdef.filter.categoryBits = RunGunGame.ENEMY_BULLET_BIT;
            fdef.filter.maskBits = RunGunGame.PLAYER_BIT | RunGunGame.GROUND_BIT;
        }
        
        body.createFixture(fdef).setUserData(this);
        shape.dispose();

        body.setLinearVelocity(velocity.cpy());
    }


    // Set initial position
    public void setPosition(Vector2 bulletPos) {
        this.position.set(bulletPos);
    }

    // Set direction vector (ยิงไป cursor)
    public void setDirection(Vector2 dir) {
        this.velocity = dir.cpy().nor().scl(Speed); // normalize + scale speed
        this.rotation = dir.angleDeg(); // เก็บมุมหมุน
    }

    public void update(float delta) {
        if (!Active) return;
        if (body == null) return;
        position.set(body.getPosition());
        // Off-screen check
        if (position.x < 0 || position.x > RunGunGame.V_WIDTH ||
                position.y < 0 || position.y > RunGunGame.V_HEIGHT) {
            Active = false;
        }
    }

    public void render(SpriteBatch batch) {
        if (!Active) return;

        float width = 0.3f;
        float height = 0.3f;
        Vector2 pos = body.getPosition();
        if (hasTexture) {
            // วาด sprite พร้อมหมุน
            batch.draw(texture,
                    pos.x - width/2, pos.y - height/2,
                    width/2, height/2,
                    width, height,
                    1f, 1f,
                    rotation,
                    0, 0, texture.getWidth(), texture.getHeight(),
                    false, false);
        } else {
            // Fallback: draw as a yellow circle when no texture is available
            batch.end();
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1, 1, 0, 1); // Yellow color
            shapeRenderer.circle(pos.x, pos.y, 0.1f); // Draw circle with radius 0.1f
            shapeRenderer.end();
            batch.begin();
        }
    }

    public boolean isActive() { return Active; }
    public Vector2 getPosition() { return position; }
    public void destroy() {
        Active = false;
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
        if (body != null) {
            body.getWorld().destroyBody(body);
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
    
    public boolean isFromPlayer() {
        return owner == BulletOwner.PLAYER;
    }
    
    public boolean isFromBoss() {
        return owner == BulletOwner.BOSS;
    }

}
