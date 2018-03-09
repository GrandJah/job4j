package ru.job4j.pretty_interface.action;

import org.json.simple.JSONObject;
import ru.job4j.data_base.JSONConvert;
import ru.job4j.data_base.model.Role;
import ru.job4j.data_base.model.User;
import ru.job4j.data_base.store.RoleStore;
import ru.job4j.data_base.store.UserStore;

import javax.servlet.http.HttpSession;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 09.03.2018
 */
public class UseUser implements AjaxAction {
    @Override
    public JSONConvert action(JSONObject data, HttpSession session) {
        String json = "{}";
        User user = (User) session.getAttribute("user");
        if ("logout".equals(data.get("action"))) {
            session.removeAttribute("user");
        } else {
            if (user == null) {
                String login = (String) data.get("login");
                String password = (String) data.get("password");
                if (login != null && password != null) {
                    user = new UserStore().getUser(login);//! login must validate password
                    if (user != User.UNKNOWN) {
                        session.setAttribute("user", user);
                    }
                } else {
                    user = User.UNKNOWN;
                }
            }
            if (user != User.UNKNOWN) {
                String login = user.getLogin();
                boolean admin = new RoleStore().getUserRole(login) == Role.ADMINISTRATOR;
                String create = admin ? "true" : "false";
                String edit = admin ? "all" : "user";
                json = String.format("{\"useUser\":{\"login\":\"%s\",\"create\":%s,\"edit\":\"%s\"}}",
                        login, create, edit);
            }
        }
        String finalJson = json;
        return () -> finalJson;
    }
}
