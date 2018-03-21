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
public class RotateArrayTest {
    /**
     * Test.
     */
    @Test
    public void whenArrayThreeThenRotate() {
        RotateArray rotate = new RotateArray();
        assertThat(rotate.rotate(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}),
                is(new int[][]{{7, 4, 1}, {8, 5, 2}, {9, 6, 3}}));
    }
    /**
     * Test.
     */
    @Test
    public void whenArrayTwoThenRotate() {
        RotateArray rotate = new RotateArray();
        assertThat(rotate.rotate(new int[][]{{1, 2}, {3, 4}}),
                is(new int[][]{{3, 1}, {4, 2}}));
    }
    /**
     * Test.
     */
    @Test
    public void whenArrayNMThenRotate() {
        RotateArray rotate = new RotateArray();
        assertThat(rotate.rotate(new int[][]{{1, 2, 3}, {4, 5, 6}}),
                is(new int[][]{{4, 1}, {5, 2}, {6, 3}}));
    }
}