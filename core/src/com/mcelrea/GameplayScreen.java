package com.mcelrea;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Tech on 3/2/2015.
 */
public class GameplayScreen implements Screen{

    World world;                        //the entire world
    OrthographicCamera camera;          //show a small portion of the world
    Box2DDebugRenderer debugRenderer;   //displays the bodies and fixtures
    SpriteBatch batch;                  //used to draw Textures and Sprites
    Player player;                      //the actor that is controlled with user input
    BitmapFont font;
    boolean debugOn = true;

    @Override
    public void show() {
        //create the world with Earth gravity
        world = new World(new Vector2(0, -9.81f), true);

        camera = new OrthographicCamera();
        debugRenderer = new Box2DDebugRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        player = new Player(world, 4, 4);

        createLevel1();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        world.step(1/60f, 8, 3);
        camera.position.set(player.body.getPosition().x, 0, 0);
        camera.update();

        batch.begin();
        if(debugOn) {
            debug(); //draw the debug text onto the screen
        }
        batch.end();

        debugRenderer.render(world, camera.combined);
    }

    public void update(float delta) {
        updatePlayer(delta);
    }

    public void updatePlayer(float delta) {

        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveRight();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.moveLeft();
        }

        //jump
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            player.jump();
        }

        //shoot
        if(Gdx.input.isKeyJustPressed(Input.Keys.J)) {
            player.shootBullet(world);
        }
    }

    public void debug() {

        //display player position
        font.draw(batch, "x = " + player.body.getPosition().x, 10, 570);
        font.draw(batch, "y = " + player.body.getPosition().y, 10, 550);
        font.draw(batch, "x-vel = " + player.body.getLinearVelocity().x, 10, 530);
        font.draw(batch, "y-vel = " + player.body.getLinearVelocity().y, 10, 500);

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
        batch.dispose();
        debugRenderer.dispose();
        world.dispose();
    }

    public void createLevel1() {

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        ChainShape platform = new ChainShape();
        platform.createChain(new Vector2[]{new Vector2(-12,-10),
                                           new Vector2(12,-10)});
        fixtureDef.shape = platform;
        fixtureDef.restitution = 0f;
        fixtureDef.density = 1000;
        fixtureDef.friction = 7f;
        world.createBody(bodyDef).createFixture(fixtureDef);
        platform.dispose();

        //platform 2
        platform = new ChainShape();
        platform.createChain(new Vector2[]{new Vector2(11,-6),
                                           new Vector2(30,-6)});
        fixtureDef.shape = platform;
        fixtureDef.restitution = 0f;
        fixtureDef.density = 1000;
        fixtureDef.friction = 7f;
        world.createBody(bodyDef).createFixture(fixtureDef);
        platform.dispose();

        //platform 3
        platform = new ChainShape();
        platform.createChain(new Vector2[]{new Vector2(30,-8),
                                           new Vector2(60,-8)});
        fixtureDef.shape = platform;
        fixtureDef.restitution = 0f;
        fixtureDef.density = 1000;
        fixtureDef.friction = 7f;
        world.createBody(bodyDef).createFixture(fixtureDef);
        platform.dispose();
    }
}




