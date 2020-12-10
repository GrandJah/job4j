package ru.job4j.tracker;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import ru.job4j.res.Configure;
import ru.job4j.tracker.expire.Item;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 19.12.2017
 */
public class TrackerDB implements Tracker, Closeable {

   /**
    * Соединение с БД.
    */
   private Connection connection;

   /**
    * Оператор для запросов к БД.
    */
   private Statement db;

   /**
    * Конфигурация.
    */
   private Configure conf;

   /**
    * Конфигурация по умолчанию.
    */
   public TrackerDB() {
      this(null);
   }

   /**
    * @param config Путь к файлу конфигурации
    */
   public TrackerDB(String config) {
      initDB(config);
      checkDB();
   }

   /**
    * Проверка БД на корректность полей.
    */
   private void checkDB() {
      try {
         db.executeUpdate(this.conf.get("SQL_create_table"));
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   /**
    * Подключаемся к базе.
    *
    * @param config путь к файлу конфигурации.
    */
   private void initDB(String config) {
      if (this.connection == null) {
         this.conf = new Configure(config);
         try {
            this.connection = DriverManager
             .getConnection("jdbc:" + this.conf.get("url"), this.conf.get("user"),
              this.conf.get("password"));
         } catch (SQLException e) {
            this.connection = null;
            e.printStackTrace();
         }
      }
      if (this.connection != null) {
         try {
            this.db = this.connection.createStatement();
         } catch (SQLException e) {
            this.db = null;
            try {
               this.connection.close();
            } catch (SQLException e1) {
               e1.printStackTrace();
            }
            this.connection = null;
            e.printStackTrace();
         }
      }
   }


   /**
    * Отключаемся от базы.
    */
   public void close() {
      if (this.db != null) {
         this.db = null;
      }
      if (this.connection != null) {
         try {
            this.connection.close();
         } catch (SQLException e) {
            e.printStackTrace();
         } finally {
            this.connection = null;
         }
      }

   }

   @Override
   protected void finalize() {
      this.close();
   }

   /**
    * Проверка валидности параметра.
    *
    * @param item item
    * @throws ErrorValue ошибка параметра
    */
   private void valid(Item item) throws ErrorValue {
      if (item == null) {
         throw new ErrorValue();
      }
   }

   @Override
   public Item add(Item item) throws ErrorValue {
      valid(item);
      try {
         this.db.executeUpdate(String
          .format(this.conf.get("set_item"), item.getId(), item.getName(), item.getDescription(),
           item.getCreated().toString()));
         if (item.getComments() != null) {
            for (String comment : item.getComments()) {
               this.db
                .executeUpdate(String.format(this.conf.get("set_comment"), item.getId(), comment));
            }
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return item;
   }

   @Override
   public ArrayList<Item> getAll() {
      ArrayList<Item> ret = new ArrayList<>();
      try (Statement db1 = this.connection.createStatement()) {
         try (ResultSet rs = this.db.executeQuery(this.conf.get("get_all"))) {
            while (rs.next()) {
               ResultSet cr = db1
                .executeQuery(String.format(this.conf.get("get_comments"), rs.getString("id")));
               ret.add(Item.convertFromDB(rs, cr));
            }
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return ret;
   }

   @Override
   public Item findById(Integer id) throws NotFound {
      Item item = null;
      try {
         try (ResultSet rs = this.db
          .executeQuery(String.format(this.conf.get("get_tracker_id"), id))) {
            item = getItemFromRS(rs);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return item;
   }

   @Override
   public Item findById(String id) throws NotFound {
      return findById(Integer.valueOf(id));
   }

   @Override
   public void update(Item item) throws ErrorValue {
      valid(item);
      try {
         this.db.executeUpdate(String
          .format(this.conf.get("update_id"), item.getId(), item.getName(), item.getDescription(),
           item.getCreated().toString()));
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   @Override
   public void delete(Item byId) throws ErrorValue {
      valid(byId);
      try {
         this.db.executeUpdate(String.format(this.conf.get("delete_id"), byId.getId()));
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   @Override
   public Item findByName(String name) throws NotFound {
      Item item = null;
      try {
         try (ResultSet rs = this.db
          .executeQuery(String.format(this.conf.get("get_tracker_name"), name))) {
            item = getItemFromRS(rs);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return item;

   }

   /**
    * Сборка Item.
    *
    * @param rs результат запроса
    * @return Item
    * @throws NotFound     элементы не найдены
    * @throws SQLException SQLException
    */
   private Item getItemFromRS(ResultSet rs) throws NotFound, SQLException {
      Item item;
      if (rs != null && rs.next()) {
         try (Statement dbTemp = this.connection.createStatement()) {
            try (ResultSet cr = dbTemp
             .executeQuery(String.format(this.conf.get("get_comments"), rs.getString("id")))) {
               item = Item.convertFromDB(rs, cr);
            }
         }
      } else {
         throw new NotFound();
      }
      return item;
   }
}
