package ru.job4j.authentication;

import org.junit.Test;
import ru.job4j.data_base.model.User;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Matchers.anyString;
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
     * @param path path request
     * @param user user session
     * @throws IOException IOException
     * @throws ServletException ServletException
     */
    private void test(String path, User user) throws IOException, ServletException {
        when(this.req.getServletPath()).thenReturn(path);
        new AuthFilter().doFilter(this.req, this.resp, this.filter);
    }

    /** test.
     * @throws IOException IOException
     * @throws ServletException ServletException
     */
    @Test
    public void whenWrongURL() throws IOException, ServletException {
        test("/hb/k", null);
        verify(this.resp).sendRedirect(anyString());
    }

    /** test.
     * @throws IOException IOException
     * @throws ServletException ServletException
     */
    @Test
    public void whenSRCPathThenGo() throws IOException, ServletException {
        test("/src", User.UNKNOWN);
        verify(this.filter).doFilter(this.req, this.resp);
    }

    /** test.
     * @throws IOException IOException
     * @throws ServletException ServletException
     */
    @Test
    public void whenWrongAjaxUrlThenRedirect() throws IOException, ServletException {
        test("/ajax/gh", User.UNKNOWN);
        verify(this.resp).sendRedirect(anyString());
    }

    /** test.
     * @throws IOException IOException
     * @throws ServletException ServletException
     */
    @Test
    public void whenAjaxUrlThenGo() throws IOException, ServletException {
        test("/ajax", null);
        verify(this.filter).doFilter(this.req, this.resp);
    }

    /** stub test.
     */
    @Test
    public void stub() {
        new AuthFilter().init(mock(FilterConfig.class));
        new AuthFilter().destroy();
    }
}