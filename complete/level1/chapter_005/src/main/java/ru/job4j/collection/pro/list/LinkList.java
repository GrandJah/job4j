package ru.job4j.collection.pro.list;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;

/** Динамический список на базе связного списка.
 * @author Igor Kovalkov
 * @version 0.1
 * @since 27.06.2017
 *
 * @param <E> Тип контейнера
 */
@ThreadSafe
public class LinkList<E> implements Iterable<E> {
    /** блокировка. */
    private final Object lock = new Object();

    /** Начальный и конечный узлы. */
    @GuardedBy("this.lock")
    private Node<E> first, end;

    /**
     * @param value Добавляемое значение.
     */
    public void add(E value) {
        synchronized (this.lock) {
            Node<E> val = new Node<>(value);
            if (this.end != null) {
                val.setPrevious(this.end);
                this.end.setNext(val);
                this.end = val;
            } else if (this.first != null) {
                val.setPrevious(this.first);
                this.first.setNext(val);
                this.end = val;
            } else {
                this.first = val;
            }
        }
    }

    /**
     * @param index индекс в списке
     * @return значение ячейки
     */
    public E get(int index) {
        synchronized (this.lock) {
            Node<E> current = this.first;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            return current.getValue();
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new ListIt();
    }

    /** Получение первого элемента с последующим удалением.
     * @return Последний элемент */
    E removeEnd() {
        E val = null;
        synchronized (this.lock) {
            if (this.end != null) {
                val = this.end.getValue();
                Node<E> previous = this.end.getPrevious();
                if (previous == this.first) {
                    this.end = null;
                    this.first.setNext(null);
                } else {
                    previous.setNext(null);
                    this.end = previous;
                }
            } else if (this.first != null) {
                val = this.first.getValue();
                this.first = null;
            }
        }
        return val;
    }

    /** Получение первого элемента с последующим удалением.
     * @return первый элемент. */
    E removeFirst() {
        E val = null;
        synchronized (this.lock) {
            if (this.first != null) {
                val = first.getValue();
                Node<E> next = first.getNext();
                if (next == null) {
                    this.first = null;
                } else if (next == this.end) {
                    this.first = this.end;
                    this.first.setPrevious(null);
                } else {
                    this.first = next;
                    this.first.setPrevious(null);
                }
            }
        }
        return val;
    }

    /** Итератор. */
    class ListIt implements Iterator<E> {
        /** Текущий узел. */
        private Node<E> current;

        /** Default constructor. */
        ListIt() {
            this.current = first;
        }

        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public E next() {
            E val = this.current.getValue();
            this.current = this.current.getNext();
            return val;
        }
    }


    /**
     * @param <T> Тип значения
     */
    class Node<T> {
        /**
         * Переменная.
         */
        private T value;

        /**
         * NodeLine.
         */
        private Node<T> previous, next;

        /**
         * @param previous Предыдущий
         */
        void setPrevious(Node<T> previous) {
            this.previous = previous;
        }

        /**
         * @param next Следующий
         */
        public void setNext(Node<T> next) {
            this.next = next;
        }

        /**
         * Getter.
         *
         * @return previous previous
         */
        Node<T> getPrevious() {

            return this.previous;
        }

        /**
         * Getter.
         *
         * @return next next
         */
        Node<T> getNext() {
            return this.next;
        }

        /**
         * Getter.
         *
         * @return value value
         */
        public T getValue() {
            return this.value;
        }

        /**
         * @param value переменная
         */
        Node(T value) {
            this.value = value;
        }
    }
}

