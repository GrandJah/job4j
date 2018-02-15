package ru.job4j.authentication;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.user_store.UserStore;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

/**
 * Login test class.
 */
public class LoginTest {
    /**
     * HttpServletRequest mock.
     */
    private HttpServletRequest req = mock(HttpServletRequest.class);

    /**
     * HttpServletResponse mock.
     */
    private HttpServletResponse resp  = mock(HttpServletResponse.class);

    /**
     * RequestDispatcher mock.
     */
    private RequestDispatcher dispatcher = mock(RequestDispatcher.class);

    /**
     * Login test class object.
     */
    private Login login = new Login();

    /**
     * login user stub.
     */
    private Object user;

    /**
     * Before test process.
     */
    @Before
    public void init() {
        when(this.req.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        when(this.req.getParameter(eq("login"))).thenReturn("UserLogin");

        //session test reaction
        HttpSession session = mock(HttpSession.class);
        doAnswer(i -> this.user = i.getArgumentAt(1, Object.class))
                .when(session).setAttribute(eq("user"), anyObject());
        when(session.getAttribute(eq("user"))).thenAnswer(i -> this.user);
        when(this.req.getSession()).thenReturn(session);
    }

    /**
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    @Test
    public void whenNotLoginUser() throws ServletException, IOException {
        this.login.doGet(this.req, this.resp);
        verify(this.dispatcher).forward(this.req, this.resp);
    }

    /**
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    @Test
    public void whenLoginIsCorrect() throws ServletException, IOException {
        UserStore.getStore().addUser("UserLogin", "nameLogin", "email");

        login.doPost(this.req, this.resp);
        verify(this.resp).sendRedirect(anyString());
        reset(this.resp);

        login.doGet(this.req, this.resp);
        verify(this.resp).sendRedirect(anyString());
    }

    /**
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    @Test
    public void whenLoginIsNotCorrect() throws ServletException, IOException {
        UserStore.getStore().deleteUser("UserLogin");

        login.doPost(this.req, this.resp);
        verify(dispatcher).forward(this.req, this.resp);
    }


}