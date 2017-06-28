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
public class StackTest {
    /**
     * Test method.
     */
    @Test
    public void whenQuickTestThenTwoOperation() {
        Stack<String> stack = new Stack<>();
        for (int i = 0; i < 10; i++) {
            stack.push(String.valueOf(i));
        }

        String result = "";

        for (int i = 0; i < 10; i++) {
            result += stack.pop();
        }
        assertThat(result, is("9876543210"));
    }
}