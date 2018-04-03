package ru.job4j.sell_car;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * test class.
 */
public class AjaxTest {

    /** main test method.
     * @param json json action
     * @param expect answer json
     */
    private void test(String json, String expect) {
        try {
            AtomicReference<String> answer = new AtomicReference<>();
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);
            BufferedReader reader = mock(BufferedReader.class);
            when(req.getReader()).thenReturn(reader);

            when(reader.readLine()).thenReturn(json);

            PrintWriter writer = mock(PrintWriter.class);
            when(resp.getWriter()).thenReturn(writer);
            when(req.getSession()).thenReturn(mock(HttpSession.class));
            doAnswer(invocationOnMock -> {
                answer.set(invocationOnMock.getArgumentAt(0, String.class));
                return null;
            }).when(writer).write(anyString());
            new Ajax().doPost(req, resp);
            assertEquals(expect, answer.get());
        } catch (IOException e) {
            e.printStackTrace();
            throw new Error("error test!");
        }
    }

    /**
     * test method.
     */
    @Test
    public void whenAjaxGetThenReturnJSONTable() {
//        test("{\"action\":\"get\"}", "");
    }

    /**
     * test method.
     */
    @Test
    public void whenAjaxCreateThenReturnJSONSuccess() {
//        test("{\"action\":\"create\"}", "");
    }
}