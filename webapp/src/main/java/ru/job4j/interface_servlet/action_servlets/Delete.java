package ru.job4j.interface_servlet.action_servlets;

import ru.job4j.user_store.Role;
import ru.job4j.user_store.UserStore;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 14.01.2018
 */
public class Delete extends AbstractActionServlets {
    @Override
    void doAction(String login, String name, String email, Role role) {
        UserStore.getStore().deleteUser(login);
    }
}
