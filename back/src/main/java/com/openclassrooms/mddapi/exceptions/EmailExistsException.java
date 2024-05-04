package com.openclassrooms.mddapi.exceptions;

/**
 * Exception thrown when an email already exists.
 */
public class EmailExistsException extends RuntimeException {
    public EmailExistsException(String message) {
        super(message);
    }
}
