package com.mcelrea;

import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Tech on 3/18/2015.
 */
public class EnemyBullet extends Bullet {

    public EnemyBullet(World world, float size,
                        float x, float y,
                        float xvel, float yvel) {
        super(world, size, x, y, xvel, yvel);
        super.body.getFixtureList().first().setUserData(this);
    }
}

