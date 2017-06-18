package ru.job4j.unitsort;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 12.06.2017
 */
public class UnitSortTest {

    /**
     * Массив строк подразделений.
     */
    private static final String[]  STRING_UNITS = new String[]{
            "K1\\SK1",
            "K1\\SK2",
            "K1\\SK1\\SSK1",
            "K1\\SK1\\SSK2",
            "K2",
            "K2\\SK1\\SSK1",
            "K2\\SK1\\SSK2"
    };

    /**
     * Test method.
     */
    @Test
    public void whenSortAscentThenOk() {
        UnitSort sort = new UnitSort();
        String[] result = sort.sortAscent(STRING_UNITS);
        String[] expected = {
                "K1",
                "K1\\SK1",
                "K1\\SK1\\SSK1",
                "K1\\SK1\\SSK2",
                "K1\\SK2",
                "K2",
                "K2\\SK1",
                "K2\\SK1\\SSK1",
                "K2\\SK1\\SSK2"
        };
        assertThat(result, is(expected));
    }

    /**
     * Test method.
     */
    @Test
    public void whenSortDescentThenOk() {
        UnitSort sort = new UnitSort();
        String[] result = sort.sortDescent(STRING_UNITS);
        String[] expected = {
                "K2",
                "K2\\SK1",
                "K2\\SK1\\SSK2",
                "K2\\SK1\\SSK1",
                "K1",
                "K1\\SK2",
                "K1\\SK1",
                "K1\\SK1\\SSK2",
                "K1\\SK1\\SSK1"
        };
        assertThat(result, is(expected));
    }


}
