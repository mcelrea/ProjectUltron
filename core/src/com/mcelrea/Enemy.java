package com.mcelrea;

import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Tech on 3/17/2015.
 */
public interface Enemy {

    public void act(World world, float delta);
}
