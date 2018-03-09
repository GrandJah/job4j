package ru.job4j.data_base.action_servlets;

import ru.job4j.data_base.model.Role;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 14.01.2018
 */
public class Create extends AbstractActionServlets {

    @Override
    void doAction(String login, String name, String email, Role role) {
        USERS.addUser(login, name, email);
        ROLES.setUserRole(login, role);
    }
}
