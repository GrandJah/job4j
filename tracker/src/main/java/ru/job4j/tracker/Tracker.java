package ru.job4j.tracker;

/**
 * junior.
 *
 * @author Igor Kovalkov aka Atlant
 * @version 0.1
 * @since 24.05.2017
 */
public class Tracker {
    /**
     * Массив заявок.
     */
    private Item[] items = new Item[100];

    /**
     * Размер заполнения.
     */
    private int size = 0;

    /** Добавление заявок.
     * @param item заявка
     * @return заявка
     */
    public Item add(Item item) {
        if (item != null) {
            this.items[this.size++] = item;
        }
        return item;
    }

    /** Поиск позиции в массиве по ID.
     * @param id ID заявки
     * @return позиция в массиве
     */
    private int findPositionId(String id) {
        int position = -1;
        for (int index = 0; index < size; index++) {
            if (id.equals(this.items[index].getId())) {
                position = index;
                break;
            }
        }
        return position;
    }

    /** Обновление заявки.
     * @param item Заявка
     */
    public void update(Item item) {
        if (item != null) {
            int position = findPositionId(item.getId());
            if (position >= 0) {
                this.items[position] = item;
            }
        }
    }

    /** Удаление заявки.
     * @param item Заявка
     */
    public void delete(Item item) {
        if (item != null) {
            int position = findPositionId(item.getId());
            if (position >= 0) {
                this.items[position] = this.items[--this.size];
            }
        }
    }

    /** Поиск по ID.
     * @param id ID заявки
     * @return заявка
     */
    public Item findById(String id) {
        Item item = null;
        int position = findPositionId(id);
        if (position >= 0) {
            item = this.items[position];
        }
        return item;
    }

    /** Получение всех заявок.
     * @return массив заявок
     */
    public Item[] getAll() {
        Item[] allItem = new Item[size];
        // Всё прверяется на null поэтому выводим всё
        System.arraycopy(this.items, 0,
                allItem, 0, size);
        return allItem;
    }

    /** Поиск по названию заявки.
     * @param key ключ поиска
     * @return найденая заявка
     */
    public Item findByName(String key) {
        Item findItem = null;
        for (Item item : items) {
            if (item.getName().equals(key)) {
                findItem = item;
                break;
            }
        }
        return findItem;
    }
}
