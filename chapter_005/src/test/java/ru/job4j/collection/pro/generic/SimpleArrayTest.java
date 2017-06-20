package ru.job4j.collection.pro.generic;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 20.06.2017
 */
public class SimpleArrayTest {
    /**
     * Test method.
     */
    @Test
    public void whenEmptyArrayThenEmptyString() {
        SimpleArray<Integer> simpleArray = new SimpleArray<>(15);
        assertThat(simpleArray.toString(), is("Array : {}"));
    }

    /**
     * Test method.
     */
    @Test
    public void whenAddThenOk() {
        SimpleArray<Integer> simpleArray = new SimpleArray<>(15);
        simpleArray.add(34);
        simpleArray.add(45);
        simpleArray.add(78);
        assertThat(simpleArray.toString(), is("Array : {34, 45, 78}"));
    }

    /**
     * Test method.
     */
    @Test
    public void whenUpdateThenOk() {
        SimpleArray<String> simpleArray = new SimpleArray<>(15);
        simpleArray.add("string");
        simpleArray.update("test", 0);
        assertThat(simpleArray.toString(), is("Array : {test}"));

    }

    /**
     * Test method.
     */
    @Test
    public void whenDeleteThenOk() {
        SimpleArray<Integer> simpleArray = new SimpleArray<>(15);
        simpleArray.add(34);
        simpleArray.add(45);
        simpleArray.add(78);
        simpleArray.delete(1);
        assertThat(simpleArray.toString(), is("Array : {34, 78}"));
    }

    /**
     * Test method.
     */
    @Test
    public void whenGetThenOk() {
        SimpleArray<Integer> simpleArray = new SimpleArray<>(15);
        simpleArray.add(34);
        simpleArray.add(45);
        simpleArray.add(78);
        assertThat(simpleArray.get(2), is(78));
    }

}