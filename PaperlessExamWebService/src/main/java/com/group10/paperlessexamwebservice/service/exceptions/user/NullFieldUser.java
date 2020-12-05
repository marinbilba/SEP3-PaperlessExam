package com.group10.paperlessexamwebservice.service.exceptions.user;

/**
 * Exception thrown if one of the users fields is null
 */
public class NullFieldUser extends Exception{
    public NullFieldUser(String message)
    {
        super(message);
    }
}
