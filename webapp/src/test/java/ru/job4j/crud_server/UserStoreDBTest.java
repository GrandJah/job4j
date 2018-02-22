package ru.job4j.crud_server;

import org.junit.Test;
import ru.job4j.crud_server.data_base.UserStoreDB;
import ru.job4j.store.IUserStore;
import ru.job4j.store.db.DBInterface;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 18.02.2018
 */
public class UserStoreDBTest {


    /**
     * DBInterface mock.
     */
    private DBInterface db = new DBInterface() {

        @Override
        public boolean goDB(String query, Object... params) {
            return goDB(query, null, params);
        }

        @Override
        public boolean goDB(String query, Function<ResultSet, Void> function, Object... params) {
            UserStoreDBTest.this.query = query;
            UserStoreDBTest.this.arg.addAll(Arrays.asList(params));
            return function == null;
        }
    };

    /**
     * Test store object.
     */
    private IUserStore store = new UserStoreDB() { { setDb(UserStoreDBTest.this.db); } };

    /**
     * actual SQL query string.
     */
    private String query;

    /**
     * actual SQL query argument.
     */
    private final List<Object> arg = new ArrayList<>();

    /** Test method. */
    @Test(expected = UnsupportedOperationException.class)
    public void whenGetUsersThenUnSupport() {
        this.store.getUsers();
    }

    /** Test method. */
    @Test(expected = UnsupportedOperationException.class)
    public void whenIteratorThenReturnIteratorUserStore() {
        this.store.iterator();
    }

    /** Test method. */
    @Test
    public void whenAddUserThenCorrectQuery() {
        this.store.addUser("log", "name", "email");
        assertEquals(this.query, "INSERT  INTO  users_store VALUES (?, ?, ?, ?)");
        this.arg.remove(3);
        assertArrayEquals(new Object[] {"log", "name", "email"}, this.arg.toArray());
    }

    /** Test method. */
    @Test
    public void whenDeleteUserThenCorrectQuery() {
        this.store.deleteUser("DeleteLogin");
        assertEquals(this.query, "DELETE FROM users_store WHERE login = ?");
        assertArrayEquals(new Object[] {"DeleteLogin"}, this.arg.toArray());
    }

    /** Test method. */
    @Test
    public void whenUpdateUserThenCorrectQuery() {
        this.store.updateUser("log", "name", "email");
        assertEquals(this.query, "UPDATE users_store SET name = ?, email =  ? WHERE login = ?");
        assertArrayEquals(new Object[] {"name", "email", "log"}, this.arg.toArray());
    }

    /** Test method. */
    @Test
    public void whenGetUserThenReturnUser() {
        this.store.updateUser("log", "name", "email");
        assertEquals(this.query, "UPDATE users_store SET name = ?, email =  ? WHERE login = ?");
        assertArrayEquals(new Object[] {"name", "email", "log"}, this.arg.toArray());
    }
}