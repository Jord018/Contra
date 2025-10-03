package org.game.contra.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import org.game.contra.RunGunGame;

public class WorldCreator {
    public WorldCreator(World world) {
        // Create ground
        createPlatform(world, 0, 0, 1800, 32, true);
        
        // Create some platforms
        createPlatform(world, 200, 150, 200, 20, false);
        createPlatform(world, 500, 200, 200, 20, false);
        createPlatform(world, 100, 300, 200, 20, false);
    }
    
    private void createPlatform(World world, float x, float y, float width, float height, boolean isGround) {
        // Convert pixels to meters
        x /= RunGunGame.PPM;
        y /= RunGunGame.PPM;
        width /= RunGunGame.PPM;
        height /= RunGunGame.PPM;
        
        // Create body definition
        BodyDef bdef = new BodyDef();
        bdef.position.set(x + width/2, y + height/2);
        bdef.type = BodyDef.BodyType.StaticBody;
        
        // Create body
        Body body = world.createBody(bdef);
        
        // Create shape
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);
        
        // Create fixture
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.filter.categoryBits = isGround ? RunGunGame.GROUND_BIT : RunGunGame.OBJECT_BIT;
        body.createFixture(fdef).setUserData("platform");
        
        shape.dispose();
    }
}
