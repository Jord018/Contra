package org.game.contra.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.game.contra.RunGunGame;

public class Player {
    private Body body;
    private Vector2 position;
    private boolean facingRight = true;
    private boolean isJumping = false;
    private float width = 1.0f; // 1 meter wide
    private float height = 1.0f; // 1.8 meters tall (average human height)
    private float jumpCooldown = 0.1f; // Small cooldown to prevent double jumps
    private float jumpTimer = 0;
    // No texture needed for now, we'll use a colored rectangle

    public Player(World world, float x, float y) {
        position = new Vector2(x, y);
        createBody(world);
        
        // No texture loading needed for now, we'll use a colored rectangle
    }

    private void createBody(World world) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        // Main fixture
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.filter.categoryBits = RunGunGame.PLAYER_BIT;
        fdef.filter.maskBits = RunGunGame.GROUND_BIT | RunGunGame.OBJECT_BIT;
        body.createFixture(fdef).setUserData(this);

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
    
    public void update(float delta) {
        // Update position
        position.set(body.getPosition().x - width/2, body.getPosition().y - height/2);
        
        // Update jump timer
        if (jumpTimer > 0) {
            jumpTimer -= delta;
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

    private com.badlogic.gdx.graphics.glutils.ShapeRenderer shapeRenderer = new com.badlogic.gdx.graphics.glutils.ShapeRenderer();
    
    public void draw(SpriteBatch batch) {
        // End the SpriteBatch and use ShapeRenderer to draw a rectangle
        batch.end();
        
        // Draw a red rectangle as a placeholder for the player
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 0, 1); // Red color

        shapeRenderer.rect(position.x, position.y, width, height);
        shapeRenderer.end();
        
        // Restart the SpriteBatch for other rendering
        batch.begin();
    }

    public void moveLeft() {
        // Apply a force to the left
        body.applyForceToCenter(-50f, 0, true);
        facingRight = false;
    }

    public void moveRight() {
        // Apply a force to the right
        body.applyForceToCenter(50f, 0, true);
        facingRight = true;
    }

    public void jump() {
        // Only allow jumping if not moving up/down and cooldown is over
        if (jumpTimer <= 0 && Math.abs(body.getLinearVelocity().y) < 0.1f) {
            System.out.println("Jumping! Velocity: " + body.getLinearVelocity().y);
            body.applyLinearImpulse(new Vector2(0, 10f), body.getWorldCenter(), true);
            isJumping = true;
            jumpTimer = jumpCooldown;
        } else {
            System.out.println("Can't jump now. Velocity: " + body.getLinearVelocity().y + 
                             ", Cooldown: " + jumpTimer);
        }
    }

    public void shoot() {
        // TODO: Implement shooting
    }

    public Vector2 getPosition() {
        return position;
    }
    
    public float getWidth() {
        return width;
    }
    
    public float getHeight() {
        return height;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }

    public void dispose() {
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }
    public boolean isGrounded() {
        return !isJumping;
    }
    
    // Helper method for debugging
    public String getJumpState() {
        return isJumping ? "JUMPING" : "ON_GROUND";
    }

}
