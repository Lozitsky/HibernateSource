package com.kirilo.hibernate.exceptions;

public class CloseEntityManagerFactoryException extends Exception {
    public CloseEntityManagerFactoryException() {
        super("Can't close EntityManagerFactory!");
    }
}
