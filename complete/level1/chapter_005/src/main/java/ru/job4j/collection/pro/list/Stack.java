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
public class Stack<E> {
    /**
     * Список значений стэка.
     */
    private LinkList<E> list = new LinkList<>();

    /**
     * @param value Добавление в стэк.
     */
    public void push(E value) {
        list.add(value);
    }

    /**
     * @return Переменная из стека
     */
    public E pop() {
        return list.removeEnd();
    }
}
