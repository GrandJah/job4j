package ru.job4j.pretty_interface.action;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.data_base.model.Role;
import ru.job4j.data_base.model.User;
import ru.job4j.data_base.store.IRoleStore;
import ru.job4j.data_base.store.IUserStore;
import ru.job4j.data_base.store.RoleStore;
import ru.job4j.data_base.store.UserStore;
import ru.job4j.data_base.store.UserStoreTest;

import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 09.03.2018
 */
public class CreateUserTest {
    private HttpSession session = mock(HttpSession.class);
    private IUserStore users = new UserStore();
    private IRoleStore roles = new RoleStore();


    @Before
    public void before(){
        reset(this.session);
        UserStoreTest.clear();
    }

    private User setUser(String login, Role role) {
        this.users.addUser(login,"","");
        this.roles.setUserRole(login, role);
        return this.users.getUser(login);
    }

    private JSONObject setJSONUser(String login, String name, String email) {
        JSONObject json = new JSONObject();
        json.put("login", login);
        json.put("name", name);
        json.put("email", email);
        return json;
    }

    private void test(String login, String name, String email, Role roleUseUser, boolean status) {
        when(this.session.getAttribute(eq("user"))).thenReturn(setUser("adm", roleUseUser));
        JSONObject data = setJSONUser(login,name, email);
        String expected = "{\"success\":\"" + (status ? "true" : "false") + "\"}";
        assertEquals(expected, new CreateUser().action(data, this.session).toJSON());
    }

    /**
     * Test method.
     */
    @Test
    public void whenCorrectRulesAndParmsThenOK() {
        test("adhiw","dcs", "d@csd.c", Role.ADMINISTRATOR, true);
    }

    /**
     * Test method.
     */
    @Test
    public void whenNotRulesThenFalse() {
        test("adhiw","dcs", "d@csd.c", Role.DEFAULT_USER, false);
    }

    /**
     * Test method.
     */
    @Test
    public void whenNotCorrectedLoginThenFalse() {
        test("adiw","dcs", "d@csd.c", Role.ADMINISTRATOR, false);
    }

    /**
     * Test method.
     */
    @Test
    public void whenNotCorrectedNameThenFalse() {
        test("adwiw","dc//s", "d@csd.c", Role.ADMINISTRATOR, false);
    }

    /**
     * Test method.
     */
    @Test
    public void whenNotCorrectedEmailThenFalse() {
        test("adwiw","dcsss", "dcsd.c", Role.ADMINISTRATOR, false);
    }
}