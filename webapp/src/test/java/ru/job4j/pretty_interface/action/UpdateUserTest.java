package ru.job4j.pretty_interface.action;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.data_base.model.Role;
import ru.job4j.data_base.store.IUserStore;
import ru.job4j.data_base.store.RoleStore;
import ru.job4j.data_base.store.UserStore;

import javax.servlet.http.HttpSession;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

/**
 * Test class.
 */
public class UpdateUserTest {
    /**
     * Session mock.
     */
    private HttpSession session = mock(HttpSession.class);

    /**
     * User Store.
     */
    private IUserStore users = new UserStore();

    /**
     * before test method.
     */
    @Before
    public void before() {
        this.users.addUser("testDataUser", "", "");
        reset(this.session);
    }

    /**
     * before test method.
     */
    @After
    public void after() {
        new RoleStore().setUserRole("testDataUser", Role.DEFAULT_USER);
        this.users.deleteUser("testDataUser");
    }

    /** test method.
     * @param name name
     * @param email email
     * @param role role
     * @param country country
     * @param city city
     * @param roleUseUser user role
     * @param status success status
     */
    private void test(String name, String email, String role, String country, String city, Role roleUseUser, boolean status) {
        when(this.session.getAttribute(eq("user"))).thenReturn(this.users.getUser(roleUseUser == Role.ADMINISTRATOR ? "admin" : "login"));
        JSONObject data = setJSONUser(name, email, role, country, city);
        String expected = String.format("{\"success\":%s}", status ? "true" : "false");
        assertEquals(expected, new UpdateUser().action(data, this.session).toJSON());
    }

    /** set JSON object user.
     * @param name name
     * @param email email
     * @param role role
     * @param country country
     * @param city city
     * @return JSON object
     */
    private JSONObject setJSONUser(String name, String email, String role, String country, String city) {
        HashMap<String, String> json = new HashMap<>();
        json.put("login", "testDataUser");
        json.put("name", name);
        json.put("email", email);
        json.put("role", role);
        json.put("country", country);
        json.put("city", city);
        return new JSONObject(json);
    }

    /**
     * Test method.
     */
    @Test
    public void whenCorrectRulesAndParamsThenOK() {
        test("drag", "dc@sd.ds", "DEFAULT_USER", "dcWEs", "dolly", Role.ADMINISTRATOR, true);
    }

    /**
     * Test method.
     */
    @Test
    public void whenNotRulesAndParamsThenFalse() {
        test("dcs", "dcs", "dcs", "dcs", "d@csd.c", Role.DEFAULT_USER, false);
    }

    /**
     * Test method.
     */
    @Test
    public void whenNotCorrectParamsThenFalse() {
        test("dcs", "dcs", "dcs", "dcs", "d.c", Role.ADMINISTRATOR, false);
    }

    /**
     * Test method.
     */
    @Test
    public void whenCorrectRulesAndParamsThenOK2() {
        test("dragon", "dc@ee.ss", "ADMINISTRATOR", "dolly", "manchester", Role.ADMINISTRATOR, true);
    }
}