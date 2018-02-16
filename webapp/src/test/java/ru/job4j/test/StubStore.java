package ru.job4j.test;

import ru.job4j.user_store.IUserStore;
import ru.job4j.user_store.Role;
import ru.job4j.user_store.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stub UserStore.
 */
public class StubStore implements IUserStore {
    /**
     * UserStore.
     */
    private Map<String, User> users = new HashMap<>();

    @Override
    public User getUser(String login) {
        User user = this.users.get(login);
        return user != null ? user : User.UNKNOWN;
    }

    @Override
    public List<User> getUsers(String... logins) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addUser(String login, String name, String email) {
        this.users.put(login, new User(login, name, email, 0));
        return true;
    }

    @Override
    public void deleteUser(String login) {
        this.users.remove(login);
    }

    @Override
    public void updateUser(String login, String name, String email) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Role getRole(String login) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setUser(String login, Role role) {
        throw new UnsupportedOperationException();
    }
}
