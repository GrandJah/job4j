package ru.job4j.collection.pro.list;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 27.06.2017
 */
public class ArrListTest {
    /**
     * Test method.
     */
    @Test
    public void whenManyAddThenNoErrors() {
        ArrList<Integer> list = new ArrList<>();
        for (int i = 0; i < 10000; i++) {
                list.add(i);
        }
    }

    /**
     * Test method.
     */
    @Test
    public void whenGetIndexThenReturnValue() {
        ArrList<String> list = new ArrList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(String.valueOf(i));
        }
        assertThat(list.get(444), is("444"));
    }

    /**
     * Test method.
     */
    @Test
    public void whenReturnIteratorThenIterableList() {
        ArrList<Integer> list = new ArrList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }
        Iterator<Integer> it = list.iterator();
        int result = 0;
        while (it.hasNext()) {
            it.hasNext();
            result += it.next();
        }
        assertThat(result, is(49995000));
    }
}