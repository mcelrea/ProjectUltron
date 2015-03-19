package com.mcelrea;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Tech on 3/17/2015.
 */
public class PatrolEnemy extends Enemy{

    float patrolx1;
    float patrolx2;
    float patroly1;
    float patroly2;
    boolean xswitch;
    boolean yswitch;

    public PatrolEnemy(World world, float x, float y,
                       float px1, float px2,
                       float py1, float py2) {

        patrolx1 = px1;
        patrolx2 = px2;
        patroly1 = py1;
        patroly2 = py2;

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        //making the enemy's body
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x,y); //put the body at the origin of the world, (0,0)
        PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f, 0.5f); //create a player sized box
        fixtureDef.shape = box; //set the shape of the fixture to the box we created above
        fixtureDef.friction = 0f; //set friction
        fixtureDef.restitution = 0f; //set restitution, how much bounce a fixture will have
        fixtureDef.density = 670f; //670 is the average density for a human
        body = world.createBody(bodyDef); //put the body into the world
        body.createFixture(fixtureDef); //attach the box fixture to the body
        body.getFixtureList().first().setUserData(this); //name the box fixture as a Player (this) so we can reference it for collision detection
        body.setFixedRotation(true);
        if(px1 != 0) {
            body.setLinearVelocity(-5, 0);
            System.out.println("x patrol detected");
        }
        if(py1 != 0) {
            body.setLinearVelocity(0, 5);
            body.setGravityScale(0);
            System.out.println("y patrol detected");
        }
        box.dispose(); //erase the temporary box from memory to reduce memory leaks
    }


    @Override
    public void act(World world, float delta) {

        //if enemey should patrol in the x-direction
        if(patrolx1 != 0 && patrolx2 != 0) {
            //if he's moving to the left
            //and he's past the patrol point
            if (xswitch == false && body.getPosition().x < patrolx1) {
                body.setLinearVelocity(5, 0);//move him to the right now
                xswitch = !xswitch;
            }
            //if he's moving to the right
            //and he's past the patrol point
            if (xswitch == true && body.getPosition().x > patrolx2) {
                body.setLinearVelocity(-5, 0);//move him to the left now
                xswitch = !xswitch;
            }
        }
        if (patroly1 != 0 && patroly2 != 0) {
            //if he's moving up
            //and he's past the patrol point
            //System.out.println("move in the y direction");
            if(yswitch == false && body.getPosition().y > patroly2) {
                body.setLinearVelocity(0,-5);
                yswitch = !yswitch;
                System.out.println("switch down");
            }
            if(yswitch == true && body.getPosition().y < patroly1) {
                body.setLinearVelocity(0, 5);
                yswitch = !yswitch;
                System.out.println("switch up");
            }
        }
    }

    @Override
    public void die(World world) {

        float x, y;
        x = body.getPosition().x;
        y = body.getPosition().y;

        GameplayScreen.playerBullets.add(new EnemyBullet(world,
                .2f, x, y+1,
                0, 15));
        GameplayScreen.playerBullets.add(new EnemyBullet(world,
                .2f, x,y+1,
                15, 0));
        GameplayScreen.playerBullets.add(new EnemyBullet(world,
                .2f, x, y+1,
                -15, 0));
        GameplayScreen.playerBullets.add(new EnemyBullet(world,
                .2f, x, y+1,
                15, 15));
        GameplayScreen.playerBullets.add(new EnemyBullet(world,
                .2f, x, y+1,
                -15, 15));

        world.destroyBody(body);
    }

}










