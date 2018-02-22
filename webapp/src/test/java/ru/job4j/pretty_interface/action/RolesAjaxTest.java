package ru.job4j.pretty_interface.action;

import org.json.simple.JSONObject;
import org.junit.Test;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Test class.
 */
public class RolesAjaxTest {
    /**
     * Test method.
     */
    @Test
    public void whenEmptyRequestThenReturnAllRoles() {
        String actual = new RolesAjax().action(new JSONObject(), mock(HttpSession.class)).toJSON();
        assertEquals("{\"roles\":[\"ADMINISTRATOR\",\"DEFAULT_USER\"]}", actual);
    }
}