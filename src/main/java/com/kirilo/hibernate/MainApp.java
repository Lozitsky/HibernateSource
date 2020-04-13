package com.kirilo.hibernate;

import javax.persistence.EntityManager;

// https://www.boraji.com/hibernate-5-jpa-2-configuration-example
public class MainApp {
    public static void main(String[] args) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        // Check database version
        String sql = "select version()";

        String result = (String) entityManager.createNativeQuery(sql).getSingleResult();
        System.out.println(result);

        entityManager.getTransaction().commit();
        entityManager.close();

        JPAUtil.shutdown();
    }
}
