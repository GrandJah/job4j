package ru.job4j.data_base.action_servlets;

import ru.job4j.data_base.model.Role;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 14.01.2018
 */
public class Update extends AbstractActionServlets {

    @Override
    void doAction(String login, String name, String email, Role role) {
        USERS.updateUser(login, name, email);
        if (role != null) {
            ROLES.setUserRole(login, role);
        }
    }
}
