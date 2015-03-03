package com.mcelrea;

import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Tech on 3/2/2015.
 */
public class Player {

    Body body;
    float speed;
    float jumpForce;

    //constructor
    public Player(World world, float speed, float jumpForce) {
        this.speed = speed;
        this.jumpForce = jumpForce;

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        //making the player's body
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0,0); //put the body at the origin of the world, (0,0)
        PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f, 1); //create a player sized box
        fixtureDef.shape = box; //set the shape of the fixture to the box we created above
        fixtureDef.friction = 0.3f; //set friction
        fixtureDef.restitution = 0f; //set restitution, how much bounce a fixture will have
        fixtureDef.density = 670f; //670 is the average density for a human
        body = world.createBody(bodyDef); //put the body into the world
        body.createFixture(fixtureDef); //attach the box fixture to the body
        body.getFixtureList().first().setUserData(this); //name the box fixture as a Player (this) so we can reference it for collision detection
        box.dispose(); //erase the temporary box from memory to reduce memory leaks

    }

}
