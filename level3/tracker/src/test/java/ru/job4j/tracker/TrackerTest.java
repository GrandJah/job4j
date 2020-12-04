package ru.job4j.tracker;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * junior.
 *
 * @author Igor Kovalkov aka Atlant
 * @version 0.1
 * @since 24.05.2017
 */
public class TrackerTest {

    /**
     * Тестируемый трекер.
     */
    private Tracker tracker = new TrackerArray();

    /** Установка тестируемого трекера.
     * @param tracker tracker
     */
    void setTracker(Tracker tracker) {
        this.tracker = tracker;
    }

    /**
     * Test.
     * @throws Tracker.ErrorValue ошибка параметра
     */
    @Test
    public void whenGetAllThenTrackerReturnAll() throws Tracker.ErrorValue {
        this.tracker.add(new Item("Заявка1"));
        this.tracker.add(new Item("Заявка2"));
        this.tracker.add(new Item("Заявка3"));
        assertThat(tracker.getAll().size(), is(3));
    }

    /**
     * Test.
     * @throws Tracker.ErrorValue ошибка параметра
     */
    @Test
    public void whenAddItemThenTrackerHasItem() throws Tracker.ErrorValue {
        Item item = new Item("Заявка");
        this.tracker.add(item);
        Item result = tracker.getAll().get(0);
        assertThat(result, is(item));
    }

    /**
     * Test.
     * @throws Tracker.NotFound ненайден элемент
     * @throws Tracker.ErrorValue ошибка параметра
     */
    @Test
    public void whenUpdateItemThenItemUpdate() throws Tracker.NotFound, Tracker.ErrorValue {
        Item item = new Item("Заявка");
        this.tracker.add(item);
        item.setName("Редактированная");
        this.tracker.update(item);
        assertThat(tracker.getAll().get(0).getName(),
                is("Редактированная"));
    }

    /**
     * Test.
     * @throws Tracker.NotFound ненайден элемент
     * @throws Tracker.ErrorValue ошибка параметра
     */
    @Test
    public void whenDeleteItemThenTrackerNoHasItem() throws Tracker.NotFound, Tracker.ErrorValue {
        Item item = new Item("Заявка");
        this.tracker.add(item);
        this.tracker.delete(item);
        int result = this.tracker.getAll().size();
        assertThat(result, is(0));
    }

    /**
     * Test.
     * @throws Tracker.NotFound ненайден элемент
     * @throws Tracker.ErrorValue ошибка параметра
     */
    @Test
    public void whenFindByIdThenReturnItemId() throws Tracker.NotFound, Tracker.ErrorValue {
        Item item = new Item("Заявка");
        this.tracker.add(item);
        Item result = tracker.findById(item.getId());
        assertThat(result, is(item));
    }

    /**
     * Test.
     * @throws Tracker.NotFound ненайден элемент
     * @throws Tracker.ErrorValue ошибка параметра
     */
    @Test
    public void whenFindByNameThenReturnItemName() throws Tracker.NotFound, Tracker.ErrorValue {
        Item item = new Item("Заявка");
        this.tracker.add(item);
        Item result = tracker.findByName("Заявка");
        assertThat(result, is(item));
    }

    /**
     * Проверка сохранения порядка при удалении элемента.
     * @throws Tracker.NotFound ненайден элемент
     * @throws Tracker.ErrorValue ошибка параметра
     */
    @Test
    public void whenDeleteItemThenSaveOrder() throws Tracker.NotFound, Tracker.ErrorValue {
        this.tracker.add(new Item("Первый"));
        this.tracker.add(new Item("Второй"));
        this.tracker.add(new Item("Третий"));
        this.tracker.add(new Item("Четвертый"));
        this.tracker.add(new Item("Пятый"));
        this.tracker.delete(this.tracker.findByName("Третий"));
        this.tracker.delete(this.tracker.findByName("Первый"));
        StringBuilder result = new StringBuilder();
        for (Item item : this.tracker.getAll()) {
            result.append(item.getName());
            result.append(", ");
        }
        String expectOrder = "Второй, Четвертый, Пятый, ";
        assertThat(result.toString(), is(expectOrder));
    }
}