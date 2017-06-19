package ru.job4j.collection.pro;

import java.util.Iterator;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 19.06.2017
 */
public class IteratorIterator {
    /**
     * @param it Итератор итераторов
     * @return обычный итератор
     */
    public static Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {

        return new InnerIterator<>(it);
    }

    /**  Итератор итераторов.
     * @param <E> внутренний тип итератора
     */
    private static class InnerIterator<E> implements Iterator<E> {
        /**
         * Ссылка на итератор итераторов.
         */
        private Iterator<Iterator<E>> it;
        /**
         * текущий итератор.
         */
        private Iterator<E> current;

        /**
         * @param it итератор итераторов
         */
        InnerIterator(Iterator<Iterator<E>> it) {
            this.it = it;
            this.current = it.next();
        }

        @Override
        public boolean hasNext() {
            boolean hasNext = false;
            if (current.hasNext()) {
                hasNext = true;
            } else {
                while (this.it.hasNext()) {
                    this.current = this.it.next();
                    if (current.hasNext()) {
                        hasNext = true;
                        break;
                    }
                }
            }
            return hasNext;
        }

        @Override
        public E next() {
            return this.current.next();
        }
    }
}
