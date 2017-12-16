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
     * Сигнал для тайсмера.
     */
    private final Object signal = new Object();


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

        initTimeSignal();
    }


    /**
     * Таймер.
     */
    private void initTimeSignal() {
        new Thread(()-> {
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
                synchronized (this.signal) {
                    this.signal.notify();
                }
            }
        }).start();
    }

    /** Возвращает доску, блокирутся в ожидании сигнала таймера.
     * @return Отображение блокировок доски.
     */
    private String getNextStep() {
        StringBuilder boardString = new StringBuilder();
        synchronized (this.signal) {
            try {
                this.signal.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (ReentrantLock[] row : this.board) {
                for (ReentrantLock cell : row) {
                    boardString.append(cell.isLocked() ? "1" : "0");
                }
                boardString.append(System.lineSeparator());
            }
        }
        return boardString.toString();
    }

    /**
     * @param args args
     */
    public static void main(String[] args) {
        BomberMan game = new BomberMan(24, 8);
        int stepCount = 0;
        while (true) {
            String step = game.getNextStep();
            if (step.equals("Game Over!")) {
                break;
            } else {
                System.out.println("Step - " + ++stepCount + System.lineSeparator() + step);
            }
        }
    }
}
