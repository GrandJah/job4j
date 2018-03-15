package ru.job4j.webtest;

import ru.job4j.webtest.dao.RoleModelDao;
import ru.job4j.webtest.model.RoleModel;

import java.util.LinkedList;

/**
 * Class role.
 */
public class Role {
    /**
     * Empty object.
     */
    public static final Role EMPTY = new Role(null);

    /**
     * RoleModel.
     */
    private RoleModel role;


    /**
     * @return role id.
     */
    public int getId() {
        return this.role.getId();
    }

    /**
     * DAO.
     */
    private static final RoleModelDao DAO = new RoleModelDao();

    /**
     * @param role model role.
     */
    private Role(RoleModel role) {
        this.role = role;
    }

    /**
     * @param name name role.
     * @return Role for name
     */
    public static Role valueOf(String name) {
        LinkedList<RoleModel> list = new LinkedList<>(Role.DAO.findByName(name));
        return list.size() > 0 ? new Role(list.getFirst()) : Role.EMPTY;
    }

    /** crate new role.
     * @param name role name
     * @return new Role
     */
    public static Role newRole(String name) {
        if (valueOf(name) == Role.EMPTY) {
            throw new IllegalArgumentException();
        }
        return new Role(Role.DAO.create(name));
    }

    /** get all Users use Role.
     * @param role role
     * @return users
     */
    public User[] getAllUsers(Role role) {
        return User.findByFild(null, role, null);
    }
}

