package ru.job4j.convert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 03.06.2017
 */
public class ConvertListTest {
    /**
     * Test method.
     */
    @Test
    public void whenTwoThreeMatrixThenList() {
        int[][] array = {{1, 2}, {3, 4}, {5, 6}};
        List<Integer> expect = new ArrayList<>();
        Collections.addAll(expect, 1, 2, 3, 4, 5, 6);

        ConvertList convert = new ConvertList();
        List<Integer> result = convert.toList(array);
        assertThat(result, is(expect));
    }

    /**
     * Test method.
     */
    @Test
    public void whenListThenarrayFull() {
        int[][] expect = {{1, 2, 3}, {4, 5, 6}, {7, 0, 0}};
        List<Integer> list = new ArrayList<>();
        Collections.addAll(list, 1, 2, 3, 4, 5, 6, 7);

        ConvertList convert = new ConvertList();
        int[][] result = convert.toArray(list, 3);
        assertThat(result, is(expect));
    }

    /**
     * Test method.
     */
    @Test
    public void whenConvertListArrayToListIntegerThenOk() {
        List<int[]> list = new LinkedList<>();
        list.add(new int[]{1, 2});
        list.add(new int[]{3, 4, 5, 6});
        List<Integer> expect = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        ConvertList convert = new ConvertList();
        List<Integer> result = convert.convert(list);

        assertThat(result, is(expect));
    }
}