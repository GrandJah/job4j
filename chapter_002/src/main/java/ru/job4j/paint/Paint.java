package ru.job4j.paint;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 26.05.2017
 */
public class Paint {
    /** Рисуем фигуру.
     * @param shape фигура
     */
    public void draw(Shape shape) {
        System.out.println(shape.pic());
    }
}
