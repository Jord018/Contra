package org.game.contra.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.game.contra.RunGunGame;

import java.util.ArrayList;
import java.util.Iterator;

public class Boss {
    private Body body;
    private Vector2 position;

    private float width = 1.0f; // 1 meter wide
    private float height = 1.0f; // 1.8 meters tall (average human height)

    private ArrayList<Bullet> bullets;
    private Texture bulletTexture;
    private float shootCooldown = 0.25f;
    private float shootTimer = 0;
    private float Healt = 10;
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
        // Update position
        position.set(body.getPosition().x - width/2, body.getPosition().y - height/2);


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


    }

    private com.badlogic.gdx.graphics.glutils.ShapeRenderer shapeRenderer;

    public Boss(World world, int x, int y) {
        position = new Vector2(x, y);
        this.world = world;
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
        // Draw the boss as a red rectangle
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 0, 1); // Red color

        // Draw the boss at the correct position and size
        shapeRenderer.rect(
                position.x,      // x position in world units
                position.y,      // y position in world units
                width,           // width in world units
                height           // height in world units
        );
        shapeRenderer.end();
    }
    public void shoot(Viewport viewport) {
        if (shootTimer > 0) return; // ยัง cooldown

        Vector2 mouseWorld = new Vector2();
        viewport.unproject(mouseWorld.set(Gdx.input.getX(), Gdx.input.getY()));

        // ทิศทางจาก player → cursor
        Vector2 direction = new Vector2(mouseWorld.x - position.x, mouseWorld.y - (position.y + height/2));

        // สร้าง bullet
        Bullet bullet = new Bullet(
                new Vector2(position.x + width/2, position.y + height/2),
                10,
                5,
                // speed
                bulletTexture);
        bullet.setPosition(new Vector2(position.x + width/2, position.y + height/2));
        bullet.setDirection(direction); // ยิงไป cursor

        bullets.add(bullet);
        shootTimer = shootCooldown; // reset timer

        System.out.println("Shooting! Bullet at " + bullet.getPosition() + " toward " + direction);
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

    public float getHealt() {
        return Healt;
    }

    public void takeDamage(int i) {
        Healt -= i;
    }
    public void isDead(){
        Dead = Healt == 0;
    }
}
