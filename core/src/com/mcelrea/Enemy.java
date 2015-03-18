package com.mcelrea;

import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Tech on 3/17/2015.
 */
public abstract class Enemy {

    boolean alive;

    public Enemy() {
        alive = true;
    }

    public abstract void act(World world, float delta);
    public abstract void die(World world);
}
