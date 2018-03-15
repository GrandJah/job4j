package ru.job4j.webtest.dao.db;

import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DataBaseSingleton class.
 */
public class DataBasePool {
    /**
     * Connection POOL.
     */
    private static final PoolingDataSource POOL = new PoolingDataSource();

    static {
        new PoolableConnectionFactory(
                new DriverManagerConnectionFactory("jdbc:postgresql://localhost:5432/webtest_test",
                        "postgres", "postgres"),
                new GenericObjectPool() { { POOL.setPool(this); } },
                null, null, false, false);
        new DataBasePool().goDB(resToString("/sql/create_tables.sql"));
    }

    /**
     * @param query query
     * @param params parameters
     * @return true if commit is success
     */
    public boolean goDB(String query, Object... params) {
        return goDB(query, null, params);
    }

    /**
     * @param query query
     * @param function function
     * @param params parameters
     * @return true if commit is success
     */
    public boolean goDB(String query, RS function, Object ... params) {
        boolean success = false;
        try (Connection connection = DataBasePool.POOL.getConnection()) {
            try (PreparedStatement db = connection.prepareStatement(query)) {
                if (params.length > 0) {
                    ParameterMetaData meta = db.getParameterMetaData();
                    for (int i = 1; i <= params.length; i++) {
                        db.setObject(i, params[i - 1], meta.getParameterType(i));
                    }
                }
                try {
                    db.execute();
                    ResultSet rs = db.getResultSet();
                    if (function != null) {
                        function.go(rs);
                    }
                    connection.commit();
                    success = true;
                } catch (SQLException e) {
                    e.printStackTrace();
                    connection.rollback();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    /** Resources o string.
     * @param urlResources resources path
     * @return string resource
     */
    public static String resToString(String urlResources) {
        final char[] buffer = new char[128];
        final StringBuilder out = new StringBuilder();
        InputStream stream = DataBasePool.class.getResourceAsStream(urlResources);
        try (Reader in = new InputStreamReader(stream, "UTF-8")) {
            for (;;) {
                int rsz = in.read(buffer, 0, buffer.length);
                if (rsz < 0) {
                    break;
                }
                out.append(buffer, 0, rsz);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    /**
     * Interface ResultSet Handler.
     */
    public interface RS {
        /**
         * @param rs result set.
         * @throws SQLException exception
         */
        void go(ResultSet rs) throws SQLException;
    }
}
