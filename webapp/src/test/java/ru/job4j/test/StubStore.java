package ru.job4j.test;

import ru.job4j.user_store.IUserStore;
import ru.job4j.user_store.Role;
import ru.job4j.user_store.User;

import java.util.HashMap;
import java.util.Iterator;
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

    /**
     * RoleStore.
     */
    private Map<String, Role> roles = new HashMap<>();

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
        if (this.users.get(login) != null) {
            this.users.replace("Login", new User(login, name, email, 0));
        } else {
            throw new RuntimeException("not element User with login:" + login);
        }
    }

    @Override
    public Role getUserRole(String login) {
        Role role = this.roles.get(login);
        return role != null ? role : Role.DefaultUser;
    }

    @Override
    public void setUserRole(String login, Role role) {
        if (role != null && role != Role.DefaultUser) {
            this.roles.put(login, role);
        } else {
            this.roles.remove(login);
        }
    }

    @Override
    public Iterator<User> iterator() {
        return getUsers().iterator();
    }
}
