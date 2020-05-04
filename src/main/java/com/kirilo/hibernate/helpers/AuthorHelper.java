package com.kirilo.hibernate.helpers;

import com.kirilo.hibernate.entities.Author;
import com.kirilo.hibernate.entities.Author_;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
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
//        entityManager.getTransaction().begin();
//        final Author reference =
//        entityManager.getReference(Author.class, 1L);
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

//        entityManager.getTransaction().commit();

        return authors;
    }

    public Author addAuthor(Author author) {
        final EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(author);
        entityManager.getTransaction().commit();

        return author;
    }

    public List<Author> getAuthorList(String... params) {
        final EntityManager entityManager = getEntityManager();
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);
        final Root<Author> root = criteriaQuery.from(Author.class);

        addSelectToCriteriaQuery(criteriaQuery, criteriaBuilder, root, params);

        final TypedQuery<Author> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public List<Author> getAuthorListForName(String name, String... params) {
        final EntityManager entityManager = getEntityManager();
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);
        final Root<Author> root = criteriaQuery.from(Author.class);
        final ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class, "name");

        addSelectToCriteriaQuery(criteriaQuery, criteriaBuilder, root, params)
                .where(criteriaBuilder.like(root.get(Author_.NAME), parameter));
        final TypedQuery<Author> query = entityManager.createQuery(criteriaQuery);
        query.setParameter("name", String.format("%%%s%%", name));
        return query.getResultList();
    }

    private CriteriaQuery<Author> addSelectToCriteriaQuery(CriteriaQuery<Author> criteriaQuery, CriteriaBuilder criteriaBuilder, Root<Author> root, String[] params) {
        Selection[] selections = new Selection[params.length];
        for (int i = 0; i < params.length; i++) {
            selections[i] = root.get(params[i]);
        }
        return criteriaQuery.select(criteriaBuilder.construct(Author.class, selections));
    }

    public boolean generateAndAddAuthors(int count) {
        final EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();

        for (int i = 0; i < count; i++) {
//            Author author = createAuthor("FirstName_" + i, "LastName_" + i);
            Author author = new Author();
            author.setName("FirstName_" + i);
            author.setSecondName("LastName_" + i);
            if (i % 20 == 0) {
                entityManager.flush();
            }
            entityManager.persist(author);
        }

        entityManager.getTransaction().commit();
        return true;
    }

    public Author createAuthor(String firstName, String lastName) {
        Author author = new Author();
        author.setName(firstName);
        author.setSecondName(lastName);
        return author;
    }

    public Author getAuthor(Long id) {
        final EntityManager entityManager = getEntityManager();
        final Author author = entityManager.getReference(Author.class, id);
        return author;
    }

    //    https://kodejava.org/how-do-i-update-entity-object-using-jpa/
    public void updateAuthor(Author author) {
        final EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(author);
        entityManager.getTransaction().commit();
    }


}
