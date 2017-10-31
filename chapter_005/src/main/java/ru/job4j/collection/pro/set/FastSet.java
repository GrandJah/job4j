package ru.job4j.collection.pro.set;

import java.util.Arrays;
import java.util.Iterator;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 29.10.2017
 *
 * @param <T> T
 */
public class FastSet<T> implements Iterable<T> {
    /**
     * Массив.
     */
    private T[] arr = (T[]) new Object[100];
    /**
     * Кол-во добавленных элементов.
     */
    private int size = 0;

    /**
     * Индекс добавленных элементов.
     */
    private NodeTreeIndex indexTree = null;

    /**
     * @param element Добавляемый элемент
     */
    public void add(T element) {

        if (this.indexTree == null) {
            this.indexTree = new NodeTreeIndex(element);
            insert(element);
        }
        if (this.indexTree.insert(element)) {
            insert(element);
        }
    }

    /**
     * @param element элемент для вставки.
     */
    private void insert(T element) {
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


    /**
     * Узел Дерева Индекса.
     */
    class NodeTreeIndex {
        /**
         * Ветка меньшего значения.
         */
        private NodeTreeIndex less = null;
        /**
         * Ветка большего значения.
         */
        private NodeTreeIndex more = null;

        /**
         * Индекс узла.
         */
        private int hashIndex;

        /**
         * Элемент узла.
         */
        private T element;

        /**
         * ПодУзел для определения коллизий.
         */
        private NodeTreeIndex subNode = null;

        /**
         * @param element элемент узла
         */
        NodeTreeIndex(T element) {
            this.hashIndex = element.hashCode();
            this.element = element;
        }

        /**
         * @param element вставляемый элемент
         * @return успешность операции
         */
        boolean insert(T element) {
            int index = element.hashCode();
            boolean free;
            if (this.hashIndex == index) {
                if (element.equals(this.element)) {
                    free = false;
                } else if (subNode == null) {
                    subNode = new NodeTreeIndex(element);
                    free = true;
                } else {
                    free = subNode.insert(element);
                }
            } else if (this.hashIndex < index && this.less == null) {
                less = new NodeTreeIndex(element);
                free = true;
            } else if (this.hashIndex > index && this.more == null) {
                more = new NodeTreeIndex(element);
                free = true;
            } else {
                free = this.hashIndex < index ? this.less.insert(element) : this.more.insert(element);
            }

            return free;
        }
    }
}
