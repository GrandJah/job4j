package ru.job4j.mvc_servlets;


import org.junit.Before;
import org.junit.Test;
import ru.job4j.data_base.store.IRoleStore;
import ru.job4j.data_base.store.IUserStore;
import ru.job4j.data_base.store.RoleStore;
import ru.job4j.data_base.store.UserStore;
import ru.job4j.data_base.model.Role;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 18.02.2018
 */
public class ControllerTest {
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
    private Controller controller = new Controller();

    /**
     * stub User data_base.
     */
    private IUserStore users = new UserStore();

    /**
     * stub User data_base.
     */
    private IRoleStore roles = new RoleStore();

    /**
     * before test.
     */
    @Before
    public void init() {
        this.users.addUser("1", "", "");
        this.users.addUser("2", "", "");
        this.users.addUser("3", "", "");
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute(eq("user"))).thenReturn(this.users.getUser("1"));
        when(this.req.getSession()).thenReturn(session);
        when(this.req.getRequestDispatcher(anyString())).thenReturn(mock(RequestDispatcher.class));
    }

    /** test ADMINISTRATOR.
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    @Test
    public void whenUserAdminThenAdminPage() throws ServletException, IOException {
        this.roles.setUserRole("1", Role.ADMINISTRATOR);
        this.controller.doGet(this.req, this.resp);
        verify(this.req).getRequestDispatcher(contains("forAdmins"));
    }

    /** test Default.
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    @Test
    public void whenDefaultUserThenDefaultPage() throws ServletException, IOException {
        this.roles.setUserRole("1", Role.DEFAULT_USER);
        this.controller.doGet(this.req, this.resp);
        verify(this.req).getRequestDispatcher(contains("forDefaultUsers"));
    }
}