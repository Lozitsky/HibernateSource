package com.kirilo.hibernate.exceptions;

public class CloseEntityManagerException extends Exception {
    public CloseEntityManagerException() {
        super("Can't close EntityManager!");
    }
}
