package ru.job4j.array;

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
public class ArrayDuplicateTest {
    /**
     * Test.
     */
    @Test
    public void whenArrayFullDuplicateThenOne() {
        ArrayDuplicate arrayDuplicate = new ArrayDuplicate();
        String[] array = {"duplicate", "duplicate", "duplicate", "duplicate", "duplicate"};
        String[] expect = {"duplicate"};
        assertThat(arrayDuplicate.remove(array), is(expect));
    }
    /**
     * Test.
     */
    @Test
    public void whenArrayDuplicateThenUnique() {
        ArrayDuplicate arrayDuplicate = new ArrayDuplicate();
        String[] array = {"Привет", "Мир", "Привет", "Супер", "Мир"};
        String[] expect = {"Привет", "Мир", "Супер"};
        assertThat(arrayDuplicate.remove(array), is(expect));
    }
}