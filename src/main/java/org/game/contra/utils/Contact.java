package org.game.contra.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.game.contra.entities.Boss;
import org.game.contra.entities.Bullet;
import org.game.contra.entities.Player;

public class Contact implements ContactListener{


    @Override
    public void beginContact(com.badlogic.gdx.physics.box2d.Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Object dataA = fixtureA.getUserData();
        Object dataB = fixtureB.getUserData();

        // [Keep your existing debug logging...]

        // Handle foot sensor
        if ("foot".equals(dataA)) {
            handleFootContact(fixtureA, fixtureB, contact);
        } else if ("foot".equals(dataB)) {
            handleFootContact(fixtureB, fixtureA, contact);
        }

        // Mark bullets for destruction instead of destroying them immediately
        if (dataA instanceof Bullet) {
            if (dataB instanceof Boss) {
                Boss boss = (Boss) dataB;
                ((Bullet) dataA).markForDestruction();
                boss.takeDamage(25);
            } else if ("ground".equals(dataB)) {
                ((Bullet) dataA).markForDestruction();
            }
        } else if (dataB instanceof Bullet) {
            if (dataA instanceof Boss) {
                Boss boss = (Boss) dataA;
                ((Bullet) dataB).markForDestruction();
                boss.takeDamage(25);
            } else if ("ground".equals(dataA)) {
                ((Bullet) dataB).markForDestruction();
            }
        }
    }


    private void handleFootContact(Fixture footFixture, Fixture otherFixture, com.badlogic.gdx.physics.box2d.Contact contact) {
        // Get the player object from the foot fixture's body
        Player player = (Player) footFixture.getBody().getUserData();
        if (player != null) {
            player.onGround = true;
            System.out.println("Foot touched something!");
        }
        if (player.isFallingThrough()) {
            player.stopFallingThrough();
        }
    }
    @Override
    public void endContact(com.badlogic.gdx.physics.box2d.Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        // Check if either fixture is the foot sensor
        if ("foot".equals(fixtureA.getUserData())) {
            handleFootEndContact(fixtureA,fixtureB);
        } else if ("foot".equals(fixtureB.getUserData())) {
            handleFootEndContact(fixtureB,fixtureA);
        }
    }

    private void handleFootEndContact(Fixture footFixture, Fixture otherFixture) {

        if ("foot".equals(footFixture.getUserData())) {
            Player player = (Player) footFixture.getBody().getUserData();
            if (player != null) {
                player.onGround = false;
                System.out.println("Foot left the ground!");
            }
        }


        if ("foot".equals(otherFixture.getUserData())) {
            Player player = (Player) otherFixture.getBody().getUserData();
            if (player != null) {
                player.onGround = false;
                System.out.println("Foot left the ground!");
            }
        }
    }


    @Override
    public void preSolve(com.badlogic.gdx.physics.box2d.Contact contact, Manifold oldManifold) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Object dataA = fixtureA.getUserData();
        Object dataB = fixtureB.getUserData();

        boolean isAPlatform = "platform".equals(dataA);
        boolean isBPlatform = "platform".equals(dataB);

        if (isAPlatform || isBPlatform) {
            Fixture platformFixture = isAPlatform ? fixtureA : fixtureB;
            Fixture otherFixture = isAPlatform ? fixtureB : fixtureA;

            Object bodyData = otherFixture.getBody().getUserData();
            if (bodyData instanceof Player) {
                Player player = (Player) bodyData;

                // -----------  1: Drop Down -----------
                if (player.isFallingThrough()) {
                    contact.setEnabled(false);
                    return;
                }

                // -----------  2:(one-way platform) -----------
                float playerY = player.getBody().getPosition().y;
                float playerHeight = 1.0f;
                float platformY = platformFixture.getBody().getPosition().y;
                float platformHeight = 0.5f;

                float playerBottom = playerY - playerHeight / 2;
                float platformTop = platformY + platformHeight / 2;

                if (playerBottom < platformTop - 0.05f) {
                    contact.setEnabled(false);
                }
            }
        }
    }

    @Override
    public void postSolve(com.badlogic.gdx.physics.box2d.Contact box2dContact, ContactImpulse impulse) {

    }


}
