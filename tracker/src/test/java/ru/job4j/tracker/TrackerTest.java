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
     */
    @Test
    public void whenGetAllThenTrackerReturnAll() {
        Tracker tracker = new Tracker();
        tracker.add(new Item("Заявка1"));
        tracker.add(new Item("Заявка2"));
        tracker.add(new Item("Заявка3"));
        assertThat(tracker.getAll().length, is(3));
    }

    /**
     * Test.
     */
    @Test
    public void whenAddItemThenTrackerHasItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("Заявка");
        tracker.add(item);
        assertThat(tracker.getAll()[0], is(item));
    }

    /**
     * Test.
     */
    @Test
    public void whenUpdateItemThenItemUpdate() {
        Tracker tracker = new Tracker();
        Item item = new Item("Заявка");
        tracker.add(item);
        item.setName("Редактированная");
        tracker.update(item);
        assertThat(tracker.getAll()[0].getName(),
                is("Редактированная"));
    }

    /**
     * Test.
     */
    @Test
    public void whenDeleteItemThenTrackerNoHasItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("Заявка");
        tracker.add(item);
        tracker.delete(item);
        assertThat(tracker.getAll().length, is(0));
    }

    /**
     * Test.
     */
    @Test
    public void whenFindByIdThenReturnItemId() {
        Tracker tracker = new Tracker();
        Item item = new Item("Заявка");
        tracker.add(item);
        assertThat(tracker.findById(item.getId()), is(item));
    }

    /**
     * Test.
     */
    @Test
    public void whenFindByNameThenReturnItemName() {
        Tracker tracker = new Tracker();
        Item item = new Item("Заявка");
        tracker.add(item);
        assertThat(tracker.findByName("Заявка"), is(item));
    }

}