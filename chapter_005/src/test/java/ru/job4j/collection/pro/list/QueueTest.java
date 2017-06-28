package ru.job4j.collection.pro.list;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 28.06.2017
 */
public class QueueTest {
    /**
     * Test method.
     */
    @Test
    public void whenQuickTestThenTwoOperation() {
        Queue<String> queue = new Queue<>();
        for (int i = 0; i < 10; i++) {
            queue.enqueue(String.valueOf(i));
        }

        String result = "";

        for (int i = 0; i < 10; i++) {
            result += queue.dequeue();
        }
        assertThat(result, is("0123456789"));
    }
}