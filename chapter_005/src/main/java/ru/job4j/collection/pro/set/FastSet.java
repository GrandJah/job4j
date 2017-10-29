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
            this.indexTree = new NodeTreeIndex(element.hashCode());
            insert(element);
        }
        if (this.indexTree.insert(element.hashCode())) {
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
         * @param hashIndex индекс узла
         */
        NodeTreeIndex(int hashIndex) {
            this.hashIndex = hashIndex;
        }

        /**
         * @param index вставляемый индекс
         * @return успешность операции
         */
        boolean insert(int index) {
            boolean free;
            if (this.hashIndex == index) {
                free = false;
            } else if (this.hashIndex < index && this.less == null) {
                less = new NodeTreeIndex(index);
                free = true;
            } else if (this.hashIndex > index && this.more == null) {
                more = new NodeTreeIndex(index);
                free = true;
            } else {
                free = this.hashIndex < index ? this.less.insert(index) : this.more.insert(index);
            }

            return free;
        }
    }

    /**Сравнение времени вставки.
     * @param args args
     */
    public static void mzain(String[] args) {
        int amount = 10000;
        String[] arr = new String[amount];
        for (int i = 0; i < amount; i++) {
            arr[i] = Double.toString(Math.random()).substring(0, 5);
        }

        FastSet<String> fastSet = new FastSet<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < amount; i++) {
            fastSet.add(arr[i]);
        }
        long timeFast =  System.currentTimeMillis() - start;

        SimpleSet<String> simpleSet = new SimpleSet<>();
        start = System.currentTimeMillis();
        for (int i = 0; i < amount; i++) {
            simpleSet.add(arr[i]);
        }
        long timeSimple =  System.currentTimeMillis() - start;

        LinkedSet<String> linkedSet = new LinkedSet<>();
        start = System.currentTimeMillis();
        for (int i = 0; i < amount; i++) {
            linkedSet.add(arr[i]);
        }
        long timeLinked =  System.currentTimeMillis() - start;

        StringBuilder out = new StringBuilder();
        out.append("FastSet - ");
        out.append(timeFast);
        out.append(System.lineSeparator());
        out.append("SimpleSet - ");
        out.append(timeSimple);
        out.append(System.lineSeparator());
        out.append("LinkedSet - ");
        out.append(timeLinked);
        out.append(System.lineSeparator());


        System.out.println(out);
    }
}
