package ru.job4j.sell_car;

import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.sell_car.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * test class.
 */
public class AjaxTest {
    /**
     * Clear test DB.
     */
    @BeforeClass
    public static void clearBD() {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/job4j_test", "postgres", "postgres")) {
            conn.createStatement().executeUpdate("drop table if exists announcements; drop table if exists  images; drop table if exists cars; drop table if exists users;\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * User session test object.
     */
    private User user = null;

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
            HttpSession session = mock(HttpSession.class);
            when(req.getSession()).thenReturn(session);
            when(session.getAttribute(eq("user"))).thenReturn(this.user);
            doAnswer(invocationOnMock -> {
                user = invocationOnMock.getArgumentAt(1, User.class);
                return null;
            }).when(session).setAttribute(eq("user"), any());
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
    public void whenFullAjaxThenReturnJSONSAnswer() {
        test("{\"action\":\"getUser\"}", "{\"success\":false}");
        test("{\"action\":\"login\", \"login\":\"login\", \"password\":\"pass\"}", "{\"success\":false}");
        test("{\"action\":\"create\"}", "{\"success\":false,\"error\":\"no login user\"}");
        test("{\"action\":\"registration\", \"login\":\"login\", \"password\":\"pass\"}", "{\"success\":true}");
        test("{\"action\":\"registration\", \"login\":\"login\", \"password\":\"pass\"}", "{\"success\":false}");
        test("{\"action\":\"login\", \"login\":\"login\", \"password\":\"pass\"}", "{\"success\":true}");
        test("{\"action\":\"get\"}", "{\"head\":[\"\",\"\"],\"data\":[],\"success\":true}");
        test("{\"action\":\"getUser\"}", "{\"success\":true,\"user\":\"login\"}");
    }
}