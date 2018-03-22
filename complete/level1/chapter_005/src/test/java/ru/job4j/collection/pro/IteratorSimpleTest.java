package ru.job4j.collection.pro;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 18.06.2017
 */
public class IteratorSimpleTest {

    /**
     * Test method.
     */
    @Test
    public void whenTwoOnThreeThenOk() {
        int[] ar = {1, 2, 4, 3, 4, 7};
        IteratorSimple it = new IteratorSimple(ar);
        String result = "";
        while (it.hasNext()) {
            result = String.format("%s%s", result, it.next());
        }
        assertThat(result, is("1237"));
    }

}