package ru.job4j.webtest.dao;

import ru.job4j.webtest.model.MusicTypeModel;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO MusicTypeModel class.
 */
public class MusicTypeDao extends AbstractModelDao<MusicTypeModel> {
    /**
     * Create DAO music type.
     */
    MusicTypeDao() {
        super("musictypes");
    }

    /** Create MusicTypeModel Object.
     * @param musictype music type
     * @return MusicTypeModel Object
     */
    public MusicTypeModel create(String musictype) {
        return insert(new MusicTypeModel(0, musictype));
    }

    @Override
    Object[] params(MusicTypeModel entity) {
        return new Object[]{entity.getType()};
    }

    @Override
    MusicTypeModel convert(ResultSet rs) throws SQLException {
        return new MusicTypeModel(rs.getInt("id"), rs.getString("type"));
    }

    @Override
    String getQueryInsertPartitionFieldAndValues() {
        return "(type) values (?)";
    }

    @Override
    String getQueryUpdatePartitionSet() {
        return "type = ?";
    }
}
