package ru.job4j.authentication;

import org.junit.Test;
import ru.job4j.test.StubStore;
import ru.job4j.user_store.User;
import ru.job4j.user_store.UserStore;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * test AuthFilter class.
 */
public class AuthFilterTest {
    /**
     * HttpServletRequest mock.
     */
    private HttpServletRequest req = mock(HttpServletRequest.class);

    /**
     * HttpServletResponse mock.
     */
    private HttpServletResponse resp = mock(HttpServletResponse.class);

    /**
     * FilterChain mock.
     */
    private FilterChain filter = mock(FilterChain.class);

    /** Base test method.
     * @param url url request
     * @param user user session
     * @throws IOException IOException
     * @throws ServletException ServletException
     */
    private void test(String url, User user) throws IOException, ServletException {
        StubStore.stub(UserStore.class, "USER_STORE");
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute(eq("user"))).thenReturn(user);
        when(this.req.getSession()).thenReturn(session);
        when(this.req.getRequestURI()).thenReturn(url);
        new AuthFilter().doFilter(this.req, this.resp, this.filter);
    }

    /** test.
     * @throws IOException IOException
     * @throws ServletException ServletException
     */
    @Test
    public void whenNotLogin() throws IOException, ServletException {
        test("htpp://url", null);
        verify(this.resp).sendRedirect(anyString());
    }

    /** test.
     * @throws IOException IOException
     * @throws ServletException ServletException
     */
    @Test
    public void whenNotLoginUserDefined() throws IOException, ServletException {
        test("htpp://url", User.UNKNOWN);
        verify(this.filter).doFilter(this.req, this.resp);
    }

    /** test.
     * @throws IOException IOException
     * @throws ServletException ServletException
     */
    @Test
    public void whenLoginUrl() throws IOException, ServletException {
        test("htpp://url/login", User.UNKNOWN);
        verify(this.filter).doFilter(this.req, this.resp);
    }

    /** test.
     * @throws IOException IOException
     * @throws ServletException ServletException
     */
    @Test
    public void whenLoginUrlnotUser() throws IOException, ServletException {
        test("htpp://url/login", null);
        verify(this.filter).doFilter(this.req, this.resp);
    }
}