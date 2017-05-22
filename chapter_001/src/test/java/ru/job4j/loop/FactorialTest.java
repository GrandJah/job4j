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
public class FactorialTest {
    /**
     * Test.
     */
    @Test
    public void when5Then120() {
        Factorial factorial = new Factorial();
        assertThat(factorial.calc(5), is(120));
    }
    /**
     * Test.
     */
    @Test
    public void when10Then1200() {
        Factorial factorial = new Factorial();
        assertThat(factorial.calc(10), is(3628800));
    }

}