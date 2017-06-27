package ru.job4j.collection.pro.list;

import java.util.Arrays;
import java.util.Iterator;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 27.06.2017
 *
 * @param <E> Тип контейнера
 */
public class List<E> implements Iterable<E> {
    /**
     * контейнер списка.
     */
    private Object[] container;

    /**
     * Размер списка.
     */
    private int size = 0;

    /**
     * Default constructor.
     */
    public List() {
        this.container = new Object[100];
    }

    /**
     * @param value Добавляемое значение
     */
    public void add(E value) {
        if (this.container.length == this.size) {
            this.container = Arrays.copyOf(this.container,
                    (int) (this.container.length * 1.5));
        }
        this.container[size++] = value;
    }

    /**
     * @param index Индекс
     * @return Значение элемента.
     */
    public E get(int index) {
        return (E) this.container[index];
    }

    @Override
    public Iterator<E> iterator() {
        return new ListIt<>();
    }

    /** Итератор класса.
     * @param <E> Тип значений
     */
    class ListIt<E> implements Iterator<E> {
        /**
         * Индекс итератора.
         */
        private int index = 0;

        @Override
        public boolean hasNext() {
            return this.index < size;
        }

        @Override
        public E next() {
            return (E) container[this.index++];
        }
    }
}
