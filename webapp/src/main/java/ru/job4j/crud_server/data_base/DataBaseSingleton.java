package ru.job4j.crud_server.data_base;

import ru.job4j.store.db.DBInterface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

/**
 * DBInterface with single connection.
 */
public class DataBaseSingleton implements DBInterface {

    /**
     * DataBaseSingleton connection.
     */
    private Connection connection;

    /**
     * Default Constructor.
     */
    private DataBaseSingleton() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/job4j",
                    "postgres", "postgres");
            this.connection.setAutoCommit(false);
            goDB("CREATE TABLE if not exists Users_store (login VARCHAR(30) PRIMARY KEY UNIQUE, name VARCHAR(50), email VARCHAR(30), created TIMESTAMP)");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Singleton Object.
     */
    private static final DBInterface DB_INTERFACE = new DataBaseSingleton();

    /**
     * @return DBInterface
     */
    public static DBInterface getDBInterface() {
        return DB_INTERFACE;
    }

    @Override
    public boolean goDB(String query, Object... params) {
        return goDB(query, null, params);
    }

    @Override
    public boolean goDB(String query, Function<ResultSet, Void> function, Object... params) {
        boolean success = false;
        try (PreparedStatement st = this.connection.prepareStatement(query)) {
            ParameterMetaData meta = st.getParameterMetaData();
            for (int i = 1; i <= params.length; i++) {
                st.setObject(i, params[i - 1], meta.getParameterType(i));
            }
            try {
                st.execute();
                this.connection.commit();
                ResultSet rs = st.getResultSet();
                if (function != null && rs != null) {
                    function.apply(rs);
                }
                success = true;
            } catch (SQLException e) {
                this.connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
}
