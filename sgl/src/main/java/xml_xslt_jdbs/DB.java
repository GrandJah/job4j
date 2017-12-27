package xml_xslt_jdbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Function;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 26.12.2017
 */
public class DB {
    /**
     * Url базы.
     */
    private String dbUrl;

    /**
     * @param dbUrl url DB.
     */
    DB(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    /**
     * Работа с БД.
     * @param function функция для работы с БД.
     */
    public void goDB(Function<Statement, SQLException> function) {
        try (Connection conn = DriverManager.getConnection("jdbc:" + this.dbUrl)) {
            if (conn == null) {
                throw new SQLException("Нет соединения с БД!");
            } else {
                try (Statement db = conn.createStatement()) {
                    SQLException e = function.apply(db);
                    if (e != null) {
                        throw e;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
