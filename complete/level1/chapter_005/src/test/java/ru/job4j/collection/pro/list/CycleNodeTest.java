package ru.job4j.collection.pro.list;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 30.06.2017
 */
public class CycleNodeTest {
    /**
     * Test method.
     */
    @Test
    public void whenHasLoopThenReturnTrue() {
        CycleNode<Integer> first = new CycleNode<>(1);
        CycleNode<Integer> two = new CycleNode<>(2);
        CycleNode<Integer> third = new CycleNode<>(3);
        CycleNode<Integer> four = new CycleNode<>(4);

        first.setNext(two);
        two.setNext(third);
        third.setNext(four);
        four.setNext(first);

        assertThat(first.hasCycle(first), is(true));
    }

    /**
     * Test method.
     */
    @Test
    public void whenNotLoopThenReturnFalse() {
        CycleNode<Integer> first = new CycleNode<>(1);
        CycleNode<Integer> two = new CycleNode<>(2);
        CycleNode<Integer> third = new CycleNode<>(3);
        CycleNode<Integer> four = new CycleNode<>(4);

        first.setNext(two);
        two.setNext(third);
        third.setNext(four);

        assertThat(first.hasCycle(first), is(false));
    }
}