package com.group10.paperlessexamwebservice.service.exceptions.user;
/**
 * Exception thrown in case the username was found in the database
 */
public class UsernameFoundException  extends Exception{
    public UsernameFoundException(String message)
    {
        super(message);
    }
}


