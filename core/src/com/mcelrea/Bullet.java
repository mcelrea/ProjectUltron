package com.mcelrea;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Tech on 3/6/2015.
 */
public class Bullet {

    Body body;
    boolean alive;
    Sprite sprite;

    public Bullet(World world, float size,
                  float x, float y,
                  float xvel, float yvel) {
        alive = true;
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        CircleShape b = new CircleShape();
        b.setRadius(size);
        fixtureDef.shape = b;
        fixtureDef.restitution = 0f;
        fixtureDef.friction = .4f;
        fixtureDef.density = 500;
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setLinearVelocity(xvel, yvel);
        body.setGravityScale(0);
        b.dispose();
    }

    public void paint(SpriteBatch batch, OrthographicCamera camera) {
        //get the world coordinated (meters) of the player
        Vector3 worldCoords = new Vector3(body.getPosition().x,
                body.getPosition().y,
                0);

        //convert from meters in the world, to pixels on the screen
        Vector3 screenCoords = camera.project(worldCoords);

        sprite.setPosition(screenCoords.x- sprite.getWidth()/2,
                screenCoords.y- sprite.getHeight()/2);

        batch.draw(sprite, sprite.getX(), sprite.getY());
    }
}
