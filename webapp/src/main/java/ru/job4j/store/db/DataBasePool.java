package ru.job4j.store.db;

import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;
import ru.job4j.config.Configure;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

/**
 * DataBaseSingleton class.
 */
public class DataBasePool implements DBInterface {
    /**
     * Connection POOL.
     */
    private static final PoolingDataSource POOL = new PoolingDataSource();
    static {
        Configure config = new Configure("/db/pool.config");
        try {
            Class.forName(config.get("driverClass"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        new PoolableConnectionFactory(
                new DriverManagerConnectionFactory(config.get("connectUri"),
                        config.get("user_name"), config.get("password")),
                new GenericObjectPool() { { POOL.setPool(this); } },
                null, null, false, false);
    }

    @Override
    public boolean goDB(String query, Object... params) {
        return goDB(query, null, params);
    }

    @Override
    public boolean goDB(String query, Function<ResultSet, Void> function, Object ... params) {
        boolean success = false;
        try (Connection connection = DataBasePool.POOL.getConnection()) {
            try (PreparedStatement db = connection.prepareStatement(query)) {
                ParameterMetaData meta = db.getParameterMetaData();
                for (int i = 1; i <= params.length; i++) {
                    db.setObject(i, params[i - 1], meta.getParameterType(i));
                }
                try {
                    db.execute();
                    connection.commit();
                    ResultSet rs = db.getResultSet();
                    if (function != null && rs != null) {
                        function.apply(rs);
                    }
                    success = true;
                } catch (SQLException e) {
                    connection.rollback();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
}
