package ru.job4j.board;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 30.05.2017
 */
public class Cell {
    private final int row;
    private final int col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object obj) {
        boolean compare = false;
        if (obj.getClass() == Cell.class) {
            Cell cell = (Cell) obj;
            if (cell.col == this.col && cell.row == this.row) {
                compare = true;
            }
        }
        return compare;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + row;
        result = 31 * result + col;
        return result;
    }
}
