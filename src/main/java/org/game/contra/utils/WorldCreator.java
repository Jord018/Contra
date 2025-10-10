package org.game.contra.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import org.game.contra.RunGunGame;

public class WorldCreator {
    public WorldCreator(World world, String screenName) {
        switch (screenName) {
            case "Screen1":
            // Create ground
            createPlatform(world, 2.8f, 0.25f, 9.9f, 0.5f, true);
            createPlatform(world, 4.2f, 2.25f, 3.8f, 0.5f, false);
            createPlatform(world, 4.2f, 3.25f, 3.8f, 0.5f, false);
        }
    }

    private void createPlatform(World world, float x, float y, float width, float height, boolean isGround) {

        // Body definition
        BodyDef bdef = new BodyDef();
        bdef.position.set(x + width / 2, y + height / 2);
        bdef.type = BodyDef.BodyType.StaticBody;

        Body body = world.createBody(bdef);

        // Shape
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.friction = 0.6f;

        if (isGround) {
            // ✅ Ground fixture
            fdef.filter.categoryBits = RunGunGame.GROUND_BIT;
            fdef.filter.maskBits = RunGunGame.PLAYER_BIT;
            Fixture groundFixture = body.createFixture(fdef);
            groundFixture.setUserData("ground");
        } else {
            // ✅ Platform fixture
            fdef.filter.categoryBits = RunGunGame.PLATFORM_BIT;
            fdef.filter.maskBits = RunGunGame.PLAYER_BIT;
            Fixture platformFixture = body.createFixture(fdef);
            platformFixture.setUserData("platform");
        }

        shape.dispose();
    }

}
