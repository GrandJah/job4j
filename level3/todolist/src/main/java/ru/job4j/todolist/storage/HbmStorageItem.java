package ru.job4j.todolist.storage;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;
import java.util.function.Function;
import ru.job4j.todolist.models.Item;

public class HbmStorageItem implements AutoCloseable, Storage<Item> {

   private final StandardServiceRegistry registry =
    new StandardServiceRegistryBuilder().configure().build();
   private final SessionFactory sf
    = new MetadataSources(registry).buildMetadata().buildSessionFactory();

   @Override
   public void close() {
      StandardServiceRegistryBuilder.destroy(this.registry);
   }

   private <T> T query(Function<Session, T> function) {
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

   @Override
   public List<Item> getAll() {
      return query(sf ->
       sf.createQuery("from Item", Item.class).list());
   }

   @Override
   public Item create(Item item) {
      return query(sf ->
       sf.get(Item.class, sf.save(item)));
   }

   @Override
   public boolean update(Integer id, Item item) {
      item.setId(id);
      return query(sf -> {
         sf.update(item);
         return sf.get(Item.class, item.getId()).equals(item);
      });
   }

   @Override
   public Item getById(Integer id) {
      return query(sf -> sf.get(Item.class, id));
   }
}
