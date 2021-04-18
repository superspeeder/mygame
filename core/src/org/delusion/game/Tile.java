package org.delusion.game;

public enum Tile {
    DIRT(0), AIR(-1);

    private final int tileID;

    Tile(int i) {

        tileID = i;
    }


    public int getTileID() {
        return tileID;
    }
}
