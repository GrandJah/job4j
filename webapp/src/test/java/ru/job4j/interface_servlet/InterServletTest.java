package ru.job4j.interface_servlet;

import org.junit.Test;
import ru.job4j.store.IUserStore;
import ru.job4j.store.StubStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.job4j.store.StubStore.stub;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 18.02.2018
 */
public class InterServletTest {
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
        IUserStore store = new StubStore();
        store.addUser("1", "2", "3");
        PrintWriter write = mock(PrintWriter.class);
        when(this.resp.getWriter()).thenReturn(write);
        stub(new InterServlet()).doGet(this.req, this.resp);
        verify(write).write(anyString());
        verify(write).flush();
    }
}