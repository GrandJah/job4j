package ru.job4j.loop;

/**
 * job4j.
 *
 * @author Igor Kovalkov aka Atlant
 * @version 0.1
 * @since 22.05.2017
 */
public class Paint {
    /**
     * @param height высота
     * @return Пирамида
     */
    public  String piramid(int height) {
        int width = height * 2 - 1;
        StringBuilder piramid = new StringBuilder();
        for (int y = 0; y < height; y++) {
            int widthLevel = y * 2 + 1;
            int left = (width - widthLevel) / 2;
            int right = width - left;
            for (int x = 0; x < width; x++) {
                piramid.append((left <= x && x < right) ? "^" : " ");
            }
            piramid.append(System.lineSeparator());
        }
        return piramid.toString();
    }
}
