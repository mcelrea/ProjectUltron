package com.mcelrea;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Tech on 3/24/2015.
 */
public class Platform {

    Sprite sprite;
    Body body;

    public Platform(String path) {
        Texture t = new Texture(Gdx.files.internal(path));
        sprite = new Sprite(t);
    }

    public void paint(SpriteBatch batch, OrthographicCamera camera) {

        //get the world coordinated (meters) of the player
        Vector3 worldCoords = new Vector3(body.getPosition().x,
                body.getPosition().y,
                0);

        //convert from meters in the world, to pixels on the screen
        Vector3 screenCoords = camera.project(worldCoords);

        batch.draw(sprite, screenCoords.x-sprite.getWidth()/2, screenCoords.y-sprite.getHeight()/2);
    }

}
