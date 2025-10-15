package org.game.contra.entities.Items;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.*;
import org.game.contra.entities.Player;

public abstract class AbstractItem implements Items {
    protected Body body;
    protected boolean shouldBeDestroyed = false;

    @Override
    public void spawn(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);
        body.setUserData(this);

        CircleShape shape = new CircleShape();
        shape.m_radius = 0.25f;

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        // Collision filtering needs to be re-implemented
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
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