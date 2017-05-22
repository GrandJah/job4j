package ru.job4j.array;

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
public class TurnTest {
    /**
     * Test.
     */
    @Test
    public void whenOddElementsThenBack() {
        Turn turn = new Turn();
        assertThat(turn.back(new int[]{1, 2, 3, 4, 5}), is(new int[]{5, 4, 3, 2, 1}));
    }
    /**
     * Test.
     */
    @Test
    public void whenEvenElementsThenBack() {
        Turn turn = new Turn();
        assertThat(turn.back(new int[]{1, 2, 3, 4, 5, 6}), is(new int[]{6, 5, 4, 3, 2, 1}));
    }

}