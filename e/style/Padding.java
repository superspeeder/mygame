package org.delusion.game.ui.style;

public class Padding {
    private final int xPadding;
    private final int yPadding;

    public Padding(int xPadding, int yPadding) {
        this.xPadding = xPadding;
        this.yPadding = yPadding;
    }

    public int getYPadding() {
        return yPadding;
    }

    public int getXPadding() {
        return xPadding;
    }
}
