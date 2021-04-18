package org.delusion.game.utils;

import java.util.Objects;

public class IntVec2 {
    private final int x;
    private final int y;

    public IntVec2(int x, int y) {

        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntVec2 intVec2 = (IntVec2) o;
        return x == intVec2.x &&
                y == intVec2.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
