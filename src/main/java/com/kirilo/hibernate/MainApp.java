package com.kirilo.hibernate;

import com.kirilo.hibernate.entities.Author;
import com.kirilo.hibernate.entities.Author_;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

// https://www.boraji.com/hibernate-5-jpa-2-configuration-example
// https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#architecture

public class MainApp {
    public static void main(String[] args) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);
        final Root<Author> root = criteriaQuery.from(Author.class);
        criteriaQuery.select(root);

// https://discuss.gradle.org/t/annotation-processor-org-hibernate-jpamodelgen-jpametamodelentityprocessor-not-found/30823/4
// https://stackoverflow.com/questions/54218556/how-to-generate-jpa-metamodel-with-gradle-5-x
// https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#criteria-typedquery

        criteriaQuery.where(criteriaBuilder.equal(root.get(Author_.name), "John"));
        final List<Author> authors = entityManager.createQuery(criteriaQuery).getResultList();
        authors.forEach(System.out::println);
        entityManager.getTransaction().commit();
        entityManager.close();

        JPAUtil.shutdown();
    }
}
