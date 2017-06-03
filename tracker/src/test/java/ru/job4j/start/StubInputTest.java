package ru.job4j.start;

import org.junit.Test;
import ru.job4j.tracker.Item;
import ru.job4j.tracker.Tracker;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 27.05.2017
 */
public class StubInputTest {
    /** Заполняем трекер.
     * @param tracker трэкер
     * @return средний элемент
     * @throws Tracker.ErrorValue Ошибка в воде данных
     */
    private Item fillTracker(Tracker tracker) throws Tracker.ErrorValue {
        tracker.add(new Item("Один"));
        tracker.add(new Item("Два"));
        Item itemReturn = new Item("Три");
        tracker.add(itemReturn);
        tracker.add(new Item("Четыре"));
        tracker.add(new Item("Пять"));
        return itemReturn;
    }

    /**
     * Test exit.
     * @throws Tracker.ErrorValue Ошибка в воде данных
     * @throws Tracker.NotFound Объект не найден
     */
    @Test
    public void whenExitThenExit() throws Tracker.NotFound, Tracker.ErrorValue {
        Tracker tracker = new Tracker();
        String[] answer = {"6"};
        StubInput input = new StubInput(answer, 3);
        new StartUI(tracker, input).run();
        StringBuilder builder = new StringBuilder();
        for (String string : input.getOut()) {
            builder.append(string);
        }
        String menu = String.format("0. Add new Item%1$s1. Show all items%1$s2. Edit item%1$s3. Delete item%1$s4. Find item by Id%1$s5. Find items by name%1$s6. Exit Program%1$s%1$sSelect : ", System.lineSeparator());
        assertThat(builder.toString(), is(String.format("%sПрограмма завершена%s", menu, System.lineSeparator())));
    }

    /**
     * Test add.
     * @throws Tracker.ErrorValue Ошибка в воде данных
     * @throws Tracker.NotFound Объект не найден
     */
    @Test
    public void whenAddThenAdd() throws Tracker.NotFound, Tracker.ErrorValue {
        Tracker tracker = new Tracker();
        String[] answer = {"0", "name", "desc", "6"};
        StubInput input = new StubInput(answer, 10);
        new StartUI(tracker, input).run();
        assertThat(tracker.getAll().get(0).getName(), is("name"));
    }

    /**
     * Test getAll.
     * @throws Tracker.ErrorValue Ошибка в воде данных
     * @throws Tracker.NotFound Объект не найден
     */
    @Test
    public void whenGetAllThenReturnAll() throws Tracker.NotFound, Tracker.ErrorValue {
        Tracker tracker = new Tracker();
        fillTracker(tracker);
        String[] answer = {"1", "6"};
        StubInput input = new StubInput(answer, 10);
        new StartUI(tracker, input).run();
        StringBuilder builder = new StringBuilder();
        for (Item item : tracker.getAll()) {
            builder.append(item.getName());
        }
        assertThat(builder.toString(), is("ОдинДваТриЧетыреПять"));
    }

    /**
     * Test delete.
     * @throws Tracker.ErrorValue Ошибка в воде данных
     * @throws Tracker.NotFound Объект не найден
     */
    @Test
    public void whenDeleteItemThenSaveOrder() throws Tracker.NotFound, Tracker.ErrorValue {
        Tracker tracker = new Tracker();
        Item itemDelete = fillTracker(tracker);
        String[] answer = {"3", itemDelete.getId(), "6"};
        StubInput input = new StubInput(answer, 10);
        new StartUI(tracker, input).run();
        StringBuilder builder = new StringBuilder();
        for (Item item : tracker.getAll()) {
            builder.append(item.getName());
        }
        assertThat(builder.toString(), is("ОдинДваЧетыреПять"));
    }

    /**
     * Test find by ID.
     * @throws Tracker.ErrorValue Ошибка в воде данных
     * @throws Tracker.NotFound Объект не найден
     */
    @Test
    public void whenFindByIdThenReturnCorrect() throws Tracker.ErrorValue, Tracker.NotFound {
        Tracker tracker = new Tracker();
        Item itemReturn = fillTracker(tracker);
        String[] answer = {"4", itemReturn.getId(), "6"};
        StubInput input = new StubInput(answer, 10);
        new StartUI(tracker, input).run();
        assertThat(input.getOut()[3], is(String.format("%s%s", itemReturn.toString(), System.lineSeparator())));
    }

    /**
     * Test find by Name.
     * @throws Tracker.ErrorValue Ошибка в воде данных
     * @throws Tracker.NotFound Объект не найден
     */
    @Test
    public void whenFindByNameThenReturnCorrect() throws Tracker.NotFound, Tracker.ErrorValue {
        Tracker tracker = new Tracker();
        Item itemReturn = fillTracker(tracker);
        String[] answer = {"5", "Три", "6"};
        StubInput input = new StubInput(answer, 10);
        new StartUI(tracker, input).run();
        assertThat(input.getOut()[3], is(String.format("%s%s", itemReturn.toString(), System.lineSeparator())));
    }

    /**
     * Test edit.
     * @throws Tracker.ErrorValue Ошибка в воде данных
     * @throws Tracker.NotFound Объект не найден
     */
    @Test
    public void whenEditThenEditCorrect() throws Tracker.NotFound, Tracker.ErrorValue {
        Tracker tracker = new Tracker();
        Item itemEdit = fillTracker(tracker);
        String[] answer = {"2", itemEdit.getId(), "НольТриПятнадцать", "Новое описание", "6"};
        StubInput input = new StubInput(answer, 10);
        new StartUI(tracker, input).run();
        assertThat(itemEdit.getName(), is("НольТриПятнадцать"));
    }

    /**
     * Test.
     * @throws Tracker.ErrorValue Ошибка в воде данных
     * @throws Tracker.NotFound Объект не найден
     */
    @Test
    public void whenIdFoundNotResultThenCorrectMessage() throws Tracker.NotFound, Tracker.ErrorValue {
        Tracker tracker = new Tracker();
        Item itemEdit = fillTracker(tracker);
        String[] answer = {"2", "123", "6"};
        StubInput input = new StubInput(answer, 10);
        new StartUI(tracker, input).run();
        assertThat(input.getOut()[3], is(String.format("Заявка не найдена.%s", System.lineSeparator())));
    }
}