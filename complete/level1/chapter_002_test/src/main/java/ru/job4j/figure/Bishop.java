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
public class Bishop extends Figure {
    /**
     * @param color цвет фигуры
     */
    public Bishop(boolean color) {
        super(color);
    }

    @Override
    public Cell[] way(Cell source, Cell destination) throws ImpossibleMoveException {
        int deltaRow = source.getRow() - destination.getRow();
        int deltaCol = source.getCol() - destination.getCol();
        if (Math.abs(deltaRow) != Math.abs(deltaCol)) {
            throw new ImpossibleMoveException();
        }
        int sizeWay = Math.abs(deltaRow) - 1;
        if (sizeWay < 0) {
            sizeWay = 0;
        }
        Cell[] way = new Cell[sizeWay];
        for (int i = 0; i < sizeWay; i++) {
            way[i] = new Cell(source.getRow() + (deltaRow < 0 ? 1 : -1) * (i + 1),
                    source.getCol() + (deltaCol < 0 ? 1 : -1) * (i + 1));
        }
        return way;
    }
}
