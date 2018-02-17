package ru.job4j.crud_server;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.test.StubStore;
import ru.job4j.user_store.IUserStore;
import ru.job4j.user_store.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test UserServlets class.
 */
public class UserServletTest {
    /**
     * HttpServletRequest mock.
     */
    private HttpServletRequest req = mock(HttpServletRequest.class);

    /**
     * HttpServletResponse mock.
     */
    private HttpServletResponse resp = mock(HttpServletResponse.class);

    /**
     * test servlet.
     */
    private UserServlet servlet;

    /**
     * stub User store.
     */
    private IUserStore store;


    /**
     * before test.
     */
    @Before
    public void init() {
        this.store = new StubStore();
        this.servlet = new UserServlet(this.store);
        when(this.req.getParameter(eq("login"))).thenReturn("Login");
        when(this.req.getParameter(eq("name"))).thenReturn("Name User");
        when(this.req.getParameter(eq("email"))).thenReturn("E-mail");
    }

    /** test.
     * @throws IOException IOException
     */
    @Test
    public void whenPutThenAddUser() throws IOException {
        this.servlet.doPut(this.req, this.resp);
        verify(this.resp).sendError(eq(201));
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
        this.servlet.doPost(this.req, this.resp);
        verify(this.resp).sendError(eq(200));
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
        this.servlet.doDelete(this.req, this.resp);
        verify(this.resp).sendError(eq(200));
        assertEquals(this.store.getUser("Login"), User.UNKNOWN);
    }
}