package ru.job4j.board;

import ru.job4j.board.exception.FigureNotFoundException;
import ru.job4j.board.exception.ImpossibleMoveException;
import ru.job4j.board.exception.OccupiedWayException;
import ru.job4j.figure.Figure;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 30.05.2017
 */
public class Board {
    private Figure[][] figures;

    public boolean move(Cell source, Cell dist) throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException {
        Figure figure = findFigure(source);
        Cell[] way = figure.way(source, dist);

    }

    private Figure findFigure(Cell cell) throws FigureNotFoundException {
        Figure figure = figures[cell.getRow()][cell.getCol()];
        if (figure == null) {
            throw new FigureNotFoundException();
        }
        return figure;
    }


}
