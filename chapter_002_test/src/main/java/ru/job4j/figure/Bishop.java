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
public class Bishop implements Figure {
    @Override
    public Cell[] way(Cell source, Cell dist) throws ImpossibleMoveException {
        return new Cell[0];
    }
}
