package ru.job4j.bomberman.helper;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 24.12.2017
 */
public class Board {
    /**
     * Игровое поле.
     */
    private final CellLock[][] board;

    /**
     * @param sizeX размер по Х
     * @param sizeY размер по У
     */
    public Board(int sizeX, int sizeY) {
        this.board = new CellLock[sizeY][sizeX];
        for (int i = 0; i < sizeX * sizeY; i++) {
            this.board[i / sizeX][i % sizeX] = new CellLock();
        }
    }

    /** Проверка пределов поля.
     * @param cell ячейка
     * @return true если в пределах
     */
    private boolean checkCellBoard(Cell cell) {
        return cell.getX() >= 0 && cell.getX() < this.board[0].length
                && cell.getY() >= 0 && cell.getY() < this.board.length;
    }

    /**
     * @param cell ячейка
     * @return Блокер
     */
    public CellLock getCell(Cell cell) {
        if (checkCellBoard(cell)) {
            return board[cell.getY()][cell.getX()];
        } else {
            return null;
        }
    }

    /**
     * @return board board
     */
    public CellLock[][] getBoard() {
        return this.board;
    }
}
