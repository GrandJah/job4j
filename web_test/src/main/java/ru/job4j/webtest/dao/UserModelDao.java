package ru.job4j.webtest.dao;

import ru.job4j.webtest.model.UserModel;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User DAO class.
 */
public class UserModelDao extends AbstractModelDao<UserModel> {
    /**
     * Create DAO user.
     */
    public UserModelDao() {
        super("users");
    }

    /** Create UserModel Object.
     * @param login login UserModel
     * @param refAddress reference address model
     * @return UserModel
     */
    public UserModel create(String login, int refAddress) {
        return insert(new UserModel(0, login, refAddress));
    }

    @Override
    Object[] params(UserModel entity) {
        return new Object[]{entity.getLogin(), entity.getAddress()};
    }

    @Override
    UserModel convert(ResultSet rs) throws SQLException {
        return new UserModel(rs.getInt("id"), rs.getString("login"), rs.getInt("address"));
    }

    @Override
    String getQueryInsertPartitionFieldAndValues() {
        return "(login, address) values (?,?)";
    }

    @Override
    String getQueryUpdatePartitionSet() {
        return "login = ?, address = ?";
    }
}
