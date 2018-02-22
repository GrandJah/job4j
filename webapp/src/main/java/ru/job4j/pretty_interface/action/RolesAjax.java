package ru.job4j.pretty_interface.action;

import org.json.simple.JSONObject;
import ru.job4j.data_base.JSONConvert;
import ru.job4j.data_base.model.Role;
import ru.job4j.data_base.store.RoleStore;

import javax.servlet.http.HttpSession;

/**
 * Ajax action for get Roles.
 */
public class RolesAjax implements AjaxAction {
    @Override
    public JSONConvert action(JSONObject data, HttpSession session) {
        return () -> {
            StringBuilder build = new StringBuilder("{\"roles\":[");
            for (Role role: new RoleStore().getRoles()) {
                build.append(String.format("\"%s\",", role.name()));
            }
            build.deleteCharAt(build.length() - 1).append("]}");
            return build.toString();
        };
    }
}
