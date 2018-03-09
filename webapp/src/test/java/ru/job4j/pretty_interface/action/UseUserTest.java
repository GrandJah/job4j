package ru.job4j.pretty_interface.action;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.data_base.store.UserStore;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
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
    private HttpSession session;

    /**
     * Test method.
     */
    @Before
    public void before() {
        this.session = mock(HttpSession.class);
    }

    /**
     * @param expected expected string
     * @param data data for action
     */
    private void test(String expected, JSONObject data) {
        assertEquals(expected, new UseUser().action(data, this.session).toJSON());
    }

    /**
     * Test method.
     */
    @Test
    public void whenSessionUserPresentThenUseUserOK() {
        when(this.session.getAttribute(eq("user"))).thenReturn(new UserStore().getUser("login"));
        test("{\"useUser\":{\"login\":\"login\",\"create\":false,\"edit\":\"user\"}}", mock(JSONObject.class));
    }

    /**
     * Test method.
     */
    @Test
    public void whenSessionUserNoneThenUseUserOK() {
        test("{}", mock(JSONObject.class));
    }

    /**
     * Test method.
     */
    @Test
    public void whenSessionUserPresentAdminThenUseUserOK() {
        when(this.session.getAttribute(eq("user"))).thenReturn(new UserStore().getUser("admin"));
        test("{\"useUser\":{\"login\":\"admin\",\"create\":true,\"edit\":\"all\"}}", mock(JSONObject.class));
    }

    /**
     * Test method.
     */
    @Test
    public void whenLogoutThenOK() {
        JSONObject json = new JSONObject();
        json.put("action", "logout");
        new UserStore().addUser("Login", "Name", "email");
        test("{}", json);
        verify(this.session).removeAttribute(eq("user"));
    }

    /**
     * Test method.
     */
    @Test
    public void whenNotLoginInUsersThenOK() {
        JSONObject json = new JSONObject();
        json.put("login", "Fghwe");
        json.put("password", "dcsdc");
        test("{}", json);
        verify(this.session, never()).setAttribute(eq("user"), any());
    }

    /**
     * Test method.
     */
    @Test
    public void whenLoginInUsersThenSetSesion() {
        JSONObject json = new JSONObject();
        json.put("login", "login");
        json.put("password", "pass");
        test("{\"useUser\":{\"login\":\"login\",\"create\":false,\"edit\":\"user\"}}", json);
        verify(this.session).setAttribute(eq("user"), eq(new UserStore().getUser("login")));
    }
}

