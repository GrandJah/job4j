package ru.job4j.pretty_interface.action;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.data_base.model.Role;
import ru.job4j.data_base.store.RoleStore;
import ru.job4j.data_base.store.UserStore;
import ru.job4j.data_base.store.UserStoreTest;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 09.03.2018
 */
public class UseUserTest {

    /**
     * session mock.
     */
    HttpSession session;

    /**
     * Test method.
     */
    @Before
    public void before() {
        this.session = mock(HttpSession.class);
        when(this.session.getAttribute(eq("user"))).thenReturn("Login");
        UserStoreTest.clear();
    }

    private void test(String expected){
        assertEquals(expected, new UseUser().action(null, this.session).toJSON());
    }

    /**
     * Test method.
     */
    @Test
    public void whenSessionUserPresentThenUseUserOK() {
        new UserStore().addUser("Login", "Name", "email");
        new RoleStore().setUserRole("Login", Role.DEFAULT_USER);
        test("{useUser: {\"login:\"Login\",\"create\":\"false\",\"edit\":\"user\"}}");
    }

    /**
     * Test method.
     */
    @Test
    public void whenSessionUserNoneThenUseUserOK() {
        test("{}");
    }

    /**
     * Test method.
     */
    @Test
    public void whenSessionUserPresentAdminThenUseUserOK() {
        new UserStore().addUser("Login", "Name", "email");
        new RoleStore().setUserRole("Login", Role.ADMINISTRATOR);
        test("{useUser: {\"login:\"Login\",\"create\":\"true\",\"edit\":\"all\"}}");
    }
}