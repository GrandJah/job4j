package ru.job4j.figure;

import org.junit.Test;
import ru.job4j.board.Board;
import ru.job4j.board.Cell;
import ru.job4j.board.exception.BoardException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * junior.
 *
 * @author Atlant
 * @version 0.1
 * @since 01.06.2017
 */
public class BishopTest {
    /**
     * Test.
     */
    @Test
    public void whenBishopMoveTwoCellThenOk() {
        Board board = new Board();
        board.setFigureOnPosition(new Bishop(true), new Cell(1, 2));
        boolean result = false;
        try {
            result = board.move(new Cell(1, 2), new Cell(3, 4));
        } catch (BoardException e) {
            e.printStackTrace();
        }
        assertThat(result, is(true));
    }

}