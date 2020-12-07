package ru.job4j.todolist.controller;

import org.junit.Test;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicReference;
import ru.job4j.todolist.storage.HbmStorageItem;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class.
 */
public class AjaxTest {

    /** main test body.
     * @param data action data
     * @param expectStart expect start json string
     * @param expectEnd expect end json string
     * @throws IOException trows
     */
    private void test(String data, String expectStart, String expectEnd) throws IOException {
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        BufferedReader reader = mock(BufferedReader.class);
        when(req.getReader()).thenReturn(reader);
        when(reader.readLine()).thenReturn(data);
        PrintWriter writer = mock(PrintWriter.class);
        when(resp.getWriter()).thenReturn(writer);
        AtomicReference<String> answer = new AtomicReference<>();
        doAnswer(i -> {
            answer.set(i.getArgumentAt(0, String.class));
            return null;
        }).when(writer).write(anyString());
        Ajax ajax = new Ajax(new HbmStorageItem());
        ajax.doPost(req, resp);
        assertTrue(answer.get().startsWith(expectStart));
        assertTrue(answer.get().endsWith(expectEnd));
    }

    /**test method.
     * @throws IOException trows
     */
    @Test
    public void whenGetThenReturnJsonString() throws IOException {
        test("{\"action\":\"get\"}", "{\"data\":[]}", "[]}");
        test("{\"action\":\"create\",\"task\":\"NewTask\",\"description\":\"DeskTask\"}", "{\"success\":true}", "");
        test("{\"action\":\"get\"}", "{\"data\":[{\"task\":\"NewTask\",\"created\":", "\"done\":false}]}");
    }
}
