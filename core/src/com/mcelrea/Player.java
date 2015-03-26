package com.mcelrea;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Tech on 3/2/2015.
 */
public class Player {

    Body body;
    float speed;
    float jumpForce;
    static final int LEFT = 1, RIGHT = 2;
    int dir = RIGHT;
    float shootSpeed = 20;
    Sprite rightSprite;
    Sprite leftSprite;
    Sound shootSound;
    Sound jumpSound;
    boolean resetPosition = false;

    //constructor
    public Player(World world, float speed, float jumpForce) {
        this.speed = speed;
        this.jumpForce = jumpForce;

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        //making the player's body
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0,-8.98f); //put the body at the origin of the world, (0,0)
        PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f, 0.7f); //create a player sized box
        fixtureDef.shape = box; //set the shape of the fixture to the box we created above
        fixtureDef.friction = 1f; //set friction
        fixtureDef.restitution = 0f; //set restitution, how much bounce a fixture will have
        fixtureDef.density = 670f; //670 is the average density for a human
        body = world.createBody(bodyDef); //put the body into the world
        body.createFixture(fixtureDef); //attach the box fixture to the body
        body.getFixtureList().first().setUserData(this); //name the box fixture as a Player (this) so we can reference it for collision detection
        body.setFixedRotation(true);
        box.dispose(); //erase the temporary box from memory to reduce memory leaks

        Texture t = new Texture(Gdx.files.internal("playerRight.png"));
        rightSprite = new Sprite(t);
        leftSprite = new Sprite(t);
        leftSprite.flip(true, false);

        shootSound = Gdx.audio.newSound(Gdx.files.internal("Laser_Shoot11.wav"));
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("Jump2.wav"));
    }

    public void moveRight() {
        if(body.getLinearVelocity().x < 10)
            body.applyForceToCenter(100000,0,true);
        dir = RIGHT;
    }

    public void moveLeft() {
        if(body.getLinearVelocity().x > -10)
            body.applyForceToCenter(-100000,0,true);
        dir = LEFT;
    }

    public void stopXMovement() {
        body.setLinearVelocity(0,body.getLinearVelocity().y);
        body.setAwake(true);
    }

    public void jump() {
        jumpSound.play();
        body.applyForceToCenter(0, 800000, true);
    }

    public void shootBullet(World world) {
        shootSound.play();
        if(dir == RIGHT) {
            new PlayerBullet(world, 0.2f,
                             body.getPosition().x,
                             body.getPosition().y,
                             shootSpeed,0);
        }
        else { //he must be facing left
            new PlayerBullet(world, 0.2f,
                             body.getPosition().x,
                             body.getPosition().y,
                             -shootSpeed,0);
        }
    }

    public void paint(SpriteBatch batch, OrthographicCamera camera) {
        //get the world coordinated (meters) of the player
        Vector3 worldCoords = new Vector3(body.getPosition().x,
                                          body.getPosition().y,
                                          0);

        //convert from meters in the world, to pixels on the screen
        Vector3 screenCoords = camera.project(worldCoords);

        rightSprite.setPosition(screenCoords.x- rightSprite.getWidth()/2,
                           screenCoords.y- rightSprite.getHeight()/2);

        if(dir == RIGHT) {
            batch.draw(rightSprite, rightSprite.getX(), rightSprite.getY());
        }
        else if (dir == LEFT) {
            batch.draw(leftSprite, rightSprite.getX(), rightSprite.getY());
        }
    }
}
