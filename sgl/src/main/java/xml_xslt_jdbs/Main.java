package xml_xslt_jdbs;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

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
    private DB db;

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
     * @return db_interface db_interface
     */
    public DB getDB() {
        return this.db;
    }

    /**
     * @param db db_interface
     */
    public void setDb(DB db) {
        this.db = db;
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
        long start = System.currentTimeMillis();
        Console console = new Console();
        XML xml = new XML();
        Main main = new Main();

        main.setDb(new DB("sqlite:test.db_interface"));
        main.setNumberN(console.ask("Введите число: "));

        main.createAndFill();
        xml.createXML(main.getListFromDB());
        xml.convertXML();

        console.print("Sum : ");
        console.println(xml.parse());
        System.out.println((System.currentTimeMillis() - start) / 1000);
    }

    /**
     * Функция создания и заполнения БД.
     */
    public void createAndFill() {
        getDB().goDB(db -> {
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
    public List<String> getListFromDB() {
        List<String> list = new LinkedList<>();
        getDB().goDB(db -> {
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