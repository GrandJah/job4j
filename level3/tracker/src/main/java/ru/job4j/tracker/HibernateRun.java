package ru.job4j.tracker;

import java.util.List;
import java.util.function.Function;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateRun implements AutoCloseable {
  private final StandardServiceRegistry registry;

  private final SessionFactory sf;

  private static HibernateRun APP = new HibernateRun();

  HibernateRun() {
    this.registry = new StandardServiceRegistryBuilder().configure().build();
    this.sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
  }

  @Override
  public void close() {
    StandardServiceRegistryBuilder.destroy(this.registry);
  }

  public static void main(String[] args) {
    APP.createEntries();
    APP.testRun();
  }

  public void createItemEntry(String name, String description) {
    Item item = new Item();
    item.setName(name);
    item.setDescription(description);
    create(item);
  }

  private void createEntries() {
    createItemEntry("First", "First description");
    createItemEntry("Second", "Second description");
    createItemEntry("Third", "Third description");
    createItemEntry("Fourth", "Fourth description");
    createItemEntry("Fifth", "Fifth description");
  }

  private void testRun() {
    Item item = create(new Item("Learn Hibernate"));
    System.out.println(item);

    item.setName("Learn Hibernate 5.");
    update(item);
    System.out.println(item);

    Item rsl = findById(item.getId());
    System.out.println(rsl);

    delete(rsl.getId());
    List<Item> list = findAll();
    for (Item it : list) {
      System.out.println(it);
    }
  }

  private <T> T action(Function<Session, T> function) {
    Session session = this.sf.openSession();
    session.beginTransaction();
    T result = function.apply(session);
    session.getTransaction().commit();
    session.close();
    return result;
  }

  public Item create(Item item) {
    return action(sf -> {
      sf.save(item);
      return item;
    });
  }

  private void update(Item item) {
    action(sf -> {
      sf.update(item);
      return null;
    });
  }

  private void delete(Integer id) {
    action(sf -> {
      Item item = new Item(null);
      item.setId(id);
      sf.delete(item);
      return null;
    });
  }

  private List<Item> findAll() {
    return action(sf -> (sf.createQuery("from ru.job4j.tracker.Item").list()));
  }

  public Item findById(Integer id) {
    return action(sf -> sf.get(Item.class, id));
  }
}
