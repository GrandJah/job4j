package ru.job4j.data_base.model;

import java.util.Hashtable;
import java.util.Map;

/**
 * Role Users.
 */
public class Role implements Comparable<Role> {
    /**
     * Use roles.
     */
    private static final Map<String, Role> ROLES = new Hashtable<>();

    /**
     * name Role.
     */
    private String name;

    /**
     * Default User.
     */
    public static final Role DEFAULT_USER = new Role("DEFAULT_USER");

    /**
     * ADMINISTRATOR.
     */
    public static final Role ADMINISTRATOR = new Role("ADMINISTRATOR");

    /**
     * @param name role name.
     */
    private Role(String name) {
        this.name = name;
        Role.ROLES.put(name, this);
    }

    /**
     * @return role name
     */
    public String name() {
        return this.name;
    }

    /**
     * @param name role name
     * @return Role
     */
    public static Role valueOf(String name) {
        Role role = Role.ROLES.get(name);
        if (role == null) {
            role = new Role(name);
        }
        return role;
    }

    @Override
    public int compareTo(Role o) {
        return this.name.compareTo(o.name);
    }
}


