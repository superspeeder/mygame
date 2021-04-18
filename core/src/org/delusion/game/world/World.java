package org.delusion.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import org.delusion.game.Player;

import java.awt.*;
import java.io.IOException;
import java.util.Optional;

public class World {

    private SpriteBatch entityBatch;
    private WorldRenderer worldRenderer;
    private WorldGenerator worldGenerator;

    private ChunkManager chunkManager;
    private OrthographicCamera camera;

    private Player player;
    private SpriteBatch hudBatch;
    private Vector2 gravity;

    public World(OrthographicCamera camera) {
        this.camera = camera;

        gravity = new Vector2(0, -0.6f);

        chunkManager = new ChunkManager(this);
        worldGenerator = new WorldGenerator(Long.hashCode(System.currentTimeMillis()));

        worldRenderer = new WorldRenderer(this, camera, chunkManager);

        entityBatch = new SpriteBatch();
        hudBatch = new SpriteBatch();
        entityBatch.setProjectionMatrix(camera.projection);

        player = new Player(this, new Vector2(0, 0), camera);

    }

    public void update() {
        chunkManager.runUpdates();
        player.update();
        camera.update();
    }



    public Vector2 getCenterChunk() {
        return fixAsInt(new Vector2(camera.position.x, camera.position.y).scl(1.0f / Chunk.CHUNK_SIZE_PIXEL));
    }

    private static Vector2 fixAsInt(Vector2 vec) {
        return new Vector2((int)vec.x, (int)vec.y);
    }

    public void save() throws IOException {
        chunkManager.save();
    }

    public WorldGenerator getGenerator() {
        return worldGenerator;
    }

    public void render() {
        worldRenderer.render();
        entityBatch.begin();
        entityBatch.setTransformMatrix(camera.view);
        player.draw(entityBatch);
        entityBatch.end();
        hudBatch.begin();
        player.renderDebug(hudBatch);
        hudBatch.end();
    }

    public float getHorizontalGravity() {
        return gravity.x;
    }

    public float getVerticalGravity() {
        return gravity.y;
    }

    public ChunkManager getChunkManager() {
        return chunkManager;
    }


    public boolean isTileAir(int x, int y) {

        int cx,cy,tx,ty;

        cx = Math.floorDiv(x, Chunk.CHUNK_SIZE);
        cy = Math.floorDiv(y, Chunk.CHUNK_SIZE);
        tx = Math.floorMod(x, Chunk.CHUNK_SIZE);
        ty = Math.floorMod(y, Chunk.CHUNK_SIZE);

        Optional<Chunk> chunk = chunkManager.getChunkSafe(cx,cy);

        int finalTx = tx;
        int finalTy = ty;

        return chunk.filter(value -> value.getTile(finalTx, finalTy) == -1).isPresent();
    }

    public Player getPlayer() {
        return player;
    }

    public void debugRender(ShapeRenderer sr, ShapeRenderer srF) {
        for (Chunk c : chunkManager.getChunks()) {
            c.debugRender(sr, srF);
        }
    }
}
