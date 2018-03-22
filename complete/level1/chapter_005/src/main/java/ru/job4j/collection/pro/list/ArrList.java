package ru.job4j.collection.pro.list;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Arrays;
import java.util.Iterator;

/** Динамический список на базе массива.
 * @author Igor Kovalkov
 * @version 0.1
 * @since 27.06.2017
 *
 * @param <E> Тип контейнера
 */
@ThreadSafe
public class ArrList<E> implements Iterable<E> {
    /** блокировка. */
    private final Object lock = new Object();

    /** контейнер списка. */
    @GuardedBy("this.lock")
    private E[] container;

    /** Размер списка. */
    @GuardedBy("this.lock")
    private int size = 0;

    /** Default constructor. */
    ArrList() {
        this.container = (E[]) new Object[100];
    }

    /** @param value Добавляемое значение */
    public void add(E value) {
        synchronized (this.lock) {
            if (this.container.length == this.size) {
                this.container = Arrays.copyOf(this.container,
                        (int) (this.container.length * 1.5));
            }
            this.container[size++] = value;
        }
    }

    /** @param index Индекс
     * @return Значение элемента. */
    E get(int index) {
        synchronized (this.lock) {
            return this.container[index];
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new ListIt();
    }

    /** Итератор. */
    class ListIt implements Iterator<E> {
        /** Индекс итератора. */
        private int index = 0;

        @Override
        public boolean hasNext() {
            synchronized (lock) {
                return this.index < size;
            }
        }

        @Override
        public E next() {
            synchronized (lock) {
                return container[this.index++];
            }
        }
    }
}
