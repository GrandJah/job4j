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
public class PaintTest {
    /**
     * Test.
     */
    @Test
    public void whenOneThenOne() {
        Paint paint = new Paint();
        assertThat(paint.piramid(1), is(String.format("^%1$s", System.lineSeparator())));
    }
    /**
     * Test.
     */
    @Test
    public void whenFiveThenPiramid() {
        Paint paint = new Paint();
        assertThat(paint.piramid(5),
                is(String.format("    ^    %1$s   ^^^   %1$s  ^^^^^  %1$s ^^^^^^^ %1$s^^^^^^^^^%1$s",
                System.lineSeparator())));
    }

}