package ru.job4j.pretty_interface.action;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import org.json.simple.JSONObject;
import org.junit.Before;

import java.util.HashMap;
import javax.servlet.http.HttpSession;
import ru.job4j.data_base.model.Role;
import ru.job4j.data_base.store.IUserStore;
import ru.job4j.data_base.store.UserStore;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 09.03.2018
 */
public class CreateUserTest {
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
        reset(this.session);
    }

    /** set JSON object user.
     * @param login login
     * @param name name
     * @param email email
     * @return JSON object
     */
    private JSONObject setJSONUser(String login, String name, String email) {
        HashMap<String, String> json = new HashMap<>();
        json.put("login", login);
        json.put("name", name);
        json.put("email", email);
        return  new JSONObject(json);
    }

    /** test method.
     * @param login login
     * @param name name
     * @param email email
     * @param roleUseUser user role
     * @param status success status
     */
    private void test(String login, String name, String email, Role roleUseUser, boolean status) {
        when(this.session.getAttribute(eq("user"))).thenReturn(this.users.getUser(roleUseUser == Role.ADMINISTRATOR ? "admin" : "login"));
        JSONObject data = setJSONUser(login, name, email);
        String expected = "{\"success\":\"" + (status ? "true" : "false") + "\"}";
        assertEquals(expected, new CreateUser().action(data, this.session).toJSON());
    }

//    /**
//     * Test method.
//     */
//    @Test
//    public void whenCorrectRulesAndParamsThenOK() {
//        test("creteTestUser", "dcs", "d@csd.c", Role.ADMINISTRATOR, true);
//        this.users.deleteUser("creteTestUser");
//    }

//    /**
//     * Test method.
//     */
//    @Test
//    public void whenNotRulesThenFalse() {
//        test("creteTestUser", "dcs", "d@csd.c", Role.DEFAULT_USER, false);
//    }

//    /**
//     * Test method.
//     */
//    @Test
//    public void whenNotCorrectedLoginThenFalse() {
//        test("cre", "dcs", "d@csd.c", Role.ADMINISTRATOR, false);
//    }

    /**/

//    /**
//     * Test method.
//     */
//    @Test
//    public void whenNotCorrectedEmailThenFalse() {
//        test("creteTestUser", "dirty", "em", Role.ADMINISTRATOR, false);
//    }
}
