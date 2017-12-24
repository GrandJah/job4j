package ru.job4j.bomberman;

import ru.job4j.bomberman.helper.Board;
import ru.job4j.bomberman.helper.Cell;
import ru.job4j.bomberman.helper.Wall;
import ru.job4j.bomberman.hero.BomberMan;
import ru.job4j.bomberman.hero.Monster;
import ru.job4j.bomberman.screen.SysOut;


/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 16.12.2017
 */
public class Game {
    /**
     * Рандомайзер клеток.
     */
    private final RandCell rand;
    /**
     * Игровое поле.
     */
    private Board board;

    /**
     * @param sizeX размер по Х
     * @param sizeY размер по У
     * @param monster кол-во монстров
     * @param wall кол-во стен
     */
    Game(int sizeX, int sizeY, int monster, int wall) {
        this.board = new Board(sizeX, sizeY);
        this.rand = new RandCell(sizeX, sizeY);
        new BomberMan(new Cell(7, 4), this.board);
        monster(monster);
        wall(wall);
    }

    /**
     * @param count кол-во монстров
     */
    private void monster(int count) {
        for (int i = 0; i < count; i++) {
            new Monster(this.rand.rand(), this.board);
        }
    }

    /**
     * @param count кол-во стен
     */
    private void wall(int count) {
        for (int i = 0; i < count; i++) {
            new Wall(this.rand.rand(), this.board);
        }
    }

    /**
     * @param args args
     */
    public static void main(String[] args) {
        Game game = new Game(24, 8, 10, 20);
        SysOut sysOut = new SysOut();
        int stepCount = 0;
        while (true) {
            String step = sysOut.getNextStep(game.board.getBoard());
            if (step.equals("Game Over!")) {
                break;
            } else {
                System.out.println("Step - " + ++stepCount + System.lineSeparator() + step);
            }
        }
    }

    /**
     * Рандомайзер клеток.
     */
    class RandCell {
        /**
         * Максимальные значения.
         */
        private int x, y;

        /**
         * @param x максимальное значение х
         * @param y максимальное значение у
         */
        RandCell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * @return рандомная клетка
         */
        Cell rand() {
            return  new Cell((int) (Math.random() * x), (int) (Math.random() * y));
        }
    }
}
