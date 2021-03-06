package com.mcelrea;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

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
    public static ArrayList<Bullet> playerBullets;
    public static ArrayList<Enemy> enemies;
    public static ArrayList<Platform> platforms;
    Sound music1;

    @Override
    public void show() {
        playerBullets = new ArrayList<Bullet>();
        enemies = new ArrayList<Enemy>();
        platforms = new ArrayList<Platform>();

        //create the world with Earth gravity
        world = new World(new Vector2(0, -9.81f), true);
        world.setContactFilter(new MyContactFilter());

        camera = new OrthographicCamera();
        debugRenderer = new Box2DDebugRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        player = new Player(world, 4, 4);

        createLevel1();
        music1 = Gdx.audio.newSound(Gdx.files.internal("music1.mp3"));
        music1.loop();
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
        player.paint(batch, camera);
        drawBullets();
        drawEnemies();
        drawPlatforms();
        batch.end();

        //debugRenderer.render(world, camera.combined);


        removeDeadEnemies();
        removeDeadBullets();
    }

    private void drawPlatforms() {
        for(int i=0; i < platforms.size(); i++) {
            platforms.get(i).paint(batch, camera);
        }
    }

    private void drawEnemies() {
        for(int i=0; i < enemies.size(); i++) {
            enemies.get(i).paint(batch, camera);
        }
    }

    private void drawBullets() {
        for(int i=0; i < playerBullets.size(); i++) {
            playerBullets.get(i).paint(batch, camera);
        }
    }

    private void removeDeadEnemies() {

        //go through all the enemies
        for(int i=0; i < enemies.size(); i++) {
            //if the enemy is marked for removal
            if(enemies.get(i).alive == false) {
                enemies.get(i).die(world);//remove the enemy from the world
                enemies.remove(i);//remove the enemy from the list
                i--;//decrement i because we just removed an enemy from the list
            }
        }
    }

    private void removeDeadBullets() {

        //go through all the bullets
        for(int i=0; i < playerBullets.size(); i++) {
            //grab the next bullet to inspect
            Bullet b = playerBullets.get(i);

            //if the bullet is dead
            if(b.alive == false) {
                world.destroyBody(b.body);//remove bullet from the world
                playerBullets.remove(b);//remove bullet from the list
                i--;//decrement i because I removed a bullet from list
            }
            else { //else check if its off the screen

                //grab the bullets position in the world in meters
                float x = b.body.getPosition().x;
                float y = b.body.getPosition().y;
                Vector3 worldPos = new Vector3(x, y, 0);

                //convert meters to screen pixels
                Vector3 screenPos = camera.project(worldPos);

                //if its off the screen
                if ((screenPos.x < 0 || screenPos.x > 800)) {
                    world.destroyBody(b.body);//remove bullet from the world
                    playerBullets.remove(b);//remove bullet from the list
                    i--;//decrement i because I removed a bullet from list
                }
            }
        }
    }

    public void update(float delta) {
        updatePlayer(delta);
        updateEnemies(delta);
    }

    private void updateEnemies(float delta) {

        //go through the enemy list and
        //tell every enemy to act
        for(int i=0; i < enemies.size(); i++) {
            enemies.get(i).act(world, delta);
        }
    }

    public void updatePlayer(float delta) {

        if(player.resetPosition == true) {
            player.body.setTransform(4,4,0);
            player.resetPosition = false;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveRight();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.moveLeft();
        }

        //jump
        if(Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            player.jump();
        }

        //shoot
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            player.shootBullet(world);
        }
    }

    public void debug() {

        //display player position
        font.draw(batch, "x = " + player.body.getPosition().x, 10, 570);
        font.draw(batch, "y = " + player.body.getPosition().y, 10, 550);
        font.draw(batch, "x-vel = " + player.body.getLinearVelocity().x, 10, 530);
        font.draw(batch, "y-vel = " + player.body.getLinearVelocity().y, 10, 500);
        font.draw(batch, "# of bullets = " + playerBullets.size(), 10, 470);
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

        platforms.add(new LongPlatform(world, camera, -12, -10));
        platforms.add(new LongPlatform(world, camera, -12, 5));
        platforms.add(new StandardPlatform(world, camera, 27, -6));
        platforms.add(new SmallPlatform(world, camera, 45, -8));
        platforms.add(new BoxPlatform(world, camera, 60, 5));
        platforms.add(new StandardPlatform(world, camera, 60, 0));
        platforms.add(new BoxPlatform(world, camera, 80, 2));
        platforms.add(new BoxPlatform(world, camera, 90, 2));
        platforms.add(new LongPlatform(world, camera, 95, -6));
        platforms.add(new SmallPlatform(world, camera, 130, 0));

        //create the enemies
        enemies.add(new PatrolEnemy(world, 36, -5, 15, 35, 0, 0, "mummy.png"));
        enemies.add(new PatrolEnemy(world, 45, 10, 0, 0, -3, 10, "mummy.png"));
        enemies.add(new PatrolEnemy(world, 92, -2, 90, 100, 0, 0, "mummy.png"));
        enemies.add(new PatrolEnemy(world, 120, 6, 0, 0, -3, 10, "mummy.png"));
    }
}




