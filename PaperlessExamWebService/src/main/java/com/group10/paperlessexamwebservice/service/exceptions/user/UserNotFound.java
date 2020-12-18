package com.group10.paperlessexamwebservice.service.exceptions.user;

/**
 * Exception thrown in case the user/users where not found in the system
 */
public class UserNotFound extends Exception {
    public UserNotFound(String message) {
        super(message);
    }
}
