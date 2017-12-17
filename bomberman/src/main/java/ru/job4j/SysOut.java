package ru.job4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 18.12.2017
 */
public class SysOut {
    /**
     * Сигнал для тайсмера.
     */
    private final Object signal = new Object();

    /** Default. */
    SysOut() {
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
     * @param board отображаемая доска.
     * @return Отображение блокировок доски.
     */
    public String getNextStep(ReentrantLock[][] board) {
        StringBuilder boardString = new StringBuilder();
        synchronized (this.signal) {
            try {
                this.signal.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (ReentrantLock[] row : board) {
                for (ReentrantLock cell : row) {
                    boardString.append(cell.isLocked() ? "1" : "0");
                }
                boardString.append(System.lineSeparator());
            }
        }
        return boardString.toString();
    }
}
