package ru.job4j.sell_car.storage.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.function.Function;

public abstract class HbmStorage {

   private final SessionFactory sf = Connect.sf();

   protected <T> T query(Function<Session, T> function) {
      Session session = this.sf.openSession();
      Transaction tx = session.beginTransaction();
      try {
         T result = function.apply(session);
         tx.commit();
         return result;
      } catch (final Exception e) {
         tx.rollback();
         throw e;
      } finally {
         session.close();
      }
   }

   static class Connect implements AutoCloseable {

      private static final Connect CONNECT = new Connect();

      private Connect() {
         System.out.println("Вызван конструктор Connect");
      }

      public static SessionFactory sf() {
         return CONNECT.sf;
      }

      private final StandardServiceRegistry registry =
       new StandardServiceRegistryBuilder().configure().build();
      private final SessionFactory sf
       = new MetadataSources(registry).buildMetadata().buildSessionFactory();

      @Override
      public void close() {
         StandardServiceRegistryBuilder.destroy(this.registry);
      }
   }
}


