package ru.job4j.sell_car;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.io.Serializable;
import java.util.List;

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
     * @return list answer
     */
    public <T> List<T> getAll(String hibernateQuery, Class<T> type) {
        return this.session.createQuery(hibernateQuery, type).getResultList();
    }

    /**
     * @param hibernateQuery query hibernate
     * @param type type generic
     * @param params params query
     * @param <T> type generic
     * @return single result or null
     */
    public <T> T getSingle(String hibernateQuery, Class<T> type, Object... params) {
        T answer;
        try {
            Query<T> query = this.session.createQuery(hibernateQuery, type);
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i, params[i]);
            }
            answer = query.getSingleResult();
        } catch (NoResultException e) {
            answer = null;
        }
        return answer;
    }
}
