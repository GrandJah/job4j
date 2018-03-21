package ru.job4j.bomberman.screen;

import ru.job4j.bomberman.helper.CellLock;

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
    public SysOut() {
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
    public String getNextStep(CellLock[][] board) {
        StringBuilder boardString = new StringBuilder();
        synchronized (this.signal) {
            try {
                this.signal.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (CellLock[] row : board) {
                boardString.append("|");
                for (CellLock cell : row) {
                    boardString.append(cell.getTypeLetter());
                }
                boardString.append("|");
                boardString.append(System.lineSeparator());
            }
        }
        return boardString.toString();
    }
}
