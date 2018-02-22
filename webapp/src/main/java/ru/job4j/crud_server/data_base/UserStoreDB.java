package ru.job4j.crud_server.data_base;

import ru.job4j.store.UserStore;
import ru.job4j.store.model.User;

import java.util.List;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 02.01.2018
 */
public class UserStoreDB extends UserStore {
    static {
        setDb(DataBaseSingleton.getDBInterface());
    }

    @Override
    public User getUser(String login) {
        return super.getUsers(login).get(0);
    }

    @Override
    public List<User> getUsers(String... logins) {
        throw new UnsupportedOperationException();
    }
}