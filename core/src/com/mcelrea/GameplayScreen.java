package com.mcelrea;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Tech on 3/2/2015.
 */
public class GameplayScreen implements Screen{

    World world;                        //the entire world
    OrthographicCamera camera;          //show a small portion of the world
    Box2DDebugRenderer debugRenderer;   //displays the bodies and fixtures
    SpriteBatch batch;                  //used to draw Textures and Sprites

    @Override
    public void show() {
        //create the world with Earth gravity
        world = new World(new Vector2(0, -9.81f), true);

        camera = new OrthographicCamera();
        debugRenderer = new Box2DDebugRenderer();
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(1/60f, 8, 3);
        camera.update();

        debugRenderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width/25f;
        camera.viewportHeight = height/25f;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
