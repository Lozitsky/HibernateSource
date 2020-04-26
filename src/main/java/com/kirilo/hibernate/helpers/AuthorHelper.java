package com.kirilo.hibernate.helpers;

import com.kirilo.hibernate.entities.Author;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

// https://www.boraji.com/hibernate-5-jpa-2-configuration-example
// https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#architecture

public class AuthorHelper extends AbstractHelper<Author> {

    public AuthorHelper() {
        super();
    }

    @Override
    public List<Author> getEntityList() {
        final EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
//        final Author reference =
                entityManager.getReference(Author.class, 1L);
//        System.out.println(reference);
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);
        final Root<Author> root = criteriaQuery.from(Author.class);
        criteriaQuery.select(root);

// https://discuss.gradle.org/t/annotation-processor-org-hibernate-jpamodelgen-jpametamodelentityprocessor-not-found/30823/4
// https://stackoverflow.com/questions/54218556/how-to-generate-jpa-metamodel-with-gradle-5-x
// https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#criteria-typedquery

//        criteriaQuery.where(criteriaBuilder.equal(root.get(Author_.name), "John"));
        final List<Author> authors = entityManager.createQuery(criteriaQuery).getResultList();

        entityManager.getTransaction().commit();

        return authors;
    }

}
