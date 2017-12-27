package ru.job4j.res;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.function.Function;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 25.12.2017
 */
public class DB {
    /**
     * Параметры конфигурации.
     */
    private Configure config;

    /**
     * @param config конфигурация
     */
    public DB(Configure config) {
        this.config = config;
    }

    /**
     * Работа с БД.
     * @param function функция для работы с БД.
     */
    private void goDB(Function<Statement, SQLException> function) {
        try (Connection conn = DriverManager.getConnection("jdbc:" + this.config.get("db_url"))) {
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

    /**
     * @return возвращает время последнего старта системы.
     */
    public long checkLastStart() {
        final long[] time = {new GregorianCalendar(new Date().getYear() + 1900, 0, 1).getTimeInMillis()};
//        final long[] time = {System.currentTimeMillis() - TimeUnit.DAYS.toMillis(366)};//инфа за последний год
        goDB(db -> {
            try {
                db.executeUpdate(this.config.get("start_db"));
                ResultSet rs = db.executeQuery(this.config.get("get_lastStart"));
                if (!rs.next()) {
                    db.executeUpdate(String.format(this.config.get("ins_lastStart"), new Timestamp(time[0]).toString()));
                } else {
                    time[0] = rs.getDate("created").getTime();
                }
                return null;
            } catch (SQLException e) {
                return e;
            }
        });
        return time[0];
    }

    /**
     * @param lastStart время на цстановку последнего старта.
     */
    public void setLastStart(long lastStart) {
        goDB(db -> {
            try {
                db.executeUpdate(this.config.get("del_dup"));
                db.executeUpdate(String.format(this.config.get("upd_lastStart"), new Timestamp(lastStart).toString()));
                return null;
            } catch (SQLException e) {
                return e;
            }
        });
    }

    /**Добавление вакансии в таблицу.
     * @param heading заголовок записи
     * @param description описание записи
     * @param date время записи
     */
    public void add(String heading, String description, Date date) {
        goDB(db -> {
            try {
                db.executeUpdate(String.format(this.config.get("ins_vacancy"),
                        heading.replace("'", "''"),
                        description.replace("'", "''"),
                        new Timestamp(date.getTime()).toString()));
                return null;
            } catch (SQLException e) {
                return e;
            }
        });
    }
}
