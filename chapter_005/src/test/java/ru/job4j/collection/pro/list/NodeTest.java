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
public class NodeTest {
    /**
     * Test method.
     */
    @Test
    public void whenHasLoopThenReturnTrue() {
        Node first = new Node(1);
        Node two = new Node(2);
        Node third = new Node(3);
        Node four = new Node(4);

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
        Node first = new Node(1);
        Node two = new Node(2);
        Node third = new Node(3);
        Node four = new Node(4);

        first.setNext(two);
        two.setNext(third);
        third.setNext(four);

        assertThat(first.hasCycle(first), is(false));
    }
}