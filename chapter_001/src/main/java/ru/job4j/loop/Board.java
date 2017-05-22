package ru.job4j.loop;

/**
 * job4j.
 *
 * @author Igor Kovalkov aka Atlant
 * @version 0.1
 * @since 22.05.2017
 */
public class Board {
    /**
     * @param width  ширина
     * @param height высота
     * @return доска
     */
    public String paint(int width, int height) {
        StringBuilder board = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                board.append((x + y) % 2 == 0 ? "X" : " ");
            }
            board.append(System.lineSeparator());
        }
        return board.toString();
    }
}
