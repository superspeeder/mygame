package org.delusion.game.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import fastnoiselite.FastNoiseLite;
import net.dermetfan.gdx.physics.box2d.PositionController;
import org.delusion.game.utils.IntVec2;
import org.delusion.game.utils.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Chunk {
    public static final int CHUNK_SIZE = 32;
    public static final int TILE_SIZE = 16;
    public static final int CHUNK_SIZE_PIXEL = CHUNK_SIZE * TILE_SIZE;
    private final int cx;
    private final int cy;
    private final int tile_x, tile_y;

    private int[] map = new int[CHUNK_SIZE * CHUNK_SIZE];
    private boolean[] changemap = new boolean[CHUNK_SIZE * CHUNK_SIZE];


    public IntVec2 getPos() {
        return new IntVec2(cx, cy);
    }

    public Chunk(int cx, int cy) {
        this.cx = cx;
        this.cy = cy;

        tile_x = cx * CHUNK_SIZE;
        tile_y = cy * CHUNK_SIZE;
        Arrays.fill(changemap, false);
    }

    public void save() throws IOException {
        // Generate save map

        // -n represents n consecutive tiles that didn't change

        List<Integer> compressedSave = new ArrayList<>();

        int unchangedTiles = 0;
        for (int y = 0; y < CHUNK_SIZE; y++) {
            for (int x = 0; x < CHUNK_SIZE; x++) {
                if (changemap[y * CHUNK_SIZE + x]) {
                    if (unchangedTiles > 0) {
                        compressedSave.add(-unchangedTiles);
                    }
                    compressedSave.add(map[y * CHUNK_SIZE + x] + 1); // increment by one for saving so air(-1) is 0 to make compressed empty space work
                    unchangedTiles = 0;
                } else {
                    unchangedTiles++;
                }
            }
        }
        if (compressedSave.isEmpty()) return;

        StringBuilder sb = new StringBuilder();
        for (int i : compressedSave) {
            sb.append(i);
            sb.append(',');
        }
        writeFile(sb.toString());
    }

    private void writeFile(String content) throws IOException {
        FileWriter writer = new FileWriter("chunks/chunk_" + cx + "_" + cy + ".chunk");
        writer.write(content);

        writer.close();
    }

    private void load() throws IOException {
        FileReader reader = new FileReader("chunks/chunk_" + cx + "_" + cy + ".chunk");
        BufferedReader readerb = new BufferedReader(reader);
        String content = readerb.readLine();


        int curind = 0;

        if (content != null) {
            for (int i : Arrays.stream(content.split(",")).mapToInt(Integer::parseInt).toArray()) {
                if (i < 0) {
                    curind += -i;
                } else {

                    changemap[curind] = true;
                    map[curind++] = i - 1;
                }
            }
        }

    }

    public void tryLoad() {

        if (new File("chunks/chunk_" + cx + "_" + cy + ".chunk").exists()) {
            try {
                load();


            } catch (IOException e) {
                System.out.println("Erorringsmssm");
                e.printStackTrace();
            }
        }
    }

    private static float clamp(float v, float lower, float upper) {
        if (v < lower) return lower;
        if (v > upper) return upper;
        return v;
    }

    private static float heightThreshold(float lowerClamp, int h) {
        return 0.73f + 0.27f * clamp(1 - Math.abs(h) / 10.0f, 0,1);
    }

    public void runGenerator(WorldGenerator gen) {

        for (int x = 0; x < CHUNK_SIZE ; x++) {
//            float v = noise.GetNoise(tile_x + x,0);
//            int h = (int)(v * 30);
            for (int y = 0 ; y < CHUNK_SIZE ; y++) {
//                float v2 = noise.GetNoise(tile_x + x,tile_y + y);
                if (!changemap[y * CHUNK_SIZE + x]) {
//                    if (tile_y + y > h || v2 > heightThreshold(0.63f, h)) {
//                        map[y * CHUNK_SIZE + x] = -1;
//                    } else {
//                        map[y * CHUNK_SIZE + x] = 0;
//                    }

                    map[y * CHUNK_SIZE + x] = gen.generateBasicTerrain(tile_x + x, tile_y + y).getTileID();

                }
            }
        }
    }

    public void render(SpriteBatch mapBatch, TextureAtlas mapAtlas) {
        for (int y = 0 ; y < CHUNK_SIZE ; y++) {
            for (int x = 0; x < CHUNK_SIZE; x++) {
                int ti = map[y * CHUNK_SIZE + x];
                if (ti != -1) {
                    mapBatch.draw(mapAtlas.findRegion("tile", ti), x * TILE_SIZE + cx * CHUNK_SIZE_PIXEL, y * TILE_SIZE + cy * CHUNK_SIZE_PIXEL);
                }
            }
        }
    }

    public int getTile(int x, int y) {
//        System.out.println(x + ", " + y);
        return map[y * CHUNK_SIZE + x];
    }

    public void debugRender(ShapeRenderer sr, ShapeRenderer srF) {
        sr.setColor(Color.WHITE);
//        float x1 = Utils.chunk2pixel(cx);
//        float x2 = Utils.chunk2pixel(cx + Utils.sgn(cx));


        sr.setColor(Color.GRAY);
//        for (int i = 0 ; i < CHUNK_SIZE ; i++) {
//            sr.line(Utils.chunk2pixel(cx), Utils.chunk2pixel(cy) + Utils.tile2pixel(i), Utils.chunk2pixel(cx + 1), Utils.chunk2pixel(cy) + Utils.tile2pixel(i));
//            sr.line(Utils.chunk2pixel(cx) + Utils.tile2pixel(i), Utils.chunk2pixel(cy), Utils.chunk2pixel(cx) + Utils.tile2pixel(i), Utils.chunk2pixel(cy + 1));
//        }
        srF.setColor(Color.YELLOW);
        srF.circle(Utils.chunk2pixel(cx + 0.5f), Utils.chunk2pixel(cy + 0.5f), 3);


    }
}
