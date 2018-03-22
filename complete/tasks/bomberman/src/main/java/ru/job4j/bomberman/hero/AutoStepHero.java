package ru.job4j.bomberman.hero;

import ru.job4j.bomberman.helper.Board;
import ru.job4j.bomberman.helper.Cell;
import ru.job4j.bomberman.helper.CellLock;
import ru.job4j.bomberman.helper.Entity;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 16.12.2017
 */
public class AutoStepHero extends Thread {
    /**
     * Игровое поле.
     */
    private final Board board;

    /**
     * Управляемый герой.
     */
    private final Hero hero;

    /**
     * Героические координаты.
     */
    private Cell cell;

    /**
     * @param cell старотовая координата
     * @param board игровое поле
     * @param hero герой для самостаятельного похода.
     */
    AutoStepHero(Cell cell, Board board, Hero hero) {
        this.hero = hero;
        this.board = board;
        this.cell = cell;
        this.start();
    }

    @Override
    public void run() {
        this.board.getCell(this.cell).lock(this.hero);
        while (true) {
            Cell newCell = this.hero.goNewCell(this.cell, Hero.Direction.random());
            if (newCell != null) {
                CellLock lock = this.board.getCell(newCell);
                if (lock != null) {
                    Entity entity = lock.tryLock(this.hero);
                    if (entity != this.hero) {
                        entity.signal(this.hero);
                        entity = lock.waitLock(this.hero);
                    }
                    if (entity == this.hero) {
                        this.board.getCell(this.cell).unlock();
                        this.cell = newCell;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }
    }
}

