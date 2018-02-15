package ru.job4j.servlets;

import org.junit.Test;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
public class EchoServletTest {
    /**
     * HttpServletRequest mock.
     */
    private HttpServletRequest req = mock(HttpServletRequest.class);

    /**
     * HttpServletResponse mock.
     */
    private HttpServletResponse resp = mock(HttpServletResponse.class);

    /**
     * Test method.
     * @throws IOException IOException
     */
    @Test
    public void whenGetThenFlush() throws IOException {
        ServletOutputStream out = mock(ServletOutputStream.class);
        when(this.resp.getOutputStream()).thenReturn(out);
        new EchoServlet().doGet(this.req, this.resp);
        verify(out).flush();
    }

}