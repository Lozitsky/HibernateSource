package com.kirilo.hibernate;

import com.kirilo.hibernate.exceptions.CloseEntityManagerFactoryException;
import org.apache.log4j.Logger;


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

// https://www.boraji.com/hibernate-5-jpa-2-configuration-example

public class JPAUtil implements AutoCloseable {
    private static final String PERSISTENCE_UNIT_NAME = "PERSISTENCE";
    private static final Logger LOG = Logger.getLogger(JPAUtil.class);
    private static EntityManagerFactory factory;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return factory;
    }

    public static void shutdown() {
        LOG.debug("EntityManagerFactory closed!");
        if (factory != null) {
            factory.close();
        }
    }

    @Override
    public void close() throws CloseEntityManagerFactoryException {
        shutdown();
    }
}
