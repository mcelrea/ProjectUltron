package com.mcelrea;

import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Tech on 3/6/2015.
 */
public class Bullet {

    Body body;

    public Bullet(World world, float size,
                  float x, float y,
                  float xvel, float yvel) {
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
}
