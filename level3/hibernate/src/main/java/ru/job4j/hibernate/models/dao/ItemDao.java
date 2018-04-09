package ru.job4j.hibernate.models.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.job4j.hibernate.models.Item;

import java.io.Serializable;
import java.util.List;

/**
 * Item DAO class.
 */
public class ItemDao {
    /**
     * Hibernate session factory.
     */
    private static final SessionFactory FACTORY = new Configuration().configure().buildSessionFactory();

    /**
     * @return all items.
     */
    public static List<Item> getAll() {
        List<Item> list;
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            list = session.createQuery("from Item", Item.class).list();
            session.getTransaction().commit();
        }
        return list;
    }

    /** Save new Item.
     * @param item item
     * @return item
     */
    public static Item create(Item item) {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            Serializable id = session.save(item);
            session.getTransaction().commit();
            item = session.get(Item.class, id);
        }
        return item;
    }
}
