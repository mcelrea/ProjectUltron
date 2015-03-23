package com.mcelrea;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Tech on 3/23/2015.
 */
public class LongPlatform {

    Sprite sprite;

    public LongPlatform(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        //platform 1
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x,y);
        PolygonShape platform = new PolygonShape();
        platform.setAsBox(24,2);
        fixtureDef.shape = platform;
        fixtureDef.restitution = 0f;
        fixtureDef.density = 1000;
        fixtureDef.friction = 7f;
        world.createBody(bodyDef).createFixture(fixtureDef).setUserData("wall");
        platform.dispose();
    }
}
