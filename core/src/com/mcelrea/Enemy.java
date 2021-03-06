package com.mcelrea;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Tech on 3/17/2015.
 */
public abstract class Enemy {

    boolean alive;
    Body body;

    public Enemy() {
        alive = true;
    }

    public abstract void act(World world, float delta);
    public abstract void die(World world);
    public abstract void paint(SpriteBatch batch, OrthographicCamera camera);
}
