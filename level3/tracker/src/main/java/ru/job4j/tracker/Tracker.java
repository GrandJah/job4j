package ru.job4j.tracker;

import java.util.List;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 20.12.2017
 */
public interface Tracker {
    /** Добавление заявок.
     * @param item заявка
     * @return заявка
     * @throws ErrorValue Ошибка в параметрах
     */
    Item add(Item item) throws ErrorValue;

    /** Получение всех заявок.
     * @return массив заявок
     */
    List<Item> findAll();

    /** Поиск по ID.
     * @param id ID заявки
     * @return заявка
     * @throws NotFound ненайденый элемент
     */
    default Item findById(String id) throws NotFound {
        return findById(Integer.parseInt(id));
    }

    /**
     * Поиск по ID.
     *
     * @param id ID заявки
     * @return заявка
     * @throws NotFound ненайденый элемент
     */
    Item findById(Integer id) throws NotFound;


    /** Обновление заявки.
     * @param item Заявка
     * @throws ErrorValue Ошибка в параметрах
     * @throws NotFound ненайденый элемент
     * @return update or not
     */
    boolean update(Item item) throws NotFound, ErrorValue;

    /** Удаление заявки.
     * @param item Заявка
     * @throws ErrorValue Ошибка в параметрах
     * @throws NotFound ненайденый элемент
     * @return delete or not
     */
    boolean delete(Item item) throws NotFound, ErrorValue;

    /** Поиск по названию заявки.
     * @param key ключ поиска
     * @return найденая заявка
     * @throws NotFound ненайденый элемент
     */
    Item findByNameFirst(String key) throws NotFound;

    /**
     * элемент не найден.
     */
    class NotFound extends Exception { }

    /**
     * ошибка параметра.
     */
    class ErrorValue extends Exception { }
}
