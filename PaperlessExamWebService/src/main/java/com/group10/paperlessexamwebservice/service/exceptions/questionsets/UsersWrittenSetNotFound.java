package com.group10.paperlessexamwebservice.service.exceptions.questionsets;

/**
 * Exception thrown if user does not have any Written sets created
 */
public class UsersWrittenSetNotFound extends Exception {
    public UsersWrittenSetNotFound(String message) {
        super(message);
    }

}
