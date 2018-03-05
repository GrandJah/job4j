package ru.job4j.store;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.store.action_servlets.Create;
import ru.job4j.store.action_servlets.Delete;
import ru.job4j.store.action_servlets.Update;
import ru.job4j.store.model.User;
import ru.job4j.store.model.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.job4j.store.StubStore.stub;

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
     * stub User store.
     */
    private IUserStore users = new UserStore();
    /**
     * stub Role store.
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

    /** test.
     * @throws IOException IOException
     */
    @Test
    public void whenCreateAddUser() throws IOException {
        stub(new Create()).doPost(this.req, this.resp);
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
        stub(new Update()).doPost(this.req, this.resp);
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
        stub(new Update()).doPost(this.req, this.resp);
        verify(this.resp).sendRedirect(eq("RedirectUrl"));
        Object actual = this.users.getUser("Login");
        User expect = new User("Login", "Name User", "E-mail", 0);
        assertEquals(actual, expect);
        assertEquals(Role.ADMINISTRATOR, this.roles.getUserRole("Login"));
    }

    /** test.
     * @throws IOException IOException
     */
    @Test
    public void whenDeleteThenDeleteUser() throws IOException {
        this.users.addUser("Login", "Name", "e-mail");
        stub(new Delete()).doPost(this.req, this.resp);
        verify(this.resp).sendRedirect(eq("RedirectUrl"));
        assertEquals(User.UNKNOWN, this.users.getUser("Login"));
    }

}