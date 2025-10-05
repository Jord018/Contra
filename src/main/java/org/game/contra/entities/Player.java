package org.game.contra.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.game.contra.RunGunGame;

import java.util.ArrayList;
import java.util.Iterator;

public class Player {
    private Body body;
    private Vector2 position;
    private boolean facingRight = true;
    private boolean isJumping = false;
    private float width = 1.0f; // 1 meter wide
    private float height = 1.0f; // 1.8 meters tall (average human height)
    private float jumpCooldown = 0.1f; // Small cooldown to prevent double jumps
    private float jumpTimer = 0;
    private ArrayList<Bullet> bullets;
    private Texture bulletTexture;
    private float shootCooldown = 0.5f;
    private float shootTimer = 0;
    public boolean onGround;
    private boolean isFallingThrough = false;


    private void createBody(World world) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);
        body.setUserData(this);  // Set this Player instance as the body's user data

        // Main fixture
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.filter.categoryBits = RunGunGame.PLAYER_BIT;
        fdef.filter.maskBits = RunGunGame.GROUND_BIT | RunGunGame.OBJECT_BIT| RunGunGame.PLATFORM_BIT;
        body.createFixture(fdef).setUserData("body");

        // Foot sensor
        shape.setAsBox(width/2 - 0.1f, 0.1f, new Vector2(0, -height/2), 0);
        fdef.shape = shape;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("foot");

        shape.dispose();
    }

    public Body getBody() {
        return body;
    }
    public Vector2 getPosfoot() {
        return body.getFixtureList().get(0).getBody().getPosition();
    }

    public void update(float delta) {
        // Update position
        position.set(body.getPosition().x - width/2, body.getPosition().y - height/2);

        // Update jump timer
        if (jumpTimer > 0) {
            jumpTimer -= delta;
        }
        // Update shoot timer
        if (shootTimer > 0) {
            shootTimer -= delta;
        }

        // Update bullets
        Iterator<Bullet> iter = bullets.iterator();
        while (iter.hasNext()) {
            Bullet bullet = iter.next();
            bullet.update(delta);
            if (!bullet.isActive()) {
                iter.remove();
            }
        }

        // Check if player is on the ground (velocity is nearly zero)
        boolean wasJumping = isJumping;
        isJumping = Math.abs(body.getLinearVelocity().y) > 0.1f;

        // If we were in the air and now we're not, we've landed
        if (wasJumping && !isJumping) {
            System.out.println("Player landed (velocity-based detection)");
            jumpTimer = jumpCooldown; // Small cooldown after landing
        }
    }

    private com.badlogic.gdx.graphics.glutils.ShapeRenderer shapeRenderer;

    public Player(World world, int x, int y) {
        position = new Vector2(x, y);
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
        batch.end();
        // Draw the player as a red rectangle
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 0, 1); // Red color

        // Draw the player at the correct position and size
        shapeRenderer.rect(
                position.x * 32,      // x position in pixels
                position.y * 32,      // y position in pixels
                width * 32,           // width in pixels
                height * 32           // height in pixels
        );
        shapeRenderer.end();
        batch.begin();
    }

    public void moveLeft() {

        // Apply a force to the left
        body.applyForceToCenter(-150f, 0, true);
        facingRight = false;
    }

    public void moveRight() {
        // Apply a force to the right
        body.applyForceToCenter(150f, 0, true);
        facingRight = true;
    }

    public void jump() {
        // Only allow jumping if not moving up/down and cooldown is over
        if (jumpTimer <= 0 && Math.abs(body.getLinearVelocity().y) < 0.1f && onGround) {
            System.out.println("Jumping! Velocity: " + body.getLinearVelocity().y);
            body.applyLinearImpulse(new Vector2(0, 9f), body.getWorldCenter(), true);
            isJumping = true;
            jumpTimer = jumpCooldown;
            onGround = false;
        } else {
            System.out.println("Can't jump now. Velocity: " + body.getLinearVelocity().y +
                    ", Cooldown: " + jumpTimer);
            onGround = true;
        }
    }
    public void moveDown() {
        if (isFallingThrough) return;
        isFallingThrough = true;
        onGround = false;

        body.applyLinearImpulse(new Vector2(0, -0.2f), body.getWorldCenter(), true);

        System.out.println("Falling through platform...");
    }
    public void shoot() {
        if (shootTimer <= 0) {
            System.out.println("Shooting! Bullet texture is " +
                    (bulletTexture != null ? "loaded" : "MISSING"));
            Vector2 bulletPos = new Vector2(
                    position.x + (facingRight ? width : 0),
                    position.y + height/2
            );
            System.out.println("Creating bullet at: " + bulletPos);
            Bullet bullet = new Bullet(25, 30, bulletTexture);
            bullet.setPosition(bulletPos);
            bullet.setDirection(facingRight);
            bullets.add(bullet);
            shootTimer = shootCooldown;
        }
    }
    public Vector2 getPosition() {
        return position;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }

    public void dispose() {
        shapeRenderer.dispose();
    }

    // Helper method for debugging
    public boolean isFallingThrough() {
        return isFallingThrough;
    }

    public void stopFallingThrough() {
        if (isFallingThrough) {
            isFallingThrough = false;
            System.out.println("Fall-through stopped, normal collision restored.");
        }
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
}
