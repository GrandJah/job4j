package ru.job4j.webtest.dao;

import ru.job4j.webtest.model.RoleModel;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO role class.
 */
public class RoleDao extends AbstractModelDao<RoleModel> {
    /**
     * Create DAO role.
     */
    public RoleDao() {
        super("roles");
    }

    /** Create RoleModel Object.
     * @param name name RoleModel
     * @return RoleModel
     */
    public RoleModel create(String name) {
        return insert(new RoleModel(0, name));
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
}
