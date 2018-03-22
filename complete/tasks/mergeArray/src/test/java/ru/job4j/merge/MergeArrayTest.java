package ru.job4j.merge;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 02.06.2017
 */
public class MergeArrayTest {
    /**
     * Test method.
     */
    @Test
    public void whenArraysSameThenOk() {
        int[] array = {1, 3, 5, 8, 10};
        int[] result = MergeArray.merge(array, array);
        int[] expected = {1, 1, 3, 3, 5, 5, 8, 8, 10, 10};
        assertThat(result, is(expected));
    }

    /**
     * Test method.
     */
    @Test
    public void whenArraysVariantRangeThenOk() {
        int[] firstArray = {1, 3, 5, 8, 10};
        int[] secondArray = {12, 33, 53, 81, 102};
        int[] result = MergeArray.merge(firstArray, secondArray);
        int[] expected = {1, 3, 5, 8, 10, 12, 33, 53, 81, 102};
        assertThat(result, is(expected));
    }

    /**
     * Test method.
     */
    @Test
    public void whenArraysCrossRangeThenOk() {
        int[] firstArray = {1, 3, 5, 8, 10};
        int[] secondArray = {5, 6, 7, 9, 12};
        int[] result = MergeArray.merge(firstArray, secondArray);
        int[] expected = {1, 3, 5, 5, 6, 7, 8, 9, 10, 12};
        assertThat(result, is(expected));
    }

    /**
     * Test method.
     */
    @Test
    public void whenArraysCrossRangeContainsNegativeNumberThenOk() {
        int[] firstArray = {-3, -1, 0, 8, 10};
        int[] secondArray = {-2, -2, 7, 9, 12};
        int[] result = MergeArray.merge(firstArray, secondArray);
        int[] expected = {-3, -2, -2, -1, 0, 7, 8, 9, 10, 12};
        assertThat(result, is(expected));
    }
}