package ru.job4j.pretty_interface.action;

import org.json.simple.JSONObject;
import ru.job4j.authentication.Authentification;
import ru.job4j.data_base.JSONConvert;
import ru.job4j.data_base.model.User;
import ru.job4j.data_base.store.UserStore;

import javax.servlet.http.HttpSession;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 09.03.2018
 */
public class CreateUser implements AjaxAction {

    @Override
    public JSONConvert action(JSONObject data, HttpSession session) {
        boolean success = false;
        if (Authentification.checkRules(session, "create")) {
            String login = (String) data.get("login");
            String name = (String) data.get("name");
            String email = (String) data.get("email");
            if (User.validate(login, name, email)) {
                success = new UserStore().addUser(
                        (String) data.get("login"),
                        (String) data.get("name"),
                        (String) data.get("email")
                );
            }
        }
        boolean finalSuccess = success;
        return () -> String.format("{\"success\":\"%s\"}", ((Boolean) finalSuccess).toString());
    }
}
