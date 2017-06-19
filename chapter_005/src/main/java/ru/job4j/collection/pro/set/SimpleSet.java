package ru.job4j.collection.pro.set;

import java.util.Arrays;
import java.util.Iterator;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 01.07.2017
 *
 * @param <T> T
 */
public class SimpleSet<T> implements Iterable<T> {

    /**
     * Массив.
     */
    private T[] arr = (T[]) new Object[100];
    /**
     * Кол-во добавленных элементов.
     */
    private int size = 0;

    /**
     * @param element Добавляемый элемент
     */
    public void add(T element) {
        for (int i = 0; i < this.size; i++) {
            if (element.equals(this.arr[i])) {
                return;
            }
        }
        if (this.size == this.arr.length) {
           this.arr = Arrays.copyOf(this.arr, (int) (this.arr.length * 1.5 + 1));
        }
        this.arr[size++] = element;
    }

    @Override
    public Iterator<T> iterator() {
        return new SetIterator<>();
    }

    /**
     * @param <E> <E>
     */
    class SetIterator<E> implements Iterator<E> {
        /**
         * Текущий индекс итератора.
         */
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return this.currentIndex < size;
        }

        @Override
        public E next() {
            return (E) arr[this.currentIndex++];
        }
    }
}