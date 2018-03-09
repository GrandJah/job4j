package ru.job4j.data_base;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.data_base.action_servlets.Create;
import ru.job4j.data_base.action_servlets.Delete;
import ru.job4j.data_base.action_servlets.Update;
import ru.job4j.data_base.model.User;
import ru.job4j.data_base.model.Role;
import ru.job4j.data_base.store.IRoleStore;
import ru.job4j.data_base.store.IUserStore;
import ru.job4j.data_base.store.RoleStore;
import ru.job4j.data_base.store.UserStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 17.02.2018
 */
public class ActionServletsTest {
    /**
     * HttpServletRequest mock.
     */
    private HttpServletRequest req = mock(HttpServletRequest.class);

    /**
     * HttpServletResponse mock.
     */
    private HttpServletResponse resp = mock(HttpServletResponse.class);

    /**
     * stub User data_base.
     */
    private IUserStore users = new UserStore();
    /**
     * stub Role data_base.
     */
    private IRoleStore roles = new RoleStore();

    /**
     * before test.
     */
    @Before
    public void init() {
        when(this.req.getParameter(eq("login"))).thenReturn("Login");
        when(this.req.getParameter(eq("name"))).thenReturn("Name User");
        when(this.req.getParameter(eq("email"))).thenReturn("E-mail");
        when(this.req.getParameter(eq("role"))).thenReturn(Role.DEFAULT_USER.name());
        when(this.req.getHeader(eq("Referer"))).thenReturn("RedirectUrl");
    }

    /**
     * before test.
     */
    @After
    public void after() {
        users.deleteUser("Login");
        reset(this.req);
    }

    /** test.
     * @throws IOException IOException
     */
    @Test
    public void whenCreateAddUser() throws IOException {
        new Create().doPost(this.req, this.resp);
        verify(this.resp).sendRedirect(eq("RedirectUrl"));
        Object actual = this.users.getUser("Login");
        User expect = new User("Login", "Name User", "E-mail", 0);
        assertEquals(expect, actual);
    }

    /** test.
     * @throws IOException IOException
     */
    @Test
    public void whenPostThenUpdateDefaultUser() throws IOException {
        this.users.addUser("Login", "Name", "old e-mail");
        new Update().doPost(this.req, this.resp);
        verify(this.resp).sendRedirect(eq("RedirectUrl"));
        Object actual = this.users.getUser("Login");
        User expect = new User("Login", "Name User", "E-mail", 0);
        assertEquals(actual, expect);
        assertEquals(Role.DEFAULT_USER.name(), this.roles.getUserRole("Login").name());
    }

    /** test.
     * @throws IOException IOException
     */
    @Test
    public void whenPostThenUpdateAdministrator() throws IOException {
        this.users.addUser("Login", "Name", "old e-mail");
        when(this.req.getParameter(eq("role"))).thenReturn(Role.ADMINISTRATOR.name());
        new Update().doPost(this.req, this.resp);
        verify(this.resp).sendRedirect(eq("RedirectUrl"));
        Object actual = this.users.getUser("Login");
        User expect = new User("Login", "Name User", "E-mail", 0);
        assertEquals(expect, actual);
        assertEquals(Role.ADMINISTRATOR, this.roles.getUserRole("Login"));
    }

    /** test.
     * @throws IOException IOException
     */
    @Test
    public void whenDeleteThenDeleteUser() throws IOException {
        this.users.addUser("Login", "Name", "e-mail");
        new Delete().doPost(this.req, this.resp);
        verify(this.resp).sendRedirect(eq("RedirectUrl"));
        assertEquals(User.UNKNOWN, this.users.getUser("Login"));
    }

}