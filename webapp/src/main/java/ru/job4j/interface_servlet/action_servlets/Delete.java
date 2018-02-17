package ru.job4j.interface_servlet.action_servlets;

import ru.job4j.user_store.Role;
import ru.job4j.user_store.IUserStore;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 14.01.2018
 */
public class Delete extends AbstractActionServlets {
    /**
     * Default constructor.
     */
    public Delete() {
        super();
    }

    /** Main Constructor.
     * @param users User store.
     */
    public Delete(IUserStore users) {
        super(users);
    }

    @Override
    void doAction(String login, String name, String email, Role role) {
        getUsers().deleteUser(login);
    }
}
