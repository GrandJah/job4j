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
     * Test.
     * @throws Tracker.ErrorValue Ошибка в воде данных
     */
    @Test
    public void whenGetAllThenTrackerReturnAll() throws Tracker.ErrorValue {
        Tracker tracker = new Tracker();
        tracker.add(new Item("Заявка1"));
        tracker.add(new Item("Заявка2"));
        tracker.add(new Item("Заявка3"));
        assertThat(tracker.getAll().size(), is(3));
    }

    /**
     * Test.
     * @throws Tracker.ErrorValue Ошибка в воде данных
     */
    @Test
    public void whenAddItemThenTrackerHasItem() throws Tracker.ErrorValue {
        Tracker tracker = new Tracker();
        Item item = new Item("Заявка");
        tracker.add(item);
        assertThat(tracker.getAll().get(0), is(item));
    }

    /**
     * Test.
     * @throws Tracker.ErrorValue Ошибка в воде данных
     * @throws Tracker.NotFound Объект не найден
     */
    @Test
    public void whenUpdateItemThenItemUpdate() throws Tracker.NotFound, Tracker.ErrorValue {
        Tracker tracker = new Tracker();
        Item item = new Item("Заявка");
        tracker.add(item);
        item.setName("Редактированная");
        tracker.update(item);
        assertThat(tracker.getAll().get(0).getName(),
                is("Редактированная"));
    }

    /**
     * Test.
     * @throws Tracker.ErrorValue Ошибка в воде данных
     * @throws Tracker.NotFound Объект не найден
     */
    @Test
    public void whenDeleteItemThenTrackerNoHasItem() throws Tracker.NotFound, Tracker.ErrorValue {
        Tracker tracker = new Tracker();
        Item item = new Item("Заявка");
        tracker.add(item);
        tracker.delete(item);
        assertThat(tracker.getAll().size(), is(0));
    }

    /**
     * Test.
     * @throws Tracker.ErrorValue Ошибка в воде данных
     * @throws Tracker.NotFound Объект не найден
     */
    @Test
    public void whenFindByIdThenReturnItemId() throws Tracker.NotFound, Tracker.ErrorValue {
        Tracker tracker = new Tracker();
        Item item = new Item("Заявка");
        tracker.add(item);
        assertThat(tracker.findById(item.getId()), is(item));
    }

    /**
     * Test.
     * @throws Tracker.ErrorValue Ошибка в воде данных
     * @throws Tracker.NotFound Объект не найден
     */
    @Test
    public void whenFindByNameThenReturnItemName() throws Tracker.NotFound, Tracker.ErrorValue {
        Tracker tracker = new Tracker();
        Item item = new Item("Заявка");
        tracker.add(item);
        assertThat(tracker.findByName("Заявка"), is(item));
    }

    /**
     * Проверка сохранения порядка при удалении элемента.
     * @throws Tracker.ErrorValue Ошибка в воде данных
     * @throws Tracker.NotFound Объект не найден
     */
    @Test
    public void whenDeleteItemThenSaveOrder() throws Tracker.ErrorValue, Tracker.NotFound {
        Tracker tracker = new Tracker();
        tracker.add(new Item("Первый"));
        tracker.add(new Item("Второй"));
        tracker.add(new Item("Третий"));
        tracker.add(new Item("Четвертый"));
        tracker.add(new Item("Пятый"));
        tracker.delete(tracker.findByName("Третий"));
        tracker.delete(tracker.findByName("Первый"));
        StringBuilder result = new StringBuilder();
        for (Item item : tracker.getAll()) {
            result.append(item.getName());
            result.append(", ");
        }
        String expectOrder = "Второй, Четвертый, Пятый, ";
        assertThat(result.toString(), is(expectOrder));
    }
}