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

public class WorldRenderer {
    private ShaderProgram MAP_SHADER;
    private FrameBuffer worldFBO;
    private SpriteBatch mapBatch;
    private TextureAtlas mapAtlas;
    private World world;
    private OrthographicCamera camera;
    private ChunkManager chunkManager;
    private ShapeRenderer shapeRenderer, shapeRendererFilled;
    private int frame = 0;

    public WorldRenderer(World world, OrthographicCamera camera, ChunkManager chunkManager) {
        this.world = world;
        this.camera = camera;
        this.chunkManager = chunkManager;

        mapBatch = new SpriteBatch();
        mapAtlas = new TextureAtlas(Gdx.files.internal("atlas/tileset.atlas"));

        shapeRenderer = new ShapeRenderer();
        shapeRendererFilled = new ShapeRenderer();
        mapBatch.setProjectionMatrix(camera.projection);
    }

    public void render() {
        mapBatch.begin();
        mapBatch.setTransformMatrix(camera.view);

        for (Chunk c : chunkManager.getChunks()) {
            c.render(mapBatch, mapAtlas);
        }
        mapBatch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRendererFilled.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRendererFilled.setProjectionMatrix(camera.combined);
        world.debugRender(shapeRenderer, shapeRendererFilled);
        shapeRendererFilled.end();
        shapeRenderer.end();

        frame++;
    }
}
