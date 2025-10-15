package org.game.contra.utils;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class WorldCreator {
    public WorldCreator(World world, String screenName) {
        switch (screenName) {
            case "Screen1":
                createPlatform(world, 2.8f, 0.25f, 9.9f, 0.5f, true);
                createPlatform(world, 4.2f, 2.25f, 3.8f, 0.5f, false);
                createPlatform(world, 4.2f, 3.25f, 3.8f, 0.5f, false);
                break;
            case "Screen2":
                createPlatform(world, 3f, 1.25f, 12.85f, 0.5f, true);
                break;
            case "Screen3":
                createPlatform(world, 2.8f, 0.25f, 9.9f, 0.5f, true);
                createPlatform(world, 4.2f, 2.25f, 3.8f, 0.5f, false);
                createPlatform(world, 4.2f, 3.25f, 3.8f, 0.5f, false);
                break;
            default:
                break;
        }
    }

    private void createPlatform(World world, float x, float y, float width, float height, boolean isGround) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x + width / 2, y + height / 2);
        bdef.type = BodyType.STATIC;

        Body body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.friction = 0.6f;

        if (isGround) {
            // Collision filtering needs to be re-implemented
            body.createFixture(fdef).setUserData("ground");
        } else {
            body.createFixture(fdef).setUserData("platform");
        }
    }
}