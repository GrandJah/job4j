package ru.job4j.collection.pro.set;

import java.util.Iterator;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 21.08.2017
 *
 * @param <T> T
 *
 */
public class LinkedSet<T> implements Iterable<T> {

    /**
     * Список.
     */
    private NodeItem start = null;

    /**
     * @param element Добавляемый элемент
     */
    public void add(T element) {
        if (!checkDuplicate(element)) {
            NodeItem newItem = new NodeItem();
            newItem.setValue(element);
            newItem.setNext(this.start);
            this.start = newItem;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new SetIterator<>();
    }

    /** Проверка дубликатов.
     * @param element проверяемый элемент
     * @return наличие дубликата
     */
    boolean checkDuplicate(T element) {
        boolean check = false;
        for (T t : this) {
            if (t.equals(element)) {
                check = true;
            }
        }
        return check;
    }


    /**
     * @param <E> <E>
     */
    private class SetIterator<E> implements Iterator<E> {
        /**
         * Текущее значение итератора.
         */
        private NodeItem next;

        /**
         * Default Constructor.
         */
        private SetIterator() {
            this.next = start;
        }

        @Override
        public boolean hasNext() {
            return this.next != null;
        }

        @Override
        public E next() {
            E ret = (E) next.getValue();
            this.next = next.getNext();
            return ret;
        }
    }




    /**
     * Ячейка хранения.
     */
    private class NodeItem {

        /**
         * Значение ячейки.
         */
        private T value;

        /**
         * Следующий элемент.
         */
        private NodeItem next = null;


        /**
         * Getter.
         *
         * @return next next
         */
        public NodeItem getNext() {
            return this.next;
        }

        /**
         * Getter.
         *
         * @return value value
         */
        T getValue() {
            return this.value;
        }

        /**
         * Setter.
         *
         * @param value value
         */
        void setValue(T value) {
            this.value = value;
        }

        /**
         * Setter.
         *
         * @param next next
         */
        public void setNext(NodeItem next) {
            this.next = next;
        }
    }
}
