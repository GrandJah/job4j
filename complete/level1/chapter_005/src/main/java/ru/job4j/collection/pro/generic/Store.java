package ru.job4j.collection.pro.generic;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 22.06.2017
 *
 * @param <T> Тип контейнера
 */
public interface Store<T extends Base> {
    /**
     * @param t Добавляемый элемент
     * @return добавленный элемент
     */
    T add(T t);

    /**
     * @param t Обновляемый элемент
     * @param upd Обновленный элемент
     * @return Обновленный элемент
     */
    T update(T t, T upd);

    /**
     * @param t Удаляемый элемент
     */
    void remove(T t);
}
