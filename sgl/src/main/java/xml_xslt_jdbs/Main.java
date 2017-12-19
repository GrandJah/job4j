package xml_xslt_jdbs;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 18.12.2017
 */
public class Main implements Serializable {

    /**
     * Секретное число.
     */
    private int numberN;

    /**
     * Имя Базы.
     */
    private String dbName;

    /**
     * @return number_n number_n
     */
    public int getNumberN() {
        return this.numberN;
    }

    /**
     * @param numberN number_n
     */
    public void setNumberN(int numberN) {
        this.numberN = numberN;
    }

    /**
     * @return dbName dbName
     */
    public String getDbName() {
        return this.dbName;
    }

    /**
     * @param dbName dbName
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }


    /**
     * Main.
     */
    public Main() {
    }

    /** Точка входа.
     * @param args args
     * @throws ClassNotFoundException ненайден драйвер БД
     */
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Console console = new Console();
        XML xml = new XML();
        Main main = new Main();

        main.setDbName("test");
        main.setNumberN(console.ask("Введите число: "));

        main.createAndFill();
        xml.createXML(main.getDB());
        xml.convertXML();

        console.print("Sum : ");
        console.println(xml.parse());
    }

    /**
     * Функция создания и заполнения БД.
     */
    public void createAndFill() {
        goDB(db -> {
            try {
                db.execute("DROP TABLE IF EXISTS TEST");
                db.execute("CREATE TABLE TEST(N INTEGER)");
                db.execute(createSQLQuery(getNumberN()));
                return null;
            } catch (SQLException e) {
                return e;
            }
        });
    }

    /**
     * функция запроса из БД.
     * @return Список полей
     */
    public List<String> getDB() {
        List<String> list = new LinkedList<>();
        goDB(db -> {
            try {
                ResultSet rs = db.executeQuery("SELECT N FROM TEST");
                if (rs != null) {
                    while (rs.next()) {
                        list.add(rs.getString("N"));
                    }
                }
                return null;
            } catch (SQLException e) {
                return e;
            }
        });
        return list;
    }

    /**
     * Работа с БД.
     * @param function функция для работы с БД.
     */
    public void goDB(Function<Statement, SQLException> function) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + getDbName() + ".db")) {
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
     * Создание SQL-запроса на создание N записей - 1...N.
     * @param n число N
     * @return запрос на вставку
     */
    private String createSQLQuery(int n) {
        StringBuilder sb = new StringBuilder("INSERT INTO TEST VALUES ");
        for (int i = 1; i < n; i++) {
            sb.append("(").append(i).append(")").append(",");
        }
        sb.append("(").append(n).append(")");
        return sb.toString();
    }
}