package org.game.contra.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.game.contra.RunGunGame;

import java.util.ArrayList;
import java.util.Iterator;

public class Boss {
    private Body body;
    private Vector2 position;

    private float width; // 1 meter wide
    private float height; // 1.8 meters tall (average human height)

    private ArrayList<Bullet> bullets;
    private ArrayList<Boss> bosses;
    private Texture bulletTexture;
    private float shootCooldown = 0f;
    private float shootTimer = 0;
    private float Healt = 100;
    private boolean Dead;
    private World world;


    private void createBody(World world) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position.x + width/2, position.y + height/2);  // Center the body
        bdef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bdef);
        body.setUserData(this);  // Set this Boss instance as the body's user data

        // Create the fixture
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);
        
        // Create fixture definition
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.filter.categoryBits = RunGunGame.ENEMY_BIT;
        fdef.filter.maskBits =   RunGunGame.BULLET_BIT;
        
        // Create and set user data for the fixture
        body.createFixture(fdef).setUserData(this);
        shape.dispose();
    }

    public Body getBody() {
        return body;
    }


    public void update(float delta) {
        if (body == null) return;
        // Update position
        position.set(body.getPosition().x - width/2, body.getPosition().y - height/2);

        // Update shoot timer
        if (shootTimer > 0) {
            float oldTimer = shootTimer;
            shootTimer -= delta;
            // Log when timer is about to expire
            if (oldTimer > 0.1f && shootTimer <= 0.1f) {
                Gdx.app.log("Boss", "Shoot timer expired, ready to shoot!");
            }
        }

        if (Dead) {
            destroy();
        }
    }

    private com.badlogic.gdx.graphics.glutils.ShapeRenderer shapeRenderer;

    public Boss(World world, float x, float y,float width,float height) {
        position = new Vector2(x, y);
        this.world = world;
        this.width = width;
        this.height = height;
        this.shapeRenderer = new com.badlogic.gdx.graphics.glutils.ShapeRenderer();
        // Try loading the bullet texture
        try {
            // Make sure the path is correct and the file exists
            String texturePath = "Bullet/Bullet.png";
            if (Gdx.files.internal(texturePath).exists()) {
                this.bulletTexture = new Texture(Gdx.files.internal(texturePath));
                Gdx.app.log("Player", "Successfully loaded bullet texture from: " + texturePath);
            } else {
                Gdx.app.error("Player", "Bullet texture not found at: " +
                        Gdx.files.getLocalStoragePath() + texturePath);
            }
        } catch (Exception e) {
            Gdx.app.error("Player", "Error loading bullet texture", e);
        }
        this.bullets = new ArrayList<>();
        createBody(world);
    }

    public void draw(SpriteBatch batch) {
        // Draw the boss as a red rectangle outline (border only)
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1); // Red color

        // Draw the boss outline at the correct position and size
        shapeRenderer.rect(
                position.x,      // x position in world units
                position.y,      // y position in world units
                width,           // width in world units
                height           // height in world units
        );
        shapeRenderer.end();
    }
    public void shoot() {
        if (shootTimer > 0) {
            Gdx.app.log("Boss", "Shoot on cooldown: " + shootTimer);
            return; // Still in cooldown
        }

        Gdx.app.log("Boss", "Shooting bullet!");
        float speed = MathUtils.random(5, 10);

        // Create bullet with zero velocity initially
        Bullet bullet = new Bullet(Vector2.Zero, 10, speed, bulletTexture);

        // Set bullet position at boss's position
        Vector2 bulletPos = new Vector2(position.x-0.15f, position.y + height/2);
        bullet.setPosition(bulletPos);

        // Set direction to left (negative x direction)
        bullet.setDirection(new Vector2(-10f, 37f));

        // Add bullet to the world
        if (world != null) {
            bullet.createBody(world);
            bullets.add(bullet);
            shootTimer = shootCooldown; // Reset cooldown timer

            Gdx.app.log("Boss", "Bullet created at: " + bulletPos +
                              ", Total bullets: " + bullets.size());
        } else {
            Gdx.app.error("Boss", "World is null, cannot create bullet!");
        }
    }

    public Vector2 getPosition() {
        return position;
    }



    public void dispose() {
        shapeRenderer.dispose();
    }


    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void takeDamage(int i) {
        Healt -= i;
        if (Healt <= 0) {
            Healt = 0;
            Dead = true;
        }
    }

    public void isDead(){
        Dead = Healt == 0;
    }
    public boolean isAlive() {
        return Healt > 0;
    }

    public void destroy() {
        if (body != null) {
            body.getWorld().destroyBody(body);
            body = null;
        }
    }
}
