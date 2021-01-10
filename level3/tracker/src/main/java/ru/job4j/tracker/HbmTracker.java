package ru.job4j.tracker;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;
import java.util.function.Function;

public class HbmTracker implements AutoCloseable, Store {
   private final StandardServiceRegistry registry;
   private final SessionFactory sf;

   public HbmTracker() {
      this.registry = new StandardServiceRegistryBuilder().configure().build();
      this.sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
   }

   @Override
   public void close() {
      StandardServiceRegistryBuilder.destroy(this.registry);
   }

   private <T> T query(Function<Session, T> function) {
      Session session = this.sf.openSession();
      session.beginTransaction();
      T result = function.apply(session);
      session.getTransaction().commit();
      session.close();
      return result;
   }

   @Override
   public Item add(Item item) {
      return query(sf -> {
         sf.save(item);
         return item;
      });
   }

   @Override
   public boolean replace(String id, Item item) {
      item.setId(Integer.valueOf(id));
      return query(sf -> {
         sf.update(item);
         return sf.get(Item.class, item.getId()).equals(item);
      });
   }

   @Override
   public boolean delete(String id) {
      Item item = new Item();
      item.setId(Integer.valueOf(id));
      return query(sf -> {
         sf.delete(item);
         return sf.get(Item.class, item.getId()) == null;
      });
   }

   @Override
   public List<Item> findAll() {
      return query(sf -> sf.createQuery("from Item", Item.class)
       .list());
   }

   @Override
   public List<Item> findByName(String key) {
      return query(sf -> sf.createQuery("from Item where name = :name", Item.class)
       .setParameter("name", key)
       .list());
   }

   @Override
   public Item findById(Integer id) {
      return query(sf -> sf.get(Item.class, id));
   }
}
