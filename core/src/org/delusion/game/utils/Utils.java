package org.delusion.game.utils;

import org.delusion.game.world.Chunk;

public class Utils {
    public static float tile2pixel(float v) {
        return v * Chunk.TILE_SIZE;
    }

    public static float chunk2pixel(float v) {
        return v * Chunk.CHUNK_SIZE_PIXEL;
    }

//    public static int sgn(double i) {
//        if (i > 0)
//    }
}
