package ru.job4j.start;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ru.job4j.tracker.Tracker;
import ru.job4j.tracker.Item;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 27.05.2017
 */
public abstract class StubInputTest {
    /**
     * Тестовый трекер.
     */
    private final Tracker tracker;

    /**
     * Тестовая заявка №3.
     */
    private Item itemTest;

    /**
     * Установка тестируемого трекера.
     *
     * @param tracker tracker
     */
    public StubInputTest(Tracker tracker) {
        this.tracker = tracker;
        fillTracker();
    }

    /** Заполняем трекер.
     */
    private void fillTracker() {
        try {
            this.tracker.add(new Item("Один"));
            this.tracker.add(new Item("Два"));
            this.itemTest = this.tracker.add(new Item("Три"));
            this.tracker.add(new Item("Четыре"));
            this.tracker.add(new Item("Пять"));
        } catch (Tracker.ErrorValue errorValue) {
            errorValue.printStackTrace();
        }
    }

    /**
     * Test exit.
     */
    @Test
    public void whenExitThenExit() {
        String[] answer = {"6"};
        StubInput input = new StubInput(answer, 3);
        new StartUI(this.tracker, input).run();
        StringBuilder builder = new StringBuilder();
        for (String string : input.getOut()) {
            builder.append(string);
        }
        String menu = String.format("0. Add new Item%1$s1. Show all items%1$s2. Edit item%1$s3. Delete item%1$s4. Find item by Id%1$s5. Find items by name%1$s6. Exit Program%1$s%1$sSelect : ", System.lineSeparator());
        assertEquals(String.format("%sПрограмма завершена%s", menu, System.lineSeparator()), builder.toString());
    }

    /**
     * Test add.
     */
    @Test
    public void whenAddThenAdd() {
        String[] answer = {"0", "name", "desc", "6"};
        StubInput input = new StubInput(answer, 10);
        new StartUI(this.tracker, input).run();
        assertEquals("name", this.tracker.findAll().get(5).getName());
    }

    /**
     * Test getAll.
     */
    @Test
    public void whenGetAllThenReturnAll() {
        String[] answer = {"1", "6"};
        StubInput input = new StubInput(answer, 10);
        new StartUI(this.tracker, input).run();
        StringBuilder builder = new StringBuilder();
        for (Item item : this.tracker.findAll()) {
            builder.append(item.getName());
        }
        assertEquals("ОдинДваТриЧетыреПять", builder.toString());
    }

    /**
     * Test delete.
     */
    @Test
    public void whenDeleteItemThenSaveOrder() {
        String[] answer = {"3", this.itemTest.getId().toString(), "6"};
        StubInput input = new StubInput(answer, 10);
        new StartUI(this.tracker, input).run();
        StringBuilder builder = new StringBuilder();
        for (Item item : this.tracker.findAll()) {
            builder.append(item.getName());
        }
        assertEquals("ОдинДваЧетыреПять", builder.toString());
    }

    /**
     * Test find by ID.
     */
    @Test
    public void whenFindByIdThenReturnCorrect() {
        String[] answer = {"4", this.itemTest.getId().toString(), "6"};
        StubInput input = new StubInput(answer, 10);
        new StartUI(this.tracker, input).run();
        assertEquals(String.format("%s%s", this.itemTest.toString(), System.lineSeparator()), input.getOut()[3]);
    }

    /**
     * Test find by Name.
     */
    @Test
    public void whenFindByNameThenReturnCorrect() {
        String[] answer = {"5", "Три", "6"};
        StubInput input = new StubInput(answer, 10);
        new StartUI(this.tracker, input).run();
        assertEquals(String.format("%s%s", this.itemTest.toString(), System.lineSeparator()), input.getOut()[3]);
    }

    /**
     * Test edit.
     * @throws Tracker.NotFound Не найден
     */
    @Test
    public void whenEditThenEditCorrect() throws Tracker.NotFound {
        String[] answer = {"2", this.itemTest.getId().toString(), "НольТриПятнадцать", "Новое описание", "6"};
        StubInput input = new StubInput(answer, 10);
        new StartUI(this.tracker, input).run();
        assertEquals("НольТриПятнадцать", this.tracker.findById(this.itemTest.getId()).getName());
    }

    /**
     * Test.
     */
    @Test
    public void whenIdFoundNotResultThenCorrectMessage() {
        String[] answer = {"2", "123", "6"};
        StubInput input = new StubInput(answer, 10);
        new StartUI(this.tracker, input).run();
        assertEquals(String.format("Заявка не найдена.%s", System.lineSeparator()), input.getOut()[3]);
    }
}
