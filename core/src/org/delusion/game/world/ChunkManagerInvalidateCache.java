package org.delusion.game.world;

import com.badlogic.gdx.math.Vector2;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class ChunkManagerInvalidateCache implements Callable<Void> {
    private final ConcurrentHashMap<Vector2, Chunk> chunks;
    private final Vector2 centerChunk;

    public ChunkManagerInvalidateCache(ConcurrentHashMap<Vector2, Chunk> chunks, Vector2 centerChunk) {
        this.chunks = chunks;
        this.centerChunk = centerChunk;
    }

    @Override
    public Void call() throws Exception {



        return null;
    }
}
