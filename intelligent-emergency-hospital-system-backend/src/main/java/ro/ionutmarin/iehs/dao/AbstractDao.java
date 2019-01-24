package ro.ionutmarin.iehs.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

/**
 * Created by ionut on 10/21/2017.
 */
public class AbstractDao {
    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void persist(Object entity) {
        getSession().saveOrUpdate(entity);
        getSession().flush();
    }

    public void delete(Object entity) {
        getSession().delete(entity);
    }
}

