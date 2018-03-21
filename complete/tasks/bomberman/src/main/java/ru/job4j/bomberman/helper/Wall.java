package ru.job4j.bomberman.helper;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 24.12.2017
 */
public class Wall extends Entity {
    /**
     * @param cell старотовая координата
     * @param board board
     */
    public Wall(Cell cell, Board board) {
        super(Type.Wall);
        board.getCell(cell).lock(this);
    }
}
