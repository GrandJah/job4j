package ru.job4j.user_store;

import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * DataBase class.
 */
public class DB {

    /**
     * Singleton instance.
     */
    private static DB instance = new DB();

    /**
     * Connection pool.
     */
    private static PoolingDataSource pool;

    /**
     * @return instance
     */
    public static DB getInstance() {
        return instance;
    }


    /**
     * Create connection's pool.
     */
    private DB() {
//        Class.forName("org.postgresql.Driver");
        new PoolableConnectionFactory(
                new DriverManagerConnectionFactory(
                        "jdbc:postgresql://localhost:5432/job4j",
                        "postgres", "postgres"),
                new GenericObjectPool() { {
//                    new PoolingDriver().registerPool("user_store", this);
                    DB.pool = new PoolingDataSource(this);
                } }, null, null, false, false);
        goDB(new BufferedReader(new InputStreamReader(getClass()
                .getResourceAsStream("/sql/user_store.sql")))
                .lines().collect(Collectors.joining(" ")));
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
    public boolean goDB(String query, Function<ResultSet, Void> function, Object ... params) {
        boolean success = false;
//        try (Connection connection = DriverManager.getConnection("jdbc:apache:commons:dbcp:user_store")) {
        try (Connection connection = DB.pool.getConnection()) {

            try (PreparedStatement db = connection.prepareStatement(query)) {
                ParameterMetaData meta = db.getParameterMetaData();
                for (int i = 1; i <= params.length; i++) {
                    db.setObject(i, params[i - 1], meta.getParameterType(i));
                }
                try {
                    db.execute();
                    connection.commit();
                    if (function != null) {
                        function.apply(db.getResultSet());
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
