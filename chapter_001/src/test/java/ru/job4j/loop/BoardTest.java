package ru.job4j.loop;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * job4j.
 *
 * @author Igor Kovalkov aka Atlant
 * @version 0.1
 * @since 22.05.2017
 */
public class BoardTest {
    /**
     * Test.
     */
    @Test
    public void whenTwoThenLittleBoard() {
        Board board = new Board();
        assertThat(board.paint(2, 2), is(String.format("X %1$s X%1$s", System.lineSeparator())));
    }
    /**
     * Test.
     */
    @Test
    public void whenFourToFiveThenBigBoard() {
        Board board = new Board();
        assertThat(board.paint(4, 5), is(String.format("X X %1$s X X%1$sX X %1$s X X%1$sX X %1$s", System.lineSeparator())));
    }
}