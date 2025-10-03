package org.game.contra.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import org.game.contra.RunGunGame;

public class WorldCreator {
    public WorldCreator(World world) {
        // Create ground
        createPlatform(world, 4.2f, 1f, 15, 0.5f, true);
        createPlatform(world, 6.2f, 5f, 7, 0.5f, false);
    }

    private void createPlatform(World world, float x, float y, float width, float height, boolean isGround) {

        // Create body definition
        BodyDef bdef = new BodyDef();
        bdef.position.set(x + width/2, y + height/2);  // Center position
        bdef.type = BodyDef.BodyType.StaticBody;

        // Create body
        Body body = world.createBody(bdef);

        // Create shape - using half-width and half-height
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);

        // Create fixture
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.friction = 0.6f;

        // Set collision bits if needed
        if (isGround) {
            fdef.filter.categoryBits = 0x0001;
            fdef.filter.maskBits = -1;
        }

        body.createFixture(fdef);
        shape.dispose();
    }
}
