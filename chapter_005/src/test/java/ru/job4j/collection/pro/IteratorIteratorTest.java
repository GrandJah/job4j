package ru.job4j.collection.pro;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 19.06.2017
 */
public class IteratorIteratorTest {
    /** TestHelperClass.
     * @param <E> тип значений
     */
    private class TestIterator<E> implements Iterator<E> {
        /**
         * массив значений.
         */
        private E[] array;
        /**
         * текущий индекс.
         */
        private int index;

        /**
         * @param array массив значений.
         */
        private TestIterator(E[] array) {
            this.array = array;
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            return this.index < this.array.length;
        }

        @Override
        public E next() {
            return this.array[this.index++];
        }
    }


    /**
     * Test method.
     */
    @Test
    public void whenThenOk() {
        IteratorIterator itIt = new IteratorIterator();
        Iterator it1 = new TestIterator(new Integer[]{4, 2, 0, 4, 6, 4, 9});
        Iterator it2 = new TestIterator(new Integer[]{0, 9, 8, 7, 5});
        Iterator it3 = new TestIterator(new Integer[]{1, 3, 5, 6, 7, 0, 9, 8, 4});

        Iterator iterator = itIt.convert(new TestIterator(new Iterator[]{it1, it2, it3}));

        String result = "";

        while (iterator.hasNext()) {
            result = String.format("%s%s", result, iterator.next());
        }

        assertThat(result, is("420464909875135670984"));
    }

    /**
     * Test method.
     */
    @Test
    public void whenOnlyNextThenOk() {
        IteratorIterator itIt = new IteratorIterator();
        Iterator it1 = new TestIterator(new Integer[]{4, 2, 0, 4, 6, 4, 9});
        Iterator it2 = new TestIterator(new Integer[]{0, 9, 8, 7, 5});
        Iterator it3 = new TestIterator(new Integer[]{1, 3, 5, 6, 7, 0, 9, 8, 4});

        Iterator iterator = itIt.convert(new TestIterator(new Iterator[]{it1, it2, it3}));

        String result = "";

        for (int i = 0; i < 21; i++) {
            result = String.format("%s%s", result, iterator.next());
        }

        assertThat(result, is("420464909875135670984"));
    }
}