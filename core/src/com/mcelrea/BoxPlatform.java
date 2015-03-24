package com.mcelrea;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Tech on 3/24/2015.
 */
public class BoxPlatform extends Platform{


    public BoxPlatform(World world, OrthographicCamera camera, float x, float y) {

        super("boxPlatform.png");

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        //platform 1
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x,y);
        PolygonShape platform = new PolygonShape();
        platform.setAsBox(2,2);
        fixtureDef.shape = platform;
        fixtureDef.restitution = 0f;
        fixtureDef.density = 1000;
        fixtureDef.friction = 7f;
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData("wall");
        platform.dispose();


        //get the world coordinated (meters) of the player
        Vector3 worldCoords = new Vector3(body.getPosition().x,
                body.getPosition().y,
                0);

        //convert from meters in the world, to pixels on the screen
        Vector3 screenCoords = camera.project(worldCoords);

        sprite.setPosition(screenCoords.x- sprite.getWidth()/2,
                screenCoords.y- sprite.getHeight()/2);
    }

}
