package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.ListIterator;
import ru.job4j.tracker.expire.Item;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 24.05.2017
 */
public class TrackerArray implements Tracker {
    private static int genId = 0;
    /**
     * Массив заявок.
     */
    private ArrayList<Item> items = new ArrayList<>();

    /**
     * @param item item
     * @throws ErrorValue Ошибка в параметрах
     */
    private void validate(Item item) throws ErrorValue {
        if (item == null) {
            throw new ErrorValue();
        }
    }

    /** Поиск позиции в массиве по ID.
     * @param id ID заявки
     * @return итератор в установленном месте
     * @throws NotFound ненайденый элемент
     */
    private ListIterator<Item> iteratorFindId(Integer id) throws NotFound {
        ListIterator<Item> it = this.items.listIterator();
        while (it.hasNext()) {
            if (id.equals(it.next().getId())) {
                it.previous();
                return it;
            }
        }
        throw new NotFound();
    }

    @Override
    public void update(Item item) throws NotFound, ErrorValue {
        validate(item);
        iteratorFindId(item.getId()).set(item);
    }

    @Override
    public Item add(Item item) throws ErrorValue {
        if (item.getId() == null) {
            item.setId(genId++);
        } else {
            throw new ErrorValue();
        }
        validate(item);
        this.items.add(item);
        return item;
    }

    @Override
    public void delete(Item item) throws NotFound, ErrorValue {
        validate(item);
        iteratorFindId(item.getId()).remove();
    }

    @Override
    public Item findById(Integer id) throws NotFound {
        return iteratorFindId(id).next();
    }

    @Override
    public Item findById(String id) throws NotFound {
        return findById(Integer.valueOf(id));
    }

    @Override
    public Item findByName(String key) throws NotFound {
        for (Item item : this.items) {
            if (item.getName().equals(key)) {
                return item;
            }
        }
        throw new NotFound();
    }

    @Override
    public ArrayList<Item> getAll() {
        return new ArrayList<Item>() {
            {
                addAll(items);
            }
        };
    }

}
