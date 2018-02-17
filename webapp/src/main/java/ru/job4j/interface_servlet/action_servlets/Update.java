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
public class Update extends AbstractActionServlets {
    /**
     * Default constructor.
     */
    public Update() {
        super();
    }

    /** Main Constructor.
     * @param users User store.
     */
    public Update(IUserStore users) {
        super(users);
    }

    @Override
    void doAction(String login, String name, String email, Role role) {
        getUsers().updateUser(login, name, email);
        if (role != null) {
            Role.setRole(login, role);
        }
    }
}
