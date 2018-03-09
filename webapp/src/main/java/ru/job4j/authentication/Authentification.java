package ru.job4j.authentication;

import ru.job4j.data_base.model.Role;
import ru.job4j.data_base.model.User;
import ru.job4j.data_base.store.RoleStore;

import javax.servlet.http.HttpSession;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 09.03.2018
 */
public class Authentification {
    public static boolean checkRules(HttpSession session, String rules) {
        boolean check = false;
        User user = (User) session.getAttribute("user");
        if (user != null) {
            switch (rules) {
                case "create" : check = new RoleStore().getUserRole(user.getLogin()) == Role.ADMINISTRATOR; break;
                default:break;
            }
        }
        return check;
    }
}
