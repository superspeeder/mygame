package org.delusion.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import org.delusion.game.GameMain;
import org.delusion.game.utils.GLUtils;
import org.delusion.game.world.World;

import java.io.IOException;

public class GameScreen implements Screen {
    private static final float FREECAM_SPEED = 16;
    private final GameMain gm;
    private final InputProcessor input;
    private final World world;
    private final OrthographicCamera camera;

    public GameScreen(GameMain gameMain) {
        gm = gameMain;
        camera = new OrthographicCamera(1920, 1080);
        camera.setToOrtho(false);
        input = new GameInputProcessor(this);
        world = new World(camera);
    }

    @Override
    public void show() {
        GLUtils.setBG(Color.BLACK);
        Gdx.input.setInputProcessor(input);
    }

    @Override
    public void render(float delta) {
        GLUtils.clearScreen();

        world.update();
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            world.getPlayer().setXAcceleration(-1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            world.getPlayer().setXAcceleration(1);
        } else {
            world.getPlayer().setXAcceleration(0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && world.getPlayer().canJump()) {
            world.getPlayer().setYVelocity(15.5f);
        }

        world.render();
    }

    @Override
    public void resize(int width, int height) {

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
        try {
            world.save();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public GameMain getMain() {
        return gm;
    }
}
