package com.mcelrea;

import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * Created by Tech on 3/11/2015.
 */
public class MyContactFilter implements ContactFilter{


    @Override
    public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {

        /*
         * Player and PlayerBullet Collision
         */
        if(fixtureA.getUserData() instanceof Player &&
                fixtureB.getUserData() instanceof PlayerBullet) {
            return false;//ignore this collision
        }
        else if(fixtureB.getUserData() instanceof Player &&
                fixtureA.getUserData() instanceof PlayerBullet) {
            return false;//ignore this collision
        }

        /*
         * Bullet and Bullet Collision
         */
        if(fixtureA.getUserData() instanceof Bullet &&
                fixtureB.getUserData() instanceof Bullet) {
            return false;//ignore collision
        }

        /*
         * Bullet and Wall Collision
         */
        if(fixtureA.getUserData() instanceof Bullet &&
                fixtureB.getUserData().equals("wall")) {
            ((Bullet)(fixtureA.getUserData())).alive = false;
        }
        else if(fixtureA.getUserData().equals("wall") &&
                fixtureB.getUserData() instanceof Bullet) {
            ((Bullet)(fixtureB.getUserData())).alive = false;
        }

        /*
         * Player bullets and Enemy collision
         */
        if(fixtureA.getUserData() instanceof PlayerBullet &&
                fixtureB.getUserData() instanceof Enemy) {
            ((PlayerBullet)(fixtureA.getUserData())).alive = false;
            ((Enemy)(fixtureB.getUserData())).alive = false;
        }
        else if(fixtureB.getUserData() instanceof PlayerBullet &&
                fixtureA.getUserData() instanceof Enemy) {
            ((PlayerBullet)(fixtureB.getUserData())).alive = false;
            ((Enemy)(fixtureA.getUserData())).alive = false;
        }

        //must be the last line
        return true;
    }
}
