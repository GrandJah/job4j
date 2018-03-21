package ru.job4j.storage;

/** Интерфейс хранилища.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 03.12.2017
 * @param <T> type
 */
public interface Storage<T> {
    /** Добавление элемента.
     * @param element элемет
     * @return true если успешно */
    boolean add(T element);

    /** Обновление элемента.
     * @param element элемет
     * @return true если успешно */
    boolean update(T element);

    /** Удаление элемента.
     * @param element элемет
     * @return true если успешно */
    boolean delete(T element);
}
