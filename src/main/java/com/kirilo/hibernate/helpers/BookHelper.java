package com.kirilo.hibernate.helpers;

import com.kirilo.hibernate.entities.Book;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class BookHelper extends AbstractHelper<Book> {

    public BookHelper() {
        super();
    }

    @Override
    public List<Book> getEntityList() {
        final EntityManager entityManager = getEntityManager();
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
//        final Root<Book> root =
                criteriaQuery.from(Book.class);
//        criteriaQuery.select(root);
        final TypedQuery<Book> query = entityManager.createQuery(criteriaQuery);
        final List<Book> resultList = query.getResultList();
        return resultList;
    }
}
