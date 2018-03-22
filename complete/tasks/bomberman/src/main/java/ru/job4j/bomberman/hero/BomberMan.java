package ru.job4j.bomberman.hero;

import ru.job4j.bomberman.helper.Board;
import ru.job4j.bomberman.helper.Cell;
import ru.job4j.bomberman.helper.CellLock;
import ru.job4j.bomberman.helper.Entity;
import ru.job4j.bomberman.helper.InputInterface;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 24.12.2017
 */
public class BomberMan extends Hero implements InputInterface {
    /**
     * Игровое поле.
     */
    private final Board board;

    /**
     * Текущая координата.
     */
    private Cell cell;

    /**
     * @param cell старотовая координата
     * @param board игровое поле
     */
    public BomberMan(Cell cell, Board board) {
        super(Entity.Type.BomberMan);
        this.board = board;
        this.cell = cell;
        this.board.getCell(this.cell).lock(this);
    }

    /** Двигаем бомбера.
     * @param direction направление
     */
    private void go(Hero.Direction direction) {
            Cell newCell = goNewCell(this.cell, direction);
            if (newCell != null) {
                CellLock lock = this.board.getCell(newCell);
                if (lock != null) {
                    Entity entity = lock.tryLock(this);
                    if (entity != this) {
                        entity.signal(this);
                    } else {
                        this.board.getCell(this.cell).unlock();
                        this.cell = newCell;
                    }
                }
            }
        }

    @Override
    public void goUP() {
        go(Hero.Direction.Up);
    }

    @Override
    public void goDown() {
        go(Hero.Direction.Down);
    }

    @Override
    public void goLeft() {
        go(Hero.Direction.Left);
    }

    @Override
    public void goRight() {
        go(Hero.Direction.Right);
    }

    @Override
    public void actionA() {

    }

    @Override
    public void actionB() {

    }

    @Override
    public void signal(Entity entity) {
        if (entity.type() == Type.Monster) {
            System.out.println(" AAAAAAAAAAAAAAAAAAAAAA !!!");
        }
    }
}
