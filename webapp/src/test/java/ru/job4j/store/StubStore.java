package ru.job4j.store;

import ru.job4j.store.model.Role;
import ru.job4j.store.model.User;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Stub UserStore.
 */
public class StubStore implements IUserStore, IRoleStore {
    /**
     * UserStore.
     */
    private static Map<String, User> users = new HashMap<>();

    /**
     * RoleStore.
     */
    private static Map<String, Role> roles = new HashMap<>();

    @Override
    public User getUser(String login) {
        User user = StubStore.users.get(login);
        return user != null ? user : User.UNKNOWN;
    }

    @Override
    public List<User> getUsers(String... logins) {
        List<User> users;
        if (logins == null || logins.length == 0) {
            users = new ArrayList<>(StubStore.users.values());
        } else {
            throw new RuntimeException("UnSupport logins... args");
        }
        return users;
    }

    @Override
    public boolean addUser(String login, String name, String email) {
        return StubStore.users.putIfAbsent(login, new User(login, name, email, 0)) == null;
    }

    @Override
    public void deleteUser(String login) {
        StubStore.users.remove(login);
    }

    @Override
    public void updateUser(String login, String name, String email) {
        if (StubStore.users.get(login) != null) {
            StubStore.users.replace("Login", new User(login, name, email, 0));
        } else {
            throw new RuntimeException("not element User with login:" + login);
        }
    }

    @Override
    public Role getUserRole(String login) {
        Role role = StubStore.roles.get(login);
        return role != null ? role : Role.DEFAULT_USER;
    }

    @Override
    public void setUserRole(String login, Role role) {
        if (role != null && role != Role.DEFAULT_USER) {
            StubStore.roles.put(login, role);
        } else {
            StubStore.roles.remove(login);
        }
    }

    @Override
    public List<Role> getRoles() {
        return new LinkedList<>(roles.values());
    }

    @Override
    public Iterator<User> iterator() {
        return getUsers().iterator();
    }

    /** Stub Interface Store.
     * @param stubObject object use interface
     * @param <E> type object
     * @return  object with stub interface
     */
    public static <E> E stub(E stubObject) {
        Class clazz = stubObject.getClass();
        while (clazz != Object.class) {
            try {
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    Class fieldType = field.getType();
                    if (fieldType.isInterface()) {
                        if (fieldType == IUserStore.class || fieldType == IRoleStore.class) {
                            field.setAccessible(true);
                            Field modifiersField = Field.class.getDeclaredField("modifiers");
                            modifiersField.setAccessible(true);
                            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL & ~Modifier.PRIVATE);
                            field.set(stubObject, new StubStore());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            clazz = clazz.getSuperclass();
        }
        return stubObject;
    }
}
