package ru.job4j.pretty_interface.action;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.data_base.store.UserStore;
import ru.job4j.data_base.store.UserStoreTest;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 09.03.2018
 */
public class FillTableTest {
    @Before
    public void before() {
        UserStoreTest.clear();
    }

    /**
     * Test method.
     */
    @Test
    public void whenEmptyTableThenEmptyAnswer() {
        FillTable action = new FillTable();
        assertEquals("{\"table\":\"Таблица пользователей\",\"table_id\":\"table_id\",\"rows\":[]",
                action.action(null, mock(HttpSession.class)).toJSON());
    }

    /**
     * Test method.
     */
    @Test
    public void whenPresentUserThenUserTableOK() {
        FillTable action = new FillTable();
        new UserStore().addUser("log","name","email");
        assertEquals("{\"table\":\"Таблица пользователей\",\"table_id\":\"table_id\",\"rows\":[{\"login\":\"log\";" +
                "\"name\":\"name\";\"email\":\"email\";\"role\":\"DEFAULT_USER\";\"location\":\"MegaCity\"}]",
                action.action(null, mock(HttpSession.class)).toJSON());
    }
}