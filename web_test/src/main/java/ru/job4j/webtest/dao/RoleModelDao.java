package ru.job4j.webtest.dao;

import ru.job4j.webtest.model.RoleModel;
import ru.job4j.webtest.model.UserModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * DAO role class.
 */
public class RoleModelDao extends AbstractModelDao<RoleModel> {
    /**
     * Create DAO role.
     */
    public RoleModelDao() {
        super("roles");
    }

    /** Create RoleModel Object.
     * @param name name RoleModel
     * @return RoleModel
     */
    public RoleModel create(String name) {
        return insert(new RoleModel(0, name));
    }

    /** find by field name.
     * @param name name
     * @return role
     */
    public Collection<RoleModel> findByName(String name) {
        return find("name = ?", name);
    }

    @Override
    Object[] params(RoleModel entity) {
        return new Object[]{entity.getName()};
    }

    @Override
    RoleModel convert(ResultSet rs) throws SQLException {
        return new RoleModel(rs.getInt("id"), rs.getString("name"));
    }

    @Override
    String getQueryInsertPartitionFieldAndValues() {
        return "(name) values (?)";
    }

    @Override
    String getQueryUpdatePartitionSet() {
        return "name = ?";
    }

    /** get all users uses role.
     * @param role role
     * @return users.
     */
    public Collection<UserModel> getAllUsers(RoleModel role) {
        return new UserModelDao().find("", role.getId());
    }
}
