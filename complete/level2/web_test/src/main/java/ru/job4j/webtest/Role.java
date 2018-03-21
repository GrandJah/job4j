package ru.job4j.webtest;

import ru.job4j.webtest.dao.RoleModelDao;
import ru.job4j.webtest.model.RoleModel;

import java.util.LinkedList;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        return this == o || (o != null && getClass() == o.getClass() && Objects.equals(this.role, ((Role) o).role));
    }

    @Override
    public int hashCode() {
        return this == Role.EMPTY ? 0 : this.role.hashCode();
    }

    /** crate new role.
     * @param name role name
     * @return new Role
     */
    public static Role newRole(String name) {
        if (valueOf(name) != Role.EMPTY) {
            throw new IllegalArgumentException();
        }
        return new Role(Role.DAO.create(name));
    }

    /** get all Users use this Role.
     * @return users
     */
    public User[] getAllUsers() {
        return User.findByFild(null, this, null);
    }
}

