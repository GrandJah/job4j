package ru.job4j.user_store.action_servlets;

import ru.job4j.user_store.Role;
import ru.job4j.user_store.UserStore;

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
        UserStore.getUserStore().addUser(login, name, email);
        Role.setRole(login, role);
    }
}
