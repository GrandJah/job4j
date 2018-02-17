package ru.job4j.interface_servlet;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.interface_servlet.action_servlets.Create;
import ru.job4j.interface_servlet.action_servlets.Delete;
import ru.job4j.interface_servlet.action_servlets.Update;
import ru.job4j.test.StubStore;
import ru.job4j.user_store.IUserStore;
import ru.job4j.user_store.Role;
import ru.job4j.user_store.User;
import ru.job4j.user_store.UserStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;


import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 17.02.2018
 */
public class InterServletTest {
    /**
     * HttpServletRequest mock.
     */
    private HttpServletRequest req = mock(HttpServletRequest.class);

    /**
     * HttpServletResponse mock.
     */
    private HttpServletResponse resp = mock(HttpServletResponse.class);

    /**
     * stub User store.
     */
    private IUserStore store;

    /**
     * before test.
     * @throws NoSuchFieldException NoSuchFieldException
     * @throws IllegalAccessException IllegalAccessException
     */
    @Before
    public void init() throws NoSuchFieldException, IllegalAccessException {

        this.store = new StubStore();
        Field storeField = UserStore.class.getDeclaredField("store");
        storeField.setAccessible(true);
        storeField.set(null, this.store);

        when(this.req.getParameter(eq("login"))).thenReturn("Login");
        when(this.req.getParameter(eq("name"))).thenReturn("Name User");
        when(this.req.getParameter(eq("email"))).thenReturn("E-mail");
        when(this.req.getParameter(eq("role"))).thenReturn(Role.DefaultUser.toString());
        when(this.req.getHeader(eq("Referer"))).thenReturn("RedirectUrl");
    }

    /** test.
     * @throws IOException IOException
     */
    @Test
    public void whenCreateAddUser() throws IOException {
        new Create(this.store).doPost(this.req, this.resp);
        verify(this.resp).sendRedirect(eq("RedirectUrl"));
        Object actual = this.store.getUser("Login");
        User expect = new User("Login", "Name User", "E-mail", 0);
        assertEquals(actual, expect);
    }

    /** test.
     * @throws IOException IOException
     */
    @Test
    public void whenPostThenUpdateUser() throws IOException {
        this.store.addUser("Login", "Name", "old e-mail");
        new Update(this.store).doPost(this.req, this.resp);
        verify(this.resp).sendRedirect(eq("RedirectUrl"));
        Object actual = this.store.getUser("Login");
        User expect = new User("Login", "Name User", "E-mail", 0);
        assertEquals(actual, expect);
    }

    /** test.
     * @throws IOException IOException
     */
    @Test
    public void whenDeleteThenDeleteUser() throws IOException {
        this.store.addUser("Login", "Name", "e-mail");
        new Delete(this.store).doPost(this.req, this.resp);
        verify(this.resp).sendRedirect(eq("RedirectUrl"));
        assertEquals(this.store.getUser("Login"), User.UNKNOWN);
    }

}