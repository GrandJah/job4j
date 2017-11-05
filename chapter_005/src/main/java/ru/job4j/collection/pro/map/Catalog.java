package ru.job4j.collection.pro.map;

import java.util.Iterator;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 05.11.2017
 * @param <T> key
 * @param <V> value
 */
public class Catalog<T, V> implements Iterable<Entry<T, V>> {

    /**
     * Массив справочника.
     */
    private Entry<T, V>[] catalog;

    /** Конструктор справочника.
     * @param size размер создаваемого справочника.
     */
    public Catalog(int size) {
        this.catalog = new Entry[size];
    }

    /** Вставка элемента в справочник.
     * @param key ключ для вставки
     * @param value вставляемый элемент
     * @return успешность вставки, false если элемент уже есть
     */
    public boolean insert(T key, V value) {
        int position = findIndex(key);
        boolean success = false;
            if (this.catalog[position] == null) {
                this.catalog[position] = new Entry<>(key, value);
                success = true;
            }
        return success;
    }

    /** Возврат элемента по ключу.
     * @param key ключ элемента
     * @return элемент по ключу
     */
    public V get(T key) {
        Entry<T, V> entry = this.catalog[findIndex(key)];
        return entry == null ? null : entry.getValue();
    }

    /**
     * @param key ключ по которому удаляют элемент.
     * @return успешность, false если элемента не было.
     */
    public boolean delete(T key) {
        int position = findIndex(key);
        boolean success = false;
        if (this.catalog[position] != null) {
            this.catalog[position] = null;
            success = true;
        }
        return success;
    }

    /** Поиск места в массиве по хэшу.
     * @param key ключ по которому ищем место.
     * @return позиция в массиве для данного ключа.
     */
    private int findIndex(T key) {
        return key.hashCode() % this.catalog.length;
    }

    @Override
    public Iterator<Entry<T, V>> iterator() {
        return new CatalogIterator();
    }

    /**
     * Итератор для справочника.
     */
    private class CatalogIterator implements Iterator<Entry<T, V>> {

        /**
         * Индек текущего элемента, инициализируется в конструкторе через searchNext.
         */
        private int index;

        /**
         * Конструктор со скрытой инициализацией index.
         */
        CatalogIterator() {
            this.index = -1;
            searchNext();
        }

        @Override
        public boolean hasNext() {
            return catalog.length > this.index && catalog[this.index] != null;
        }

        @Override
        public Entry<T, V> next() {
            Entry<T, V> next;
            next = catalog[this.index];
            searchNext();
            return next;
        }

        /**
         * Поиск следующего элемента в массиве.
         */
        void searchNext() {
            while (++this.index < catalog.length) {
                if (catalog[this.index] != null) {
                    return;
                }
            }
        }
    }
}

/**
 * Запись в справочнике.
 * @param <T> key
 * @param <V> value
 */
class Entry<T, V> {
    /**
     * Ключ.
     */
    private T key;
    /**
     * Значение.
     */
    private V value;

    /** Default Constructor.
     * @param key key
     * @param value value
     */
    Entry(T key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Getter.
     *
     * @return key key
     */
    public T getKey() {
        return this.key;
    }

    /**
     * Getter.
     *
     * @return value value
     */
    public V getValue() {
        return this.value;
    }
}
