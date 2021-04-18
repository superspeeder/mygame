package org.delusion.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import org.delusion.game.utils.Utils;
import org.delusion.game.world.Chunk;
import org.delusion.game.world.World;

import java.awt.*;
import java.util.HashSet;

public class Player extends Sprite {

    private static final int WIDTH = 16;
    private static final int HEIGHT = 32;
    private float xVelocity;
    private float yVelocity;
    private float xAcceleration;
    private float yAcceleration;

    private static final float X_VELOCITY_CAP = 15;
    private static final float Y_VELOCITY_CAP = 31;

    private World world;
    private OrthographicCamera followerCamera;

    private Vector2 position = new Vector2(0,0);
    private ShapeRenderer sr;
    private BitmapFont font;
    private boolean onGround = false;

    public Player(World world, Vector2 position, OrthographicCamera followerCamera) {
        super(new Texture(Gdx.files.internal("textures/player.png")));
        this.world = world;
        this.followerCamera = followerCamera;
        setPosition(position);
        sr = new ShapeRenderer();
        font = new BitmapFont();
    }

    public void setPosition(Vector2 position) {
        position.set(position);
        super.setPosition(position.x, position.y);
    }

    public boolean canJump() {
        return onGround;
    }

    public void jump() {

    }

    public void update() {
        updateVelocity();
        moveHorizontally();
        moveVertically();

        followerCamera.position.set(getX(), getY(), followerCamera.position.z);
        followerCamera.update();
        setPosition(position);

    }

    public void renderDebug(SpriteBatch batch) {
        font.draw(batch, position.toString(), 20, Gdx.graphics.getHeight() - 20);
    }

    private void updateVelocity() {
        yVelocity = Math.max(Math.min(yVelocity + yAcceleration + world.getVerticalGravity(), Y_VELOCITY_CAP), -Y_VELOCITY_CAP);
        xVelocity = Math.max(Math.min(applyFriction(xVelocity + xAcceleration + world.getHorizontalGravity()), X_VELOCITY_CAP), -X_VELOCITY_CAP);
    }

    private float applyFriction(float vel) {
        if (Math.abs(vel) < 0.01f) {
            return 0;
        }
        return 0.9f * vel;
    }

    private void moveHorizontally() {
        if (xVelocity != 0) {
            position.add(xVelocity, 0);

            boolean movingRight = xVelocity > 0;

            int minTX = (int) Math.floor(position.x / Chunk.TILE_SIZE);
            int maxTX = (int) Math.ceil((position.x + getWidth()) / Chunk.TILE_SIZE);

            int minTY = (int) Math.floor(position.y / (float)Chunk.TILE_SIZE);
            int maxTY = (int) Math.ceil((position.y + getHeight()) / (float)Chunk.TILE_SIZE);

            for (int x = minTX ; x < maxTX ; x++) {
                for (int y = minTY ; y < maxTY ; y++) {
                    if (!world.isTileAir(x, y)) {
                        if (movingRight) {
                            if (right() > Utils.tile2pixel(x)) {
                                right(Utils.tile2pixel(x));
                                xVelocity = 0;
                            }
                        } else {
                            if (left() < Utils.tile2pixel(x + 1)) {
                                left(Utils.tile2pixel(x + 1));
                                xVelocity = 0;
                            }
                        }
                    }
                }
            }
        }

    }

    public void right(float v) {
        position.x = v - getWidth();
    }

    public void left(float v) {
        position.x = v;
    }

    public void top(float v) {
        position.y = v - getHeight();
    }

    public void bottom(float v) {
        position.y = v;
    }

    public float right() {
        return position.x + getWidth();
    }

    public float left() {
        return position.x;
    }

    public float top() {
        return position.y + getHeight();
    }

    public float bottom() {
        return position.y;
    }

    private void moveVertically() {
        if (yVelocity != 0) {
            onGround = false;
            position.add(0, yVelocity);
            boolean movingUp = yVelocity > 0;


            int minTX = (int) Math.floor(position.x / Chunk.TILE_SIZE);
            int maxTX = (int) Math.ceil((position.x + getWidth()) / Chunk.TILE_SIZE);

            int minTY = (int) Math.floor(position.y / Chunk.TILE_SIZE);
            int maxTY = (int) Math.ceil((position.y + getHeight()) / Chunk.TILE_SIZE);

            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(Color.RED);
            sr.setProjectionMatrix(followerCamera.combined);
            sr.rect(minTX * Chunk.TILE_SIZE, minTY * Chunk.TILE_SIZE, (maxTX - minTX) * Chunk.TILE_SIZE, (maxTY - minTY) * Chunk.TILE_SIZE);
            sr.end();


            for (int x = minTX ; x < maxTX ; x++) {
                for (int y = minTY ; y < maxTY ; y++) {
                    if (!world.isTileAir(x, y)) {
                        if (movingUp) {
                            if (top() > Utils.tile2pixel(y)) {
                                top(Utils.tile2pixel(y));
                                yVelocity = 0;
                            }
                        } else {
                            if (bottom() < Utils.tile2pixel(y + 1)) {
                                bottom(Utils.tile2pixel(y + 1));
                                yVelocity = 0;
                                onGround = true;
                            }
                        }
                    }
                }
            }
        }
    }

    public void setXAcceleration(float accl) {
        xAcceleration = accl;
    }

    public void setYVelocity(float yvel) {
        yVelocity = yvel;
    }
}
