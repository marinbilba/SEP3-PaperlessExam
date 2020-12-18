package com.group10.paperlessexamwebservice.service.exceptions.user;

/**
 * Exception thrown in case the username does not match the substring of the email until the '@' char
 */
public class UsernameNotMatchEmail extends Exception {
    public UsernameNotMatchEmail(String message) {
        super(message);
    }
}
