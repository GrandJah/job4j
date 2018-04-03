package ru.job4j.sell_car;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.Serializable;

/**
 * Abstract hibernate.
 */
public class Hibernate implements AutoCloseable {
    /**
     * Hibernate session factory.
     */
    private static final SessionFactory FACTORY = new Configuration().configure().buildSessionFactory();

    /**
     * session hibernate.
     */
    private Session session;

    /**
     * Default constructor.
     */
    public Hibernate() {
            this.session = FACTORY.openSession();
            this.session.beginTransaction();
    }

    @Override
    public void close() {
        this.session.getTransaction().commit();
        this.session.close();
    }

    /** save object into DB.
     * @param object object
     * @return new id.
     */
    public Serializable save(Object object) {
        return this.session.save(object);
    }


    /** query from hibernate.
     * @param hibernateQuery query hibernate
     * @param type type generic
     * @param <T> type generic
     * @return query answer
     */
    public <T> Query<T> createQuery(String hibernateQuery, Class<T> type) {
        return this.session.createQuery(hibernateQuery, type);
    }
}
