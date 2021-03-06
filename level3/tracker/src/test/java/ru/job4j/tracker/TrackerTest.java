package ru.job4j.tracker;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * junior.
 *
 * @author Igor Kovalkov aka Atlant
 * @version 0.1
 * @since 24.05.2017
 */
public abstract class TrackerTest {

    /**
     * Тестируемый трекер.
     */
    private final Tracker tracker;

    /**
     * Установка тестируемого трекера.
     *
     * @param tracker tracker
     */
    protected TrackerTest(Tracker tracker) {
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
        assertEquals(3, tracker.findAll().size());
    }

    /**
     * Test.
     * @throws Tracker.ErrorValue ошибка параметра
     */
    @Test
    public void whenAddItemThenTrackerHasItem() throws Tracker.ErrorValue {
        Item item = new Item("Заявка");
        item = this.tracker.add(item);
        Item result = tracker.findAll().get(0);
        assertEquals(item, result);
    }

    /**
     * Test.
     * @throws Tracker.NotFound ненайден элемент
     * @throws Tracker.ErrorValue ошибка параметра
     */
    @Test
    public void whenUpdateItemThenItemUpdate() throws Tracker.NotFound, Tracker.ErrorValue {
        Item item = new Item("Заявка");
        item = this.tracker.add(item);
        item.setName("Редактированная");
        this.tracker.update(item);
        assertEquals("Редактированная", tracker.findAll().get(0).getName());
    }

    /**
     * Test.
     * @throws Tracker.NotFound ненайден элемент
     * @throws Tracker.ErrorValue ошибка параметра
     */
    @Test
    public void whenDeleteItemThenTrackerNoHasItem() throws Tracker.NotFound, Tracker.ErrorValue {
        Item item = new Item("Заявка");
        item = this.tracker.add(item);
        this.tracker.delete(item);
        int result = this.tracker.findAll().size();
        assertEquals(0, result);
    }

    /**
     * Test.
     * @throws Tracker.NotFound ненайден элемент
     * @throws Tracker.ErrorValue ошибка параметра
     */
    @Test
    public void whenFindByIdThenReturnItemId() throws Tracker.NotFound, Tracker.ErrorValue {
        Item item = new Item("Заявка");
        item = this.tracker.add(item);
        Item result = tracker.findById(item.getId());
        assertEquals(item, result);
    }

    /**
     * Test.
     * @throws Tracker.NotFound ненайден элемент
     * @throws Tracker.ErrorValue ошибка параметра
     */
    @Test
    public void whenFindByNameThenReturnItemName() throws Tracker.NotFound, Tracker.ErrorValue {
        Item item = new Item("Заявка");
        item = this.tracker.add(item);
        Item result = tracker.findByNameFirst("Заявка");
        assertEquals(item, result);
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
        this.tracker.delete(this.tracker.findByNameFirst("Третий"));
        this.tracker.delete(this.tracker.findByNameFirst("Первый"));
        StringBuilder result = new StringBuilder();
        for (Item item : this.tracker.findAll()) {
            result.append(item.getName());
            result.append(", ");
        }
        String expectOrder = "Второй, Четвертый, Пятый, ";
        assertEquals(expectOrder, result.toString());
        this.tracker.delete(this.tracker.findByNameFirst("Второй"));
        this.tracker.delete(this.tracker.findByNameFirst("Пятый"));
        this.tracker.delete(this.tracker.findByNameFirst("Четвертый"));
        result = new StringBuilder();
        for (Item item : this.tracker.findAll()) {
            result.append(item.getName());
            result.append(", ");
        }
        assertEquals("", result.toString());
    }
}
