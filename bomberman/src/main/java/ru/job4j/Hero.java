package ru.job4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 16.12.2017
 */
public class Hero {
    /**
     * Направления перехода.
     */
    enum Direction {
        /**
         * Возможные направления.
         */
        Up, Down, Left, Right;

        /**
         * @return случайное направление.
         */
        public static Direction random() {
            return values()[(int) (Math.random() * values().length)];
        }
    }

    /**
     * Координаты героя.
     */
    private final int x, y;
    /**
     * Игоровое поле.
     */
    private final ReentrantLock[][] board;

    /**
     * @param x Координата Х
     * @param y Координата У
     * @param board Игоровое поле
     */
    public Hero(int x, int y, ReentrantLock[][] board) {
        if (!board[y][x].isHeldByCurrentThread()) {
            board[y][x].lock();
        }
        this.x = x;
        this.y = y;
        this.board = board;
    }

    /** Костыль для каста.
     * @param hero стандартный герой.
     */
    protected Hero(Hero hero) {
        this.x = hero.x;
        this.y = hero.y;
        this.board = hero.board;
    }

    /** Переход на новую клетку.
     * @param direction направление перехода.
     * @return Герой на новом месте
     */
    public Hero goNewCell(Direction direction) {
        Hero newCell = this;
        int x = this.x;
        int y = this.y;
        switch (direction) {
            case Up: y--; break;
            case Down: y++; break;
            case Left: x--; break;
            case Right: x++; break;
            default: break;
        }
        if (x >= 0 && x < this.board[0].length
                && y >= 0 && y < this.board.length
                && goCell(x, y)) {
            this.board[this.y][this.x].unlock();
            newCell = new Hero(x, y, this.board);
        }
        return newCell;
    }

    /** Переход на клетку.
     * @param x Координата Х
     * @param y Координата У
     * @return true если переход удачен
     */
    protected boolean goCell(int x, int y) {
        return this.board[y][x].tryLock();
    }
}
