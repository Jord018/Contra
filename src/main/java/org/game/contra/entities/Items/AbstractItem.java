package org.game.contra.entities.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;
import org.game.contra.entities.Player;
import org.game.contra.RunGunGame;

public abstract class AbstractItem implements Items {
    protected Texture texture;
    protected Body body;
    protected boolean shouldBeDestroyed = false;

    @Override
    public void spawn(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);
        body.setUserData(this);

        CircleShape shape = new CircleShape();
        shape.setRadius(0.25f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = RunGunGame.ITEM_BIT;
        fixtureDef.filter.maskBits = RunGunGame.PLAYER_BIT;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        shape.dispose();
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public void markForDestruction() {
        shouldBeDestroyed = true;
    }

    public boolean shouldBeDestroyed() {
        return shouldBeDestroyed;
    }

    public Body getBody() {
        return body;
    }

    @Override
    public abstract void applyEffect(Player player);

    @Override
    public abstract String getName();
}
