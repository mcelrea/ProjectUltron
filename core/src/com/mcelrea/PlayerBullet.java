package com.mcelrea;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Tech on 3/6/2015.
 */
public class PlayerBullet extends Bullet {

    public PlayerBullet(World world, float size,
                        float x, float y,
                        float xvel, float yvel) {
        super(world, size, x, y, xvel, yvel);
        super.body.getFixtureList().first().setUserData(this);
        Texture t = new Texture(Gdx.files.internal("player bullet.png"));
        sprite = new Sprite(t);
        GameplayScreen.playerBullets.add(this);
    }
}
