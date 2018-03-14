package ru.job4j.webtest.dao;

import ru.job4j.webtest.dao.db.DataBasePool;
import ru.job4j.webtest.model.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;

/** DAO.
 * @param <E> type model DAO
 */
public abstract class AbstractModelDao<E extends Model> {
    /**
     * Data base connection.
     */
    private static final DataBasePool DB = new DataBasePool();

    /**
     * Table name db.
     */
    private final String tableName;

    /**
     * @param tableName Table name db
     */
    AbstractModelDao(String tableName) {
        this.tableName = tableName;
    }

    /** insert entry entity
     * NOTE: Order params. id = 0
     * @param entity insert entity.
     * @return entity with id.
     */
    final E insert(E entity) {
        if (entity.getId() == 0) {
            DB.goDB(String.format("insert into %s %s RETURNING id", this.tableName,
                    getQueryInsertPartitionFieldAndValues()),
                    rs -> {
                        while (rs.next()) {
                            entity.setID(rs.getInt("id"));
                        }
                    }, params(entity));
        } else {
            throw new UnsupportedOperationException();
        }
        return entity;
    }

    /** get all entity.
     * @return Collection all entity
     */
    public final Collection<E> readAll() {
        final LinkedList<E> list = new LinkedList<>();
        DB.goDB(String.format("select * from %s ", this.tableName),
                rs -> {
                    while (rs.next()) {
                        list.add(convert(rs));
                    }
                });
        return list;
    }

    /** get entity by id.
     * @param id id
     * @return entity
     */
    public final E read(int id) {
        final AtomicReference<E> entity = new AtomicReference<>();
        DB.goDB(String.format("select * from %s where id = ?", this.tableName),
                rs -> {
                    while (rs.next()) {
                        entity.set(convert(rs));
                    }
                }, id);
        return entity.get();
    }

    /** update entity by id.
     * @param entity entity.
     * @return true if update success
     */
    public final boolean update(E entity) {
        Object[] params = params(entity);
        int len = params.length;
        params = Arrays.copyOf(params, len + 1);
        params[len] = entity.getId();
        return DB.goDB(String.format("update %s set %s where id = ?", this.tableName,
                getQueryUpdatePartitionSet()), params);
    }

    /** delete entity.
     * @param id entity id
     * @return true if success.
     */
    public final boolean delete(int id) {
        return DB.goDB(String.format("delete from %s where id = ?", this.tableName), id);
    }

    /** method return set parameters.
     * NOTE: Order params.
     * @param entity entity.
     * @return set parameters
     */
    abstract Object[] params(E entity);

    /** method convert rs to entity.
     * @param rs ResultSet
     * @return entity
     * @throws SQLException exception
     */
    abstract E convert(ResultSet rs) throws SQLException;

    /**
     * NOTE: Order params.
     * @return query string for Create.
     */
    abstract String getQueryInsertPartitionFieldAndValues();

    /**
     * NOTE: Order params.
     * @return query string for Update.
     */
    abstract String getQueryUpdatePartitionSet();
}
