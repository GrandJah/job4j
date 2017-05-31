package ru.job4j.board;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 30.05.2017
 */
public class Cell {
    /**
     * строка (цифра).
     */
    private final int row;
    /**
     * колонка (буква).
     */
    private final int col;

    /**
     * @param row строка (цифра)
     * @param col колонка (буква)
     */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * @return строку (цифра)
     */
    public int getRow() {
        return row;
    }

    /**
     * @return колонку (буква)
     */
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
