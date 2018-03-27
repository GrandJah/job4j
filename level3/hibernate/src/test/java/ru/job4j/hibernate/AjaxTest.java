package ru.job4j.hibernate;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class.
 */
public class AjaxTest {
    /**
     * Clear test DB.
     */
    @BeforeClass
    public static void clearBD() {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/job4j_test", "postgres", "postgres")) {
            conn.createStatement().executeUpdate("DELETE FROM items;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
        Ajax ajax = new Ajax();
        ajax.doPost(req, resp);
        assertEquals(answer.get().startsWith(expectStart), true);
        assertEquals(answer.get().endsWith(expectEnd), true);
    }

    /**test method.
     * @throws IOException trows
     */
    @Test
    public void whenGetThenReturnJsonString() throws IOException {
        test("{\"action\":\"get\"}", "{\"head\":[\"id\",\"task\",\"description\",\"created\",\"done\"],\"data\":[]}", "[]}");
        test("{\"action\":\"create\",\"task\":\"NewTask\",\"description\":\"DeskTask\"}", "{\"success\":true}", "");
        test("{\"action\":\"get\"}", "{\"head\":[\"id\",\"task\",\"description\",\"created\",\"done\"],\"data\":[{\"task\":\"NewTask\",\"created\":", "\"done\":false}]}");
    }
}