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
public class BubbleSortTest {
    /**
     * Test.
     */
    @Test
    public void whenOneElementThenNoProblem() {
        BubbleSort bubble = new BubbleSort();
        assertThat(bubble.sort(new int[]{1}), is(new int[]{1}));
    }
    /**
     * Test.
     */
    @Test
    public void whenTwoElementThenNoProblem() {
        BubbleSort bubble = new BubbleSort();
        assertThat(bubble.sort(new int[]{2, 1}), is(new int[]{1, 2}));
    }
    /**
     * Test.
     */
    @Test
    public void whenManyElementThenNoProblem() {
        BubbleSort bubble = new BubbleSort();
        assertThat(bubble.sort(new int[]{1, 5, 4, 2, 3, 1, 7, 8, 0, 5}), is(new int[]{0, 1, 1, 2, 3, 4, 5, 5, 7, 8}));
    }

}