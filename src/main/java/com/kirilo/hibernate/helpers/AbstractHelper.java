package com.kirilo.hibernate.helpers;

import com.kirilo.hibernate.JPAUtil;
import com.kirilo.hibernate.exceptions.CloseEntityManagerException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public abstract class AbstractHelper<T> implements EntityHelper<T>, AutoCloseable {
    private final EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public AbstractHelper() {
        entityManagerFactory = JPAUtil.getEntityManagerFactory();
    }


    public EntityManager getEntityManager(){
        entityManager = entityManagerFactory.createEntityManager();
        return entityManager;
    }

    @Override
    public void close() throws CloseEntityManagerException {
        System.out.println("EntityManager closed!");
        if (entityManager != null) {
            entityManager.close();
        }
    }
}
