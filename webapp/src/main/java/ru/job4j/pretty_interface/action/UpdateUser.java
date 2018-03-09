package ru.job4j.pretty_interface.action;

import org.json.simple.JSONObject;
import ru.job4j.authentication.Authentification;
import ru.job4j.data_base.JSONConvert;
import ru.job4j.data_base.model.Location;
import ru.job4j.data_base.model.Role;
import ru.job4j.data_base.model.User;
import ru.job4j.data_base.store.RoleStore;
import ru.job4j.data_base.store.UseLocation;
import ru.job4j.data_base.store.UserStore;

import javax.servlet.http.HttpSession;

/**
 * Ajax Action for update user.
 */
public class UpdateUser implements AjaxAction {
    @Override
    public JSONConvert action(JSONObject data, HttpSession session) {
        boolean editUser = Authentification.checkRules(session, "edit_user");
        boolean editRole = Authentification.checkRules(session, "edit_role");
        boolean success = false;
        String login = (String) data.get("login");
        String name = (String) data.get("name");
        String email = (String) data.get("email");
        String userRole = (String) data.get("role");
        String country = (String) data.get("country");
        String city = (String) data.get("city");
        boolean flag = new RoleStore().getRoles().stream().map(role -> role.name().equals(userRole)).reduce(false, (a, b) -> a || b);
        flag &= User.validate(login, name, email);
        flag &= Location.validate(country, city);
        if (flag) {
            if (editUser) {
                new UserStore().updateUser(login, name, email);
            }
            if (editRole) {
                new RoleStore().setUserRole(login, Role.valueOf(userRole));
            }
            new UseLocation().updateLocation(login, Location.valueOf(String.format("%s/%s", city, country)));
            success = true;
        }
        boolean finalSuccess = success;
        return () -> String.format("{\"success\":%s}", finalSuccess ? "true" : "false");
    }
}
