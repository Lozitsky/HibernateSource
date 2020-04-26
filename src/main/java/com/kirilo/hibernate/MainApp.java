package com.kirilo.hibernate;

import com.kirilo.hibernate.entities.Author;
import com.kirilo.hibernate.entities.Book;
import com.kirilo.hibernate.exceptions.CloseEntityManagerException;
import com.kirilo.hibernate.helpers.AbstractHelper;
import com.kirilo.hibernate.helpers.AuthorHelper;
import com.kirilo.hibernate.helpers.BookHelper;

import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        try (AbstractHelper<Author> authorHelper = new AuthorHelper()) {
            final List<Author> authorList = authorHelper.getEntityList();
            authorList.forEach(System.out::println);
        } catch (CloseEntityManagerException e) {
            e.printStackTrace();
        }

//        https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#collections-customizing-ordered-by-sql-clause
        try (AbstractHelper<Book> bookHelper = new BookHelper()) {
            final List<Book> bookList = bookHelper.getEntityList();
            bookList.forEach(System.out::println);
        } catch (CloseEntityManagerException e) {
            e.printStackTrace();
        }
// close EntityManagerFactory
        JPAUtil.shutdown();
    }
}
