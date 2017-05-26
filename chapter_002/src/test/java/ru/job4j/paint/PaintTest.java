package ru.job4j.paint;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 26.05.2017
 */
public class PaintTest {
    /**
     * Test.
     */
    @Test
    public void whenDrawSquareThenOutSquare() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        new Paint().draw(new Square());
        assertThat(out.toString(), is(String.format("XXX%1$sXXX%1$sXXX%1$s%1$s", System.lineSeparator())));
    }
    /**
     * Test.
     */
    @Test
    public void whenDrawTriangleThenOutTriangle() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        new Paint().draw(new Triangle());
        assertThat(out.toString(), is(String.format("X%1$sXX%1$sXXX%1$s%1$s", System.lineSeparator())));
    }

}