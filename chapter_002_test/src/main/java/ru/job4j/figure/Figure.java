package ru.job4j.figure;

import ru.job4j.board.Cell;
import ru.job4j.board.exception.ImpossibleMoveException;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 30.05.2017
 */
public abstract class Figure {
    /**
     * Цвет фигуры.
     */
    private boolean color;

    /**
     * @param color цвет фигуры
     */
    public Figure(boolean color) {
        this.color = color;
    }

    /** Вычисляет путь.
     * @param source начальная клетка
     * @param destination клетка назначения
     * @return проходимые клетки исключая начальную и конечную
     * @throws ImpossibleMoveException невозможный путь
     */
    public abstract Cell[] way(Cell source, Cell destination) throws ImpossibleMoveException;

    /** Враждебная фигура или нет.
     * @param figure фигура
     * @return враждебность
     */
    public boolean isEnemy(Figure figure) {
        return this.color != figure.color;
    }
}
