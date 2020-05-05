package com.kirilo.hibernate;

import com.kirilo.hibernate.entities.Author;
import com.kirilo.hibernate.entities.Author_;
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

        //---------------------------Author Persistent Object----------------------------------------------------------------------------
        try (AuthorHelper authorHelper = new AuthorHelper()) {

//            authorHelper.addAuthor(authorHelper.createAuthor("FirstName", "LastName"));
//            testUpdate(authorHelper);
//            authorHelper.generateAndAddAuthors(200);
//            final List<Author> authorListWithParam = authorHelper.getAuthorListWithParam("id", "name");

//            https://docs.jboss.org/hibernate/orm/5.0/topical/html/metamodelgen/MetamodelGenerator.html
//            https://stackoverflow.com/questions/54218556/how-to-generate-jpa-metamodel-with-gradle-5-x
//            final List<Author> authorListWithParam = authorHelper.getAuthorList(Author_.ID, Author_.NAME);
            List<Author> authorListWithParam = authorHelper.getAuthorListForName("", Author_.ID, Author_.NAME);
            printList(authorListWithParam);

//            authorHelper.deleteAuthor(203L);
//            authorHelper.deleteAuthor("_17");
//            authorHelper.deleteAuthor("_97", "_97", "197");
            authorHelper.updateNameForLengthMoreThen(12, "FirstName_99+");
            authorListWithParam = authorHelper.getAuthorListForName("", Author_.ID, Author_.NAME);
            printList(authorListWithParam);

        } catch (CloseEntityManagerException e) {
            e.printStackTrace();
        }
        //-------------------------------------------------------------------------------------------------------

        //---------------------------Book Persistent Object----------------------------------------------------------------------------
        //        https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#collections-customizing-ordered-by-sql-clause
        try (AbstractHelper<Book> bookHelper = new BookHelper()) {
            //        get list of Books
            final List<Book> bookList = bookHelper.getEntityList();
            bookList.forEach(LOG::warn/*book -> LOG.log(Level.WARN, book)*/);

        } catch (CloseEntityManagerException e) {
            e.printStackTrace();
        }
        //-------------------------------------------------------------------------------------------------------


        // close EntityManagerFactory
        JPAUtil.shutdown();
    }

    private static void testUpdate(AuthorHelper authorHelper) {
        //        get list of Authors
        GetAndPrintListOfAuthors(authorHelper);

        final Author author = authorHelper.getAuthor(12L);
        author.setName("ChangedName2");
        authorHelper.updateAuthor(author);

        GetAndPrintListOfAuthors(authorHelper);
    }

    private static void GetAndPrintListOfAuthors(AuthorHelper authorHelper) {
        final List<Author> authorList = authorHelper.getEntityList();
        printList(authorList);
    }

    private static void printList(List<Author> authorList) {
        authorList.forEach(LOG::warn);
    }

}
