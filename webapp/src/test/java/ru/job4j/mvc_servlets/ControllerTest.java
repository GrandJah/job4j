package ru.job4j.mvc_servlets;


import org.junit.Before;
import org.junit.Test;
import ru.job4j.test.StubStore;
import ru.job4j.user_store.IUserStore;
import ru.job4j.user_store.Role;
import ru.job4j.user_store.UserStore;

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
     * stub User store.
     */
    private IUserStore stubStore = StubStore.stub(UserStore.class, "USER_STORE");

    /**
     * before test.
     */
    @Before
    public void init() {
        this.stubStore.addUser("1", "", "");
        this.stubStore.addUser("2", "", "");
        this.stubStore.addUser("3", "", "");
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute(eq("user"))).thenReturn(this.stubStore.getUser("1"));
        when(this.req.getSession()).thenReturn(session);
        when(this.req.getRequestDispatcher(anyString())).thenReturn(mock(RequestDispatcher.class));
    }


    /** test Administrator.
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    @Test
    public void whenUserAdminThenAdminPage() throws ServletException, IOException {
        this.stubStore.setUserRole("1", Role.Administrator);
        new Controller().doGet(this.req, this.resp);
        verify(this.req).getRequestDispatcher(contains("forAdmins"));
    }

    /** test Default.
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    @Test
    public void whenDefaultUserThenDefaultPage() throws ServletException, IOException {
        new Controller().doGet(this.req, this.resp);
        verify(this.req).getRequestDispatcher(contains("forDefaultUsers"));
    }
}