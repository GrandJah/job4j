package ru.job4j.webtest.dao;

import ru.job4j.webtest.model.MusicType;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO MusicType class.
 */
public class MusicTypeDao extends AbstractModelDao<MusicType> {
    /**
     * Create DAO music type.
     */
    MusicTypeDao() {
        super("musictypes");
    }

    /** Create MusicType Object.
     * @param musictype music type
     * @return MusicType Object
     */
    public MusicType create(String musictype) {
        return insert(new MusicType(0, musictype));
    }

    @Override
    Object[] params(MusicType entity) {
        return new Object[]{entity.getType()};
    }

    @Override
    MusicType convert(ResultSet rs) throws SQLException {
        return new MusicType(rs.getInt("id"), rs.getString("type"));
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
