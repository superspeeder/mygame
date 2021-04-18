package org.delusion.game.world;

import fastnoiselite.FastNoiseLite;
import org.delusion.game.Tile;

import java.util.HashMap;
import java.util.Map;

public class WorldGenerator {


    private final FastNoiseLite caveNoise;
    private final FastNoiseLite surfaceNoise;

    public WorldGenerator(int seed) {
        surfaceNoise = new FastNoiseLite(seed);
        caveNoise = new FastNoiseLite(seed + 1);


        surfaceNoise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
        surfaceNoise.SetFractalOctaves(5);
        surfaceNoise.SetFractalType(FastNoiseLite.FractalType.FBm);

        caveNoise.SetNoiseType(FastNoiseLite.NoiseType.ValueCubic);
        caveNoise.SetFractalOctaves(6);
        caveNoise.SetFractalType(FastNoiseLite.FractalType.Ridged);

    }

    private static float clamp(float v, float lower, float upper) {
        if (v < lower) return lower;
        if (v > upper) return upper;
        return v;
    }

    private static float heightThreshold(float lowerClamp, int h) {

        return 0.73f + 0.27f * clamp(1 - Math.abs(h) / 17.0f, 0,1);
    }

    private Map<Integer, Integer> heightmap = new HashMap<>();

    private int getTerrainHeight(int x) {
        if (heightmap.containsKey(x)) return heightmap.get(x);

        float v = surfaceNoise.GetNoise(x,0);
        int h = (int)(v * 30) + 24;

        heightmap.put(x, h);
        return h;
    }

    public Tile generateBasicTerrain(int tx, int ty) {
        int h = getTerrainHeight(tx);
        float v2 = caveNoise.GetNoise(tx,ty);
        if (ty > h || v2 > heightThreshold(0.63f, h)) {
            return Tile.AIR;
        } else {
            return Tile.DIRT;
        }
    }
}
