package ru.job4j.loop;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * job4j.
 *
 * @author AtlantIgor Kovalkov
 * @version 0.1
 * @since 22.05.2017
 */
public class CounterTest {
    /**
     * Test.
     */
    @Test
    public void whenTwoToTwentyThenSumma() {
        Counter counter = new Counter();
        assertThat(counter.add(2, 20), is(110));
    }
}