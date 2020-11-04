package com.group10.paperlessexamwebservice.service.exceptions.user;

public class PasswordNotFoundException extends Exception {

    public PasswordNotFoundException(String message)
    {
        super(message);
    }
}
