package org.game.contra.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import org.game.contra.RunGunGame;

public class Bullet {
    private float Damage;
    private boolean Active = true;
    private float Speed;
    private Texture texture;
    private boolean hasTexture = false;
    private Vector2 position;
    private Vector2 velocity;

    public Bullet(float damage, float speed, Texture texture) {
        this.Damage = damage;
        this.Speed = speed;
        this.texture = texture;
        this.hasTexture = texture != null;
        this.velocity = new Vector2();
        this.position = new Vector2();
    }

    public void update(float delta) {
        if (!Active) return;
        position.x += velocity.x * delta; // move horizontally

        // off-screen check (use world width)
        if (position.x < 0 || position.x > RunGunGame.V_WIDTH) {
            Active = false;
        }
    }

    public void render(SpriteBatch batch) {
        if (!Active) return;

        float width = 0.3f;
        float height = 0.3f;

        if (hasTexture) {
            batch.draw(texture, position.x, position.y, width, height);
        }
        else{
            // ShapeRenderer alternative
            batch.end();
            ShapeRenderer shape = new ShapeRenderer();
            shape.setProjectionMatrix(batch.getProjectionMatrix());
            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(1, 1, 0, 1); // yellow
            shape.rect(position.x, position.y, width, height);
            shape.end();
            batch.begin();
        }
    }

    public boolean isActive() { return Active; }
    public void setPosition(Vector2 bulletPos) { this.position.set(bulletPos); }
    public void setDirection(boolean right) { velocity.x = right ? Speed : -Speed; }
    public Vector2 getPosition() { return position; }
}
