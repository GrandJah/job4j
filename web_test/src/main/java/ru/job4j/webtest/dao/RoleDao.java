package ru.job4j.webtest.dao;

import ru.job4j.webtest.model.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO role class.
 */
public class RoleDao extends AbstractModelDao<Role> {
    /**
     * Create DAO role.
     */
    public RoleDao() {
        super("roles");
    }

    /** Create Role Object.
     * @param name name Role
     * @return Role
     */
    public Role create(String name) {
        return insert(new Role(0, name));
    }

    @Override
    Object[] params(Role entity) {
        return new Object[]{entity.getName()};
    }

    @Override
    Role convert(ResultSet rs) throws SQLException {
        return new Role(rs.getInt("id"), rs.getString("name"));
    }

    @Override
    String getQueryInsertPartitionFieldAndValues() {
        return "(name) values (?)";
    }

    @Override
    String getQueryUpdatePartitionSet() {
        return "name = ?";
    }
}
