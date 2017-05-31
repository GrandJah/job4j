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
    /**
     * Шахматная доска.
     */
    private Figure[][] figures = new Figure[8][8];

    /** Главный медод движения по доске.
     * @param source начальная клетка
     * @param destination конечная клетка
     * @return передвинута фигура
     * @throws ImpossibleMoveException путь невозможен
     * @throws OccupiedWayException путь занят фигурами
     * @throws FigureNotFoundException нет фигуры в указанной клетке
     */
    public boolean move(Cell source, Cell destination) throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException {
        boolean isMoved = false;
        if (!source.equals(destination)) {
            Figure figure = findFigure(source);
            Cell[] way = figure.way(source, destination);
            for (Cell cell:way) {
                if (getFigureOnPosition(cell) != null) {
                    throw new OccupiedWayException();
                }
            }
            Figure figureDestination = getFigureOnPosition(destination);
            if (figureDestination == null || figureDestination.isEnemy(figure)) {
                setFigureOnPosition(null, source);
                setFigureOnPosition(figure, destination);
                isMoved = true;
            } else {
                throw new OccupiedWayException();
            }
        }
        return isMoved;
    }

    /** "Поиск" фигуры в указанной клетке.
     * @param position клетка поиска.
     * @return найденная фигура
     * @throws FigureNotFoundException фигура не найдена
     */
    private Figure findFigure(Cell position) throws FigureNotFoundException {
        Figure figure = getFigureOnPosition(position);
        if (figure == null) {
            throw new FigureNotFoundException();
        }
        return figure;
    }

    /** Метод установки фигуры на доску.
     * @param figure фигура
     * @param position клетка установки
     */
    public void setFigureOnPosition(Figure figure, Cell position) {
        this.figures[position.getRow()][position.getCol()] = figure;
    }

    /** Просмотр клетки.
     * @param position клетка просмотра
     * @return фигура
     */
    public Figure getFigureOnPosition(Cell position) {
        return this.figures[position.getRow()][position.getCol()];
    }


}
