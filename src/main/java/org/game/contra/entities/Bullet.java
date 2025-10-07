package org.game.contra.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.game.contra.RunGunGame;

public class Bullet {
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

    public Bullet(Vector2 velocity,float damage, float speed, Texture texture) {
        this.Damage = damage;
        this.Speed = speed;
        this.texture = texture;
        this.hasTexture = texture != null;
        this.velocity = velocity.cpy().nor().scl(speed);
        this.position = new Vector2();
        this.rotation = 0f;
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
        fdef.filter.categoryBits = RunGunGame.BULLET_BIT;
        fdef.filter.maskBits = RunGunGame.ENEMY_BIT | RunGunGame.GROUND_BIT;
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
        }
    }

    public boolean isActive() { return Active; }
    public Vector2 getPosition() { return position; }
    public void destroy() {
        Active = false;
        if (body != null) {
            body.getWorld().destroyBody(body);
            body = null;
        }
    }

}
