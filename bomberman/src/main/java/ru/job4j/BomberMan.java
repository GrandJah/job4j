package ru.job4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 16.12.2017
 */
public class BomberMan {
    /**
     * Игровое поле.
     */
    private final ReentrantLock[][] board;

    /**
     * @param sizeX размер по Х
     * @param sizeY размер по У
     */
    public BomberMan(int sizeX, int sizeY) {
        this.board = new ReentrantLock[sizeY][sizeX];
        for (int i = 0; i < sizeX * sizeY; i++) {
            this.board[i / sizeX][i % sizeX] = new ReentrantLock();
        }
        new AutoStepHero(0, 0, this.board);
        new AutoStepHero(this.board[0].length - 1, this.board.length - 1, this.board);
    }

    /**
     * @param args args
     */
    public static void main(String[] args) {
        BomberMan game = new BomberMan(24, 8);
        SysOut sysOut = new SysOut();
        int stepCount = 0;
        while (true) {
            String step = sysOut.getNextStep(game.board);
            if (step.equals("Game Over!")) {
                break;
            } else {
                System.out.println("Step - " + ++stepCount + System.lineSeparator() + step);
            }
        }
    }
}
