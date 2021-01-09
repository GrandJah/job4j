package ru.job4j.integration;

import static org.junit.Assert.assertEquals;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

public class OrdersStoreTest {
   private final BasicDataSource pool = new BasicDataSource();
   private static int serialDB = 0;

   @Before
   public void setUp() throws SQLException {
      pool.setDriverClassName("org.hsqldb.jdbcDriver");
      pool.setUrl(String.format("jdbc:hsqldb:mem:%s;sql.syntax_pgs=true;", serialDB++));
      pool.setUsername("sa");
      pool.setPassword("");
      pool.setMaxTotal(2);
      StringBuilder builder = new StringBuilder();
      try (BufferedReader br = new BufferedReader(
       new InputStreamReader(new FileInputStream("./db/update_001.sql")))) {
         br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
      } catch (IOException e) {
         e.printStackTrace();
      }
      pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
   }

   @Test
   public void whenSaveOrderAndFindAllOneRowWithDescription() {
      OrdersStore store = new OrdersStore(pool);

      store.save(Order.of("name1", "description1"));

      List<Order> all = (List<Order>) store.findAll();

      assertEquals(all.size(), 1);
      assertEquals(all.get(0).getDescription(), "description1");
      assertEquals(all.get(0).getId(), 1);
   }

   @Test
   public void whenSaveOrderAndUpdateDescriptionAndFindAllOneRowWithDescription() {
      OrdersStore store = new OrdersStore(pool);

      Order order = store.save(Order.of("name1", "description1"));
      order.setDescription("description2");
      order.setName("name2");
      store.updateOrder(order);

      List<Order> all = (List<Order>) store.findAll();

      assertEquals(all.size(), 1);
      assertEquals(all.get(0).getDescription(), "description2");
      assertEquals(all.get(0).getName(), "name2");
      assertEquals(all.get(0).getId(), 1);
   }

   @Test
   public void whenSaveRandomOrderAndFindByNameAndById() {
      OrdersStore store = new OrdersStore(pool);

      Order order = store.save(Order.of("name_search", "description_search"));
      int mid = (int) (Math.random() * 15);
      for (int i = 0; i < mid * 2; i += 1) {
         store.save(Order.of("name" + i, "description" + i));
      }
      store.updateOrder(order);

      Order findByNameOrder = store.findByName("name_search");
      Order findByIdOrder = store.findById(order.getId());


      assertEquals(findByNameOrder.getName(), "name_search");
      assertEquals(findByNameOrder.getDescription(), "description_search");
      assertEquals(findByNameOrder.getId(), findByIdOrder.getId());
      assertEquals(findByIdOrder.getName(), "name_search");
      assertEquals(findByIdOrder.getDescription(), "description_search");
      System.out.println(store.findAll());

   }
}
