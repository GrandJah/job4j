package ru.job4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 16.12.2017
 */
public class AutoStepHero extends Thread {
    /**
     * Стартовые координаты.
     */
    private final int startX, startY;
    /**
     * Игровое поле.
     */
    private final ReentrantLock[][] board;

    /**
     * @param startX старотовая координата Х
     * @param startY старотовая координата У
     * @param board игровое поле
     */
    public AutoStepHero(int startX, int startY, ReentrantLock[][] board) {
        this.startX = startX;
        this.startY = startY;
        this.board = board;
        this.start();
    }

    @Override
    public void run() {
        HeroGoWait hero = new HeroGoWait(new Hero(this.startX, this.startY, this.board));

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            HeroGoWait old = hero;
            while (old == hero) {
                hero = hero.goNewCell(Hero.Direction.random());
            }
        }
    }

    /**
     * Герой ждущий освобождения клетки.
     */
    class HeroGoWait extends Hero {
        /**
         * @param hero стандартный герой
         */
        HeroGoWait(Hero hero) {
            super(hero);
        }

        @Override
        public HeroGoWait goNewCell(Direction direction) {
            return new HeroGoWait(super.goNewCell(direction));
        }

        @Override
        protected boolean goCell(int x, int y) {
            boolean success = false;
            try {
                success = board[y][x].tryLock(500, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return success;
        }
    }
}
