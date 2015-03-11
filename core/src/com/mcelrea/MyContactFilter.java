package com.mcelrea;

import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * Created by Tech on 3/11/2015.
 */
public class MyContactFilter implements ContactFilter{


    @Override
    public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {

        if(fixtureA.getUserData() instanceof Player &&
                fixtureB.getUserData() instanceof PlayerBullet) {
            return false;//ignore this collision
        }
        else if(fixtureB.getUserData() instanceof Player &&
                fixtureA.getUserData() instanceof PlayerBullet) {
            return false;//ignore this collision
        }

        return true;
    }
}
