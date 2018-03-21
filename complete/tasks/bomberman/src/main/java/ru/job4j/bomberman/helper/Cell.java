package ru.job4j.bomberman.helper;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 24.12.2017
 */
public class Cell {
    /**
     * Координаты героя.
     */
    private final int x, y;

    /**
     * @param x координата Х
     * @param y координата У
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return x x
     */
    public int getX() {
        return this.x;
    }

    /**
     * @return y y
     */
    public int getY() {
        return this.y;
    }
}
