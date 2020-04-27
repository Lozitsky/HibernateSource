package com.kirilo.hibernate;

import com.kirilo.hibernate.entities.Author;
import com.kirilo.hibernate.entities.Book;
import com.kirilo.hibernate.exceptions.CloseEntityManagerException;
import com.kirilo.hibernate.helpers.AbstractHelper;
import com.kirilo.hibernate.helpers.AuthorHelper;
import com.kirilo.hibernate.helpers.BookHelper;
import org.jboss.logging.Logger;

import java.util.List;

//import org.apache.log4j.Logger;

public class MainApp {

    private static final Logger LOG = Logger.getLogger(MainApp.class);

    public static void main(String[] args) {
        try (AbstractHelper<Author> authorHelper = new AuthorHelper()) {
            final List<Author> authorList = authorHelper.getEntityList();
            authorList.forEach(LOG::warn);
        } catch (CloseEntityManagerException e) {
            e.printStackTrace();
        }

//        https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#collections-customizing-ordered-by-sql-clause
        try (AbstractHelper<Book> bookHelper = new BookHelper()) {
            final List<Book> bookList = bookHelper.getEntityList();
            bookList.forEach(LOG::warn/*book -> LOG.log(Level.WARN, book)*/);

        } catch (CloseEntityManagerException e) {
            e.printStackTrace();
        }
// close EntityManagerFactory
        JPAUtil.shutdown();
    }
}
