package ru.job4j.data_base.store;

import ru.job4j.data_base.model.Role;
import ru.job4j.data_base.model.User;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 09.03.2018
 */
public class UserStoreTest {

    public static void clear() {
        IUserStore store = new UserStore();
        IRoleStore roleStore = new RoleStore();
        for (User user : store.getUsers()) {
            if (user == User.UNKNOWN) {
                break;
            }
            roleStore.setUserRole(user.getLogin(), Role.DEFAULT_USER);
            store.deleteUser(user.getLogin());
        }
    }

}