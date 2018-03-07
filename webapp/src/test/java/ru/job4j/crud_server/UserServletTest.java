package ru.job4j.crud_server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.store.IUserStore;
import ru.job4j.store.UserStore;
import ru.job4j.store.model.User;

import javax.servlet.ServletOutputStream;
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
    private UserServlet servlet = new UserServlet();

    /**
     * stub User store.
     */
    private IUserStore users = new UserStore();

    /**
     * After method.
     */
    @After
    public void after() {
        users.deleteUser("Login");
    }

    /**
     * before test.
     */
    @Before
    public void init() {
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
        Object actual = this.users.getUser("Login");
        User expect = new User("Login", "Name User", "E-mail", 0);
        assertEquals(actual, expect);
    }

    /** test.
     * @throws IOException IOException
     */
    @Test
    public void whenPutPresentUserThenAddUserFailed() throws IOException {
        User added = new User("Login", "Name", "E-mail", 0);
        this.users.deleteUser("Login");
        this.users.addUser("Login", "Name", "E-mail");
        this.servlet.doPut(this.req, this.resp);
        verify(this.resp).sendError(eq(501));
        assertEquals(added, this.users.getUser("Login"));
    }

    /** test.
     * @throws IOException IOException
     */
    @Test
    public void whenPostThenUpdateUser() throws IOException {
        this.users.addUser("Login", "Name", "old e-mail");
        this.servlet.doPost(this.req, this.resp);
        verify(this.resp).sendError(eq(200));
        Object actual = this.users.getUser("Login");
        User expect = new User("Login", "Name User", "E-mail", 0);
        assertEquals(expect, actual);
    }

    /** test.
     * @throws IOException IOException
     */
    @Test
    public void whenDeleteThenDeleteUser() throws IOException {
        this.users.addUser("Login", "Name", "e-mail");
        this.servlet.doDelete(this.req, this.resp);
        verify(this.resp).sendError(eq(200));
        assertEquals(this.users.getUser("Login"), User.UNKNOWN);
    }

    /** test.
     * @throws IOException IOException
     */
    @Test
    public void whenGetThenPrintStreamFlush() throws IOException {
        ServletOutputStream outputStream = mock(ServletOutputStream.class);
        when(this.resp.getOutputStream()).thenReturn(outputStream);
        this.servlet.doGet(this.req, this.resp);
        verify(outputStream).flush();
    }


}