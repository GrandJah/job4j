package ru.job4j.test001;

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
public class StringContainsTest {
    /**
     * Test.
     */
    @Test
    public void whenStringContainsThenTrue() {
        StringContains contains = new StringContains();
        assertThat(contains.contains("string for search", "for"), is(true));
    }
    /**
     * Test.
     */
    @Test
    public void whenStringContainsLeftThenTrue() {
        StringContains contains = new StringContains();
        assertThat(contains.contains("string for search", "string"), is(true));
    }
    /**
     * Test.
     */
    @Test
    public void whenStringContainsRightThenTrue() {
        StringContains contains = new StringContains();
        assertThat(contains.contains("string for search", "search"), is(true));
    }
    /**
     * Test.
     */
    @Test
    public void whenStringNotContainsThenFalse() {
        StringContains contains = new StringContains();
        assertThat(contains.contains("string for search", "one"), is(false));
    }

}