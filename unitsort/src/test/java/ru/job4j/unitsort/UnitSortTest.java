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
        sort.addAll(STRING_UNITS);
        String[] result = sort.sortAction(UnitSort.ASCENT);
        assertThat(result, is(expected));
    }

    /**
     * Test method.
     */
    @Test
    public void whenSortDescentThenOk() {
        UnitSort sort = new UnitSort();
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
        sort.addAll(STRING_UNITS);
        String[] result = sort.sortAction(UnitSort.DESCENT);
        assertThat(result, is(expected));
    }


}
