package ru.job4j.collection.pro.list;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 28.06.2017
 *
 * @param <E> Тип контейнера
 */
public class Queue<E> {
    /**
     * Список очереди.
     */
    private LinkList<E> list = new LinkList<>();

    /**
     * @param value Добавляемое значение
     */
    public void enqueue(E value) {
        list.add(value);
    }

    /**
     * @return Значение из начала очереди
     */
    public E dequeue() {
        return list.removeFirst();
    }
}
