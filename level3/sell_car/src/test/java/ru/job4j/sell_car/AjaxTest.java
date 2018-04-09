package ru.job4j.sell_car;

import org.junit.Test;
import ru.job4j.sell_car.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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
    /** main test method.
     * @param json json action
     * @param expect answer json
     * @param user user session
     * @throws IOException IOException
     */
    private void test(String json, String expect, AtomicReference<User> user) throws IOException {
        System.out.println("JSON: " +  json + " /");
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
        when(session.getAttribute(eq("user"))).thenReturn(user.get());
        doAnswer(invocationOnMock -> {
            user.set(invocationOnMock.getArgumentAt(1, User.class));
            return null;
        }).when(session).setAttribute(eq("user"), any());
        doAnswer(invocationOnMock -> {
            answer.set(invocationOnMock.getArgumentAt(0, String.class));
            return null;
        }).when(writer).write(anyString());
        new Ajax().doPost(req, resp);
        assertEquals(expect, answer.get());
        System.out.println("-ok!");
    }

    /**
     * test method.
     * @throws IOException IOException
     */
    @Test
    public void whenFullAjaxThenReturnJSONSAnswer() throws IOException {
        AtomicReference<User> user = new AtomicReference<>();
        test("{\"action\":\"getUser\"}", "{\"success\":false}", user);
        test("{\"action\":\"login\", \"login\":\"login\", \"password\":\"pass\"}", "{\"success\":false}", user);
        test("{\"action\":\"create\"}", "{\"success\":false,\"error\":\"no login user\"}", user);
        test("{\"action\":\"registration\", \"login\":\"login\", \"password\":\"pass\"}", "{\"success\":true}", user);
        test("{\"action\":\"registration\", \"login\":\"login\", \"password\":\"pass\"}", "{\"success\":false}", user);
        test("{\"action\":\"login\", \"login\":\"login\", \"password\":\"pass\"}", "{\"success\":true}", user);
        test("{\"action\":\"get\"}", "{\"head\":[\"\",\"\"],\"data\":[],\"success\":true}", user);
        test("{\"action\":\"getUser\"}", "{\"success\":true,\"user\":\"login\"}", user);
        test("{\"action\":\"create\"}", "{\"success\":false,\"error\":\"JSONObject[\\\"car\\\"] not found.\"}", user);
        test("{\"action\":\"create\",\"car\":{}}", "{\"success\":false,\"error\":\"JSONObject[\\\"model\\\"] not found.\"}", user);
        test("{\"action\":\"create\",\"car\":{\"model\":\"lada\"}}", "{\"success\":false,\"error\":\"JSONObject[\\\"images\\\"] not found.\"}", user);
        test("{\"action\":\"create\",\"car\":{\"model\":\"lada\",\"images\":[\"path\",\"ds\"]}}", "{\"success\":true}", user);
        test("{\"action\":\"get\"}", "{\"head\":[\"\",\"\"],\"data\":[{\"car\":{\"images\":[\"path\",\"ds\"],\"price\":0,\"description\":\"lada\",\"id\":1},\"sell\":false,\"id\":1,\"user\":\"login\"}],\"success\":true}", user);
    }
}