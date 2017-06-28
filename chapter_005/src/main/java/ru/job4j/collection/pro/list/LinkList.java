package ru.job4j.collection.pro.list;

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
public class LinkList<E> implements Iterable<E> {
    /**
     * Начальный и конечный узлы.
     */
    private Node first, end;

    /**
     * @param value Добавляемое значение.
     */
    public void add(E value)  {
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

    /**
     * @param index индекс в списке
     * @return значение ячейки
     */
    public E get(int index) {
        Node current = this.first;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return (E) current.getValue();
    }

    @Override
    public Iterator<E> iterator() {
        return new ListIt<>();
    }

    /**
     * @param <E> Тип итератора
     */
    class ListIt<E> implements Iterator<E> {
        /**
         * Текущий узел.
         */
        private Node current;

        /**
         * Default constructor.
         */
        ListIt() {
            this.current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            E val = (E) current.getValue();
            current = current.getNext();
            return val;
        }
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
    public void setPrevious(Node<T> previous) {
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
    public Node<T> getPrevious() {

        return this.previous;
    }

    /**
     * Getter.
     *
     * @return next next
     */
    public Node<T> getNext() {
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


