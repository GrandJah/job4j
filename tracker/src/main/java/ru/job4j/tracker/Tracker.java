package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 24.05.2017
 */
public class Tracker {
    /**
     * Массив заявок.
     */
    private ArrayList<Item> items = new ArrayList<>();

    /**
     * @param item item
     * @throws ErrorValue ошибка параметра
     */
    private void validate(Item item) throws ErrorValue {
        if (item == null) {
            throw new ErrorValue();
        }
    }

    /** Поиск позиции в массиве по ID.
     * @param id ID заявки
     * @return итератор в установленном месте
     * @throws NotFound элемент не найден
     */
    private ListIterator<Item> iteratorFindId(String id) throws NotFound {
        ListIterator<Item> it = this.items.listIterator();
        while (it.hasNext()) {
            if (id.equals(it.next().getId())) {
                it.previous();
                return it;
            }
        }
        throw new NotFound();
    }

    /** Обновление заявки.
     * @param item Заявка
     * @throws NotFound элемент не найден
     * @throws ErrorValue ошибка параметра
     */
    public void update(Item item) throws ErrorValue, NotFound {
        if (item != null) {
            iteratorFindId(item.getId()).set(item);
        } else {
            throw new ErrorValue();
        }
    }

    /** Добавление заявок.
     * @param item заявка
     * @return заявка
     * @throws ErrorValue ошибка параметра
     */
    public Item add(Item item) throws ErrorValue {
        validate(item);
        this.items.add(item);
        return item;
    }

    /** Удаление заявки.
     * @param item Заявка
     * @throws NotFound элемент не найден
     * @throws ErrorValue ошибка параметра
     */
    public void delete(Item item) throws ErrorValue, NotFound {
        validate(item);
        iteratorFindId(item.getId()).remove();
    }

    /** Поиск по ID.
     * @param id ID заявки
     * @return заявка
     * @throws NotFound элемент не найден
     */
    public Item findById(String id) throws NotFound {
        return iteratorFindId(id).next();
    }

    /** Поиск по названию заявки.
     * @param key ключ поиска
     * @return найденая заявка
     * @throws NotFound элемент не найден
     */
    public Item findByName(String key) throws NotFound {
        for (Item item : this.items) {
            if (item.getName().equals(key)) {
                return item;
            }
        }
        throw new NotFound();
    }

    /** Получение всех заявок.
     * @return массив заявок
     */
    public ArrayList<Item> getAll() {
        return (ArrayList<Item>) this.items.clone();
    }

    /**
     * элемент не найден.
     */
    public class NotFound extends Exception { }

    /**
     * ошибка параметра.
     */
    public class ErrorValue extends Exception { }
}
