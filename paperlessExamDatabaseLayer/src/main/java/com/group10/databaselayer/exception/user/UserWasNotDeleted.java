package com.group10.databaselayer.exception.user;

/**
 * Exception thrown if user was not deleted
 */
public class UserWasNotDeleted extends Exception {
    public UserWasNotDeleted(String message){
        super(message);
    }
}
