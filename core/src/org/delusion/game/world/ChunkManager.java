package org.delusion.game.world;

import com.badlogic.gdx.math.Vector2;
import fastnoiselite.FastNoiseLite;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ChunkManager {
    private static final int LOAD_DISTANCE = 4;
    private ConcurrentHashMap<Vector2, Chunk> chunks;
    private ExecutorService cacheInvalidateThread;
    private ExecutorService generateChunksThread;
    private ExecutorService saveChunksThread;

    private FastNoiseLite noise = new FastNoiseLite();

    private World world;

    public ChunkManager(World world) {
        this.world = world;
        chunks = new ConcurrentHashMap<>();
        cacheInvalidateThread = Executors.newSingleThreadExecutor();
        generateChunksThread = Executors.newCachedThreadPool();
        saveChunksThread = Executors.newCachedThreadPool();
        noise.SetNoiseType(FastNoiseLite.NoiseType.ValueCubic);
        noise.SetFractalOctaves(6);
//        noise.SetFractalLacunarity(3);
        noise.SetFractalType(FastNoiseLite.FractalType.Ridged);
    }

    public void runUpdates() {
        Vector2 centerChunk = world.getCenterChunk();
        boolean[][] avc = new boolean[LOAD_DISTANCE * 2 + 1][LOAD_DISTANCE * 2 + 1];
        for (Vector2 p : chunks.keySet()) {
            if (Math.abs(p.x - centerChunk.x) > LOAD_DISTANCE || Math.abs(p.y - centerChunk.y) > LOAD_DISTANCE) {
                Chunk c = chunks.get(p);
                saveChunksThread.submit(() -> {
                    try {
                        c.save();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                chunks.remove(p);
            } else {
                avc[(int) (p.x - centerChunk.x + LOAD_DISTANCE)][(int) (p.y - centerChunk.y + LOAD_DISTANCE)] = true;
            }
        }

        for (int x = 0; x <= 2 * LOAD_DISTANCE; x++) {
            for (int y = 0; y <= 2 * LOAD_DISTANCE; y++) {
                if (!avc[x][y]) {
                    Chunk c = new Chunk((int) (x + centerChunk.x - LOAD_DISTANCE),(int)(y + centerChunk.y - LOAD_DISTANCE));
                    chunks.put(new Vector2((int) (x + centerChunk.x - LOAD_DISTANCE),(int)(y + centerChunk.y - LOAD_DISTANCE)), c);
                    generateChunksThread.submit(new Generator(this, c));
                }
            }
        }
    }

    public Iterable<? extends Chunk> getChunks() {
        return chunks.values().stream().collect(Collectors.toUnmodifiableList());
    }

    public void save() throws IOException {
        for (Chunk c: chunks.values()) {
            c.save();
        }
    }

    public Chunk getChunk(int cx, int cy) {
        return chunks.get(new Vector2(cx,cy));
    }

    public Chunk getChunk(Vector2 v) {
        return chunks.get(v);
    }

    public Optional<Chunk> getChunkSafe(int cx, int cy) {
        return Optional.ofNullable(chunks.getOrDefault(new Vector2(cx,cy),null));
    }

    private static class Generator implements Runnable {

        private final ChunkManager chunkManager;
        private Chunk c;

        public Generator(final ChunkManager chunkManager, final Chunk c) {
            this.chunkManager = chunkManager;
            this.c = c;
        }

        @Override
        public void run() {
            chunkManager.generateNewChunk(c);
        }
    }

    private void generateNewChunk(Chunk c) {
        c.tryLoad();
        c.runGenerator(world.getGenerator());
        System.out.println("Generated Chunk " + c.getPos());
    }
}
