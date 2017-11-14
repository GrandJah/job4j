package ru.job4j.orderbook;

import java.util.Iterator;
import java.util.TreeMap;

/**
 *  special TreeMap class.
 *
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 15.11.2017
 */
class IMap {
    /**
     * Порядок следования объектов.
     * true - если обратный(понижение),
     * false - усли прямой(возрастание)
     */
    private boolean reverse;
    /**
     * Карта храннения элементов.
     */
    private TreeMap<Integer, Integer> map;

    /**
     * default.
     */
    IMap() {
        init(false);
    }

    /** Определяемы порядок следования.
     * @param reverse true - если обратный(понижение),
     *                false - усли прямой(возрастание)
     */
    IMap(boolean reverse) {
        init(reverse);
    }

    /** Инициализация.
     * @param reverse порядок следования объектов.
     */
    private void init(boolean reverse) {
        this.map = new TreeMap<>();
        this.reverse = reverse;
    }

    /** Добавление записи из другой коллекции.
     * @param key ключ
     * @param map источник записи
     */
    void addEntry(Integer key, IMap map) {
        put(key, map.get(key));
    }

    /** Вспомогательная операция добавление к значению по ключу.
     * @param key ключ
     * @param adding добавляемое значение
     */
    void addValue(Integer key, int adding) {
        int amount = get(key) + adding;
        if (amount == 0) {
            this.map.remove(key);
        } else {
            this.map.replace(key, amount);
        }
    }

    /** Итератор ключей по заданному порядку.
     * @return Итератор ключей
     */
    Iterator<Integer> keyIterator() {
        return reverse ? this.map.descendingKeySet().iterator() : this.map.keySet().iterator();
    }

    /** Возврат значения по ключу.
     * @param key ключ
     * @return значение
     */
    int get(Integer key) {
        return this.map.get(key);
    }


    /** Проверка наличия ключа.
     * @param key ключ
     * @return true если существует
     */
    boolean containsKey(int key) {
        return this.map.containsKey(key);
    }

    /** внесение записи.
     * @param key ключ
     * @param value значение
     */
    void put(int key, int value) {
        this.map.put(key, value);
    }

    /** Последний ключ по заданному порядку.
     * @return ключ.
     */
    int endKey() {
        return reverse ? this.map.firstKey() : this.map.lastKey();
    }

    /** Передает свое содержимое новому элементу, при этом опустошается.
     * @return новый элемент со всем содержимым.
     */
    IMap newCopyFlip() {
        IMap flip = new IMap(this.reverse);
        TreeMap<Integer, Integer> temp = flip.map;
        flip.map = this.map;
        this.map = temp;
        return flip;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IMap{");
        Iterator<Integer> it = keyIterator();
        while (it.hasNext()) {
            Integer key = it.next();
            sb.append(key).append(":").append(get(key)).append(it.hasNext() ? "; " : "}");
        }
        return sb.toString();
    }
}
