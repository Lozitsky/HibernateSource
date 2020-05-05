package com.kirilo.hibernate.helpers;

import com.kirilo.hibernate.entities.Author;
import com.kirilo.hibernate.entities.Author_;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

// https://www.boraji.com/hibernate-5-jpa-2-configuration-example
// https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#architecture

public class AuthorHelper extends AbstractHelper<Author> {

    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    public AuthorHelper() {
        super();
        entityManager = getEntityManager();
        criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public List<Author> getEntityList() {
//        entityManager.getTransaction().begin();
//        final Author reference =
//        entityManager.getReference(Author.class, 1L);
//        System.out.println(reference);
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
        entityManager.getTransaction().begin();
        entityManager.persist(author);
        entityManager.getTransaction().commit();

        return author;
    }

    public List<Author> getAuthorList(String... params) {
        final EntityManager entityManager = getEntityManager();
        final CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);
        final Root<Author> root = criteriaQuery.from(Author.class);

        addSelectToCriteriaQuery(criteriaQuery, criteriaBuilder, root, params);

        final TypedQuery<Author> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public List<Author> getAuthorListForName(String name, String... params) {
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
        final Author author = entityManager.getReference(Author.class, id);
        return author;
    }

    //    https://kodejava.org/how-do-i-update-entity-object-using-jpa/
    public void updateAuthor(Author author) {
        entityManager.getTransaction().begin();
        entityManager.merge(author);
        entityManager.getTransaction().commit();
    }

    public void deleteAuthor(Long id) {
        final Author author = entityManager.find(Author.class, id);
        if (author != null) {
            entityManager.getTransaction().begin();
//        final Author reference = entityManager.getReference(Author.class, id);
            entityManager.remove(author);
            entityManager.getTransaction().commit();
        }
    }

    public void deleteAuthor(String name) {
        entityManager.getTransaction().begin();
        final CriteriaDelete<Author> criteriaDelete = criteriaBuilder.createCriteriaDelete(Author.class);
        final Root<Author> root = criteriaDelete.from(Author.class);
        final ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class, "name");
        criteriaDelete.where(criteriaBuilder.like(root.get(Author_.name), parameter));
        final Query query = entityManager.createQuery(criteriaDelete);
        query.setParameter("name", String.format("%%%s%%", name));
        query.executeUpdate();
        entityManager.getTransaction().commit();
    }

    public void deleteAuthor(String name, String lastName, String alternativeName) {
        entityManager.getTransaction().begin();
        final CriteriaDelete<Author> criteriaDelete = criteriaBuilder.createCriteriaDelete(Author.class);
        final Root<Author> root = criteriaDelete.from(Author.class);

        final ParameterExpression<String> parameterName = criteriaBuilder.parameter(String.class, Author_.NAME);
        final ParameterExpression<String> parameterLastName = criteriaBuilder.parameter(String.class, Author_.SECOND_NAME);
//        final ParameterExpression<String> parameterAlternativeName = criteriaBuilder.parameter(String.class, Author_.NAME);

        criteriaDelete.where(
                criteriaBuilder.or(
                        criteriaBuilder.and(
                                criteriaBuilder.like(root.get(Author_.name), parameterName),
                                criteriaBuilder.like(root.get(Author_.secondName), parameterLastName)
                        ),
                        criteriaBuilder.like(root.get(Author_.name), alternativeName)
                )
        );

        criteriaBuilder.parameter(String.class, Author_.SECOND_NAME);
        final Query query = entityManager.createQuery(criteriaDelete);
        query.setParameter(Author_.NAME, String.format("%%%s%%", name));
        query.setParameter(Author_.SECOND_NAME, String.format("%%%s%%", lastName));
//        query.setParameter(Author_.NAME, String.format("%%%s%%", alternativeName));

        query.executeUpdate();
        entityManager.getTransaction().commit();
    }

    public void updateNameForLengthMoreThen(int first, String name) {
        entityManager.getTransaction().begin();
        final CriteriaUpdate<Author> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Author.class);
        final Root<Author> root = criteriaUpdate.from(Author.class);
        final ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class, Author_.NAME);
        final Expression<Integer> length = criteriaBuilder.length(root.get(Author_.name));
        criteriaUpdate.set(root.get(Author_.name), parameter).where(criteriaBuilder.greaterThan(length, first));
        final Query query = entityManager.createQuery(criteriaUpdate);
        query.setParameter(Author_.NAME, name);
        query.executeUpdate();

        entityManager.getTransaction().commit();
    }

}
